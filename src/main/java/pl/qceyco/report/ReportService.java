package pl.qceyco.report;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.qceyco.client.Client;
import pl.qceyco.client.ClientRepository;
import pl.qceyco.employee.Employee;
import pl.qceyco.employee.EmployeeRepository;
import pl.qceyco.project.Project;
import pl.qceyco.project.ProjectRepository;
import pl.qceyco.timesheet.unit.TimesheetUnit;
import pl.qceyco.timesheet.unit.TimesheetUnitRepository;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.firstInMonth;

@Service
public class ReportService {
    private final TimesheetUnitRepository timesheetUnitRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;

    public ReportService(TimesheetUnitRepository timesheetUnitRepository, ProjectRepository projectRepository,
                         EmployeeRepository employeeRepository, ClientRepository clientRepository) {
        this.timesheetUnitRepository = timesheetUnitRepository;
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
        this.clientRepository = clientRepository;
    }

    List<Project> getAllBillableProjects() {
        return projectRepository.findAllByIsBillableIsTrue();
    }

    List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    List<TimesheetUnit> getAllTimesheets() {
        return timesheetUnitRepository.findAll();
    }

    LocalDate getFirstMondayOfMonth(LocalDate selectedMonday) {
        return selectedMonday.with(firstInMonth(DayOfWeek.MONDAY));
    }

    List<TimesheetUnit> getAllEmployeeTimesheetsFrom4Weeks(Long employeeId, LocalDate start, LocalDate end) {
        return timesheetUnitRepository.findAllByEmployeeIdInSearchPeriod(employeeId, start, end);
    }

    //EMPLOYEE REPORT ******************************************************************************************************
    EmployeeReport employeeReportProcess(LocalDate selectedMonday, Long employeeId) {
        List<TimesheetUnit> timesheets = getAllEmployeeTimesheetsFrom4Weeks(employeeId, selectedMonday, selectedMonday.plusDays(27));
        Employee employee = employeeRepository.findFirstById(employeeId);
        Integer hourlyRate = employee.getAdditionalInfo().getHourlyRateChargingClients();
        int valueOfRenderedServices = hourlyRate * countBillableHours(timesheets);
        boolean isMonthlyTargetAchieved = false;
        double bonusAmountD = 0.0;
        if (valueOfRenderedServices >= employee.getAdditionalInfo().getTargetBudget()) {
            isMonthlyTargetAchieved = true;
            bonusAmountD = (employee.getAdditionalInfo().getBonus() * (valueOfRenderedServices - employee.getAdditionalInfo().getTargetBudget())) / 100.0;
        }
        Integer bonusAmount = getBonusAmountAsInt(bonusAmountD);
        return EmployeeReport.builder()
                .reportedEmployee(employee)
                .amountOfBillableHours(countBillableHours(timesheets))
                .amountOfNonBillableHours(countNonBillableHours(timesheets))
                .workTimeUtilizationLevel(getWorkTimeUtilisationLevelAsInt(countNonBillableHours(timesheets), countBillableHours(timesheets)))
                .isMonthlyTargetAchieved(isMonthlyTargetAchieved)
                .bonusAmount(bonusAmount)
                .selectedMonday(selectedMonday)
                .valueOfRenderedServices(valueOfRenderedServices)
                .build();
    }

    private Integer getBonusAmountAsInt(Double bonusAmount) {
        bonusAmount = Math.floor(bonusAmount);
        return bonusAmount.intValue();
    }

    private Integer countBillableHours(List<TimesheetUnit> timesheets) {
        int billableHours = 0;
        billableHours = timesheets.stream()
                .filter(t -> t.getProject().isBillable())
                .mapToInt(TimesheetUnit::countWeekHours)
                .sum();
        return billableHours;
    }

    private Integer countNonBillableHours(List<TimesheetUnit> timesheets) {
        int nonBillableHours = 0;
        nonBillableHours = timesheets.stream()
                .filter(t -> !t.getProject().isBillable())
                .mapToInt(TimesheetUnit::countWeekHours)
                .sum();
        return nonBillableHours;
    }

    private Integer getWorkTimeUtilisationLevelAsInt(Integer nonBillableHours, Integer billableHours) {
        double workTimeUtilizationLevelD = billableHours.doubleValue() / (billableHours + nonBillableHours) * 100;
        if (Double.isNaN(workTimeUtilizationLevelD)) {
            workTimeUtilizationLevelD = 0.0;
        }
        workTimeUtilizationLevelD = Math.floor(workTimeUtilizationLevelD);
        return (int) workTimeUtilizationLevelD;
    }

    //PROJECT REPORT ******************************************************************************************************
    public ProjectReport projectReportProcess(Long projectId) {
        int potentialValueOfRenderedServices = countProjectHours(projectId) * projectRepository.findFirstByIdWithProjectTeamMembers(projectId).getClient().getAdditionalInfo().getHourlyRateIsCharged();
        boolean isProjectProfitable = true;
        if (potentialValueOfRenderedServices > projectRepository.findFirstByIdWithProjectTeamMembers(projectId).getCapOnRemuneration()) {
            isProjectProfitable = false;
        }
        return ProjectReport.builder()
                .amountOfHours(countProjectHours(projectId))
                .projectIsProfitable(isProjectProfitable)
                .potentialValueOfRenderedServices(potentialValueOfRenderedServices)
                .project(projectRepository.findFirstByIdWithProjectTeamMembers(projectId))
                .build();
    }

    private Integer countProjectHours(Long projectId) {
        int amountOfHours = 0;
        List<TimesheetUnit> timesheets = timesheetUnitRepository.findAllByProjectIdOrderByEmployeeId(projectId);
        amountOfHours = timesheets.stream()
                .mapToInt(TimesheetUnit::countWeekHours)
                .sum();
        return amountOfHours;
    }

    //INVOICE PREVIEW REPORT ******************************************************************************************************
    InvoiceReport invoicePreviewProcess(LocalDate selectedMonday, Long clientId) {
        return InvoiceReport.builder()
                .amountOfHours(timesheetUnitRepository.findAllByClientInSearchPeriod(clientId, selectedMonday, selectedMonday.plusDays(27))
                        .stream().mapToInt(TimesheetUnit::countWeekHours).sum())
                .client(clientRepository.findFirstById(clientId))
                .projectsOfClient(projectRepository.findAllByClientId(clientId))
                .selectedMonday(selectedMonday)
                .timesheets(timesheetUnitRepository.findAllByClientInSearchPeriod(clientId, selectedMonday, selectedMonday.plusDays(27)))
                .build();
    }

    //TIMESHEET EXCEL REPORT ******************************************************************************************************
    void exportTimesheetsProcess(LocalDate start, LocalDate end, Long employeeId, Model model) {
        XSSFWorkbook workbook;
        String fileDictName = "timesheets.xlsx";
        JFileChooser fileChooser = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("Files", ".xlsx");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setDialogTitle("Save the report file");
        fileChooser.setSelectedFile(new File(fileDictName));
        int userSelection = fileChooser.showSaveDialog(fileChooser);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            fileDictName = fileChooser.getSelectedFile().getAbsolutePath();
        }
        File file = new File(fileDictName);
        workbook = new XSSFWorkbook();
        List<TimesheetUnit> timesheetsAll = timesheetUnitRepository.findAllByEmployeeIdInSearchPeriod(employeeId, start, end);
        List<Project> projectsAll = projectRepository.findAllByEmployeeId(employeeId);
        Employee reportedEmployee = employeeRepository.findFirstById(employeeId);
        CellStyle headerStyle = setHeaderStyle(workbook);
        for (Project project : projectsAll) {
            Sheet sheet = createSheet(workbook, project);
            insertHeaderDataIntoExcel(start, end, reportedEmployee, headerStyle, sheet);
            int rowNumber = 4;
            insertTimesheetsIntoExcel(timesheetsAll, project, sheet, rowNumber);
        }
        try (FileOutputStream out = new FileOutputStream(file)) {
            workbook.write(out);
        } catch (Exception e) {
            model.addAttribute("excelError", "Error: cannot generate excel file. Cause: " + e.getMessage());
        }
        model.addAttribute("excelSuccess", "Success! Your report was saved at:");
        model.addAttribute("path", fileDictName);
    }

    private CellStyle setHeaderStyle(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setBold(true);
        headerStyle.setFont(font);
        return headerStyle;
    }

    private Sheet createSheet(Workbook workbook, Project project) {
        String projectSignature = project.getSignature().replace("/", "-");
        return workbook.createSheet(projectSignature);
    }

    private void insertHeaderDataIntoExcel(LocalDate start, LocalDate end, Employee reportedEmployee, CellStyle headerStyle, Sheet sheet) {
        Row employeeNameRow = sheet.createRow(0);
        Cell employeeNameCell1 = employeeNameRow.createCell(0);
        employeeNameCell1.setCellValue("Employee");
        employeeNameCell1.setCellStyle(headerStyle);
        Cell employeeNameCell2 = employeeNameRow.createCell(1);
        employeeNameCell2.setCellValue(reportedEmployee.getNameDisplay());
        employeeNameCell2.setCellStyle(headerStyle);
        Row timeSpanRow = sheet.createRow(1);
        Cell timeSpanCell1 = timeSpanRow.createCell(0);
        timeSpanCell1.setCellValue("Time span");
        timeSpanCell1.setCellStyle(headerStyle);
        Cell timeSpanCell2 = timeSpanRow.createCell(1);
        timeSpanCell2.setCellValue(start.toString() + " - " + end.toString());
        timeSpanCell2.setCellStyle(headerStyle);
        Row tableHeaders = sheet.createRow(3);
        Cell tableHeadersCell1 = tableHeaders.createCell(0);
        tableHeadersCell1.setCellValue("Date");
        tableHeadersCell1.setCellStyle(headerStyle);
        Cell tableHeadersCell2 = tableHeaders.createCell(1);
        tableHeadersCell2.setCellValue("Hours");
        tableHeadersCell2.setCellStyle(headerStyle);
        Cell tableHeadersCell3 = tableHeaders.createCell(2);
        tableHeadersCell3.setCellValue("Comments");
        tableHeadersCell3.setCellStyle(headerStyle);
    }

    private void insertTimesheetsIntoExcel(List<TimesheetUnit> timesheetsAll, Project project, Sheet sheet, int rowNumber) {
        for (TimesheetUnit timesheet : timesheetsAll) {
            if (timesheet.getProject().equals(project)) {
                insertTimesheetDataIntoExcel(sheet, rowNumber, timesheet);
                rowNumber = rowNumber + 7;
            }
        }
    }

    private void insertTimesheetDataIntoExcel(Sheet sheet, int rowNumber, TimesheetUnit timesheet) {
        Row monday = sheet.createRow(rowNumber);
        monday.createCell(0).setCellValue(timesheet.getWorkWeek().getDateMonday().toString());
        monday.createCell(1).setCellValue(timesheet.getWorkWeek().getMondayHours());
        monday.createCell(2).setCellValue(timesheet.getWorkWeek().getCommentary().getMondayCommentary());
        Row tuesday = sheet.createRow(rowNumber + 1);
        tuesday.createCell(0).setCellValue(timesheet.getWorkWeek().getDateMonday().plusDays(1).toString());
        tuesday.createCell(1).setCellValue(timesheet.getWorkWeek().getTuesdayHours());
        tuesday.createCell(2).setCellValue(timesheet.getWorkWeek().getCommentary().getTuesdayCommentary());
        Row wednesday = sheet.createRow(rowNumber + 2);
        wednesday.createCell(0).setCellValue(timesheet.getWorkWeek().getDateMonday().plusDays(2).toString());
        wednesday.createCell(1).setCellValue(timesheet.getWorkWeek().getWednesdayHours());
        wednesday.createCell(2).setCellValue(timesheet.getWorkWeek().getCommentary().getWednesdayCommentary());
        Row thursday = sheet.createRow(rowNumber + 3);
        thursday.createCell(0).setCellValue(timesheet.getWorkWeek().getDateMonday().plusDays(3).toString());
        thursday.createCell(1).setCellValue(timesheet.getWorkWeek().getThursdayHours());
        thursday.createCell(2).setCellValue(timesheet.getWorkWeek().getCommentary().getThursdayCommentary());
        Row friday = sheet.createRow(rowNumber + 4);
        friday.createCell(0).setCellValue(timesheet.getWorkWeek().getDateMonday().plusDays(4).toString());
        friday.createCell(1).setCellValue(timesheet.getWorkWeek().getFridayHours());
        friday.createCell(2).setCellValue(timesheet.getWorkWeek().getCommentary().getFridayCommentary());
        Row saturday = sheet.createRow(rowNumber + 5);
        saturday.createCell(0).setCellValue(timesheet.getWorkWeek().getDateMonday().plusDays(5).toString());
        saturday.createCell(1).setCellValue(timesheet.getWorkWeek().getSaturdayHours());
        saturday.createCell(2).setCellValue(timesheet.getWorkWeek().getCommentary().getSaturdayCommentary());
        Row sunday = sheet.createRow(rowNumber + 6);
        sunday.createCell(0).setCellValue(timesheet.getWorkWeek().getDateMonday().plusDays(6).toString());
        sunday.createCell(1).setCellValue(timesheet.getWorkWeek().getSundayHours());
        sunday.createCell(2).setCellValue(timesheet.getWorkWeek().getCommentary().getSundayCommentary());
    }

}









