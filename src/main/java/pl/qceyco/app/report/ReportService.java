package pl.qceyco.app.report;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.qceyco.app.client.Client;
import pl.qceyco.app.client.ClientRepository;
import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.employee.EmployeeRepository;
import pl.qceyco.app.project.Project;
import pl.qceyco.app.project.ProjectRepository;
import pl.qceyco.app.timesheet.unit.TimesheetUnit;
import pl.qceyco.app.timesheet.unit.TimesheetUnitRepository;

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

    void employeeReportProcess(LocalDate selectedMonday, Long employeeId, Model model) {
        LocalDate endDate = selectedMonday.plusDays(27);
        List<TimesheetUnit> timesheets = getAllEmployeeTimesheetsFrom4Weeks(employeeId, selectedMonday, endDate);
        Integer nonBillableHours = countNonBillableHours(timesheets);
        Integer billableHours = countBillableHours(timesheets);
        Integer workTimeUtilizationLevel = getWorkTimeUtilisationLevelAsInt(nonBillableHours, billableHours);
        Employee reportedEmployee = employeeRepository.findFirstById(employeeId);
        Integer targetBudget = reportedEmployee.getAdditionalInfo().getTargetBudget();
        Integer hourlyRate = reportedEmployee.getAdditionalInfo().getHourlyRateChargingClients();
        Integer valueOfRenderedServices = hourlyRate * billableHours;
        boolean isMonthlyTargetAchieved = false;
        double bonusAmountD = 0.0;
        if (valueOfRenderedServices >= targetBudget) {
            isMonthlyTargetAchieved = true;
            bonusAmountD = (reportedEmployee.getAdditionalInfo().getBonus() * (valueOfRenderedServices - targetBudget)) / 100.0;
        }
        Integer bonusAmount = getBonusAmountAsInt(bonusAmountD);
        addModelAttributesEmployeeReport(model, selectedMonday, nonBillableHours, billableHours, workTimeUtilizationLevel,
                reportedEmployee, valueOfRenderedServices, isMonthlyTargetAchieved, bonusAmount);
    }

    private void addModelAttributesEmployeeReport(Model model, LocalDate selectedMonday, Integer amountOfNonBillableHours, Integer amountOfBillableHours, Integer workTimeUtilizationLevel, Employee reportedEmployee, Integer valueOfRenderedServices, boolean isMonthlyTargetAchieved, Integer bonusAmount) {
        model.addAttribute("valueOfRenderedServices", valueOfRenderedServices);
        model.addAttribute("reportedEmployee", reportedEmployee);
        model.addAttribute("selectedMonday", selectedMonday);
        model.addAttribute("amountOfBillableHours", amountOfBillableHours);
        model.addAttribute("amountOfNonBillableHours", amountOfNonBillableHours);
        model.addAttribute("workTimeUtilizationLevel", workTimeUtilizationLevel);
        model.addAttribute("isMonthlyTargetAchieved", isMonthlyTargetAchieved);
        model.addAttribute("bonusAmount", bonusAmount);
    }

    private Integer getBonusAmountAsInt(double bonusAmountD) {
        bonusAmountD = Math.floor(bonusAmountD);
        return (Integer) (int) bonusAmountD;
    }

    private Integer countBillableHours(List<TimesheetUnit> timesheets) {
        Integer billableHours = 0;
        for (TimesheetUnit t : timesheets) {
            if (t.getProject().getBillable()) {
                billableHours += t.countWeekHours();
            }
        }
        return billableHours;
    }

    private Integer countNonBillableHours(List<TimesheetUnit> timesheets) {
        Integer nonBillableHours = 0;
        for (TimesheetUnit t : timesheets) {
            if (!t.getProject().getBillable()) {
                nonBillableHours += t.countWeekHours();
            }
        }
        return nonBillableHours;
    }

    private Integer getWorkTimeUtilisationLevelAsInt(Integer nonBillableHours, Integer billableHours) {
        double workTimeUtilizationLevelD = (double) billableHours / (billableHours + nonBillableHours) * 100;
        if (Double.isNaN(workTimeUtilizationLevelD)) {
            workTimeUtilizationLevelD = 0;
        }
        workTimeUtilizationLevelD = Math.floor(workTimeUtilizationLevelD);
        return (Integer) (int) workTimeUtilizationLevelD;
    }

    void projectReportProcess(Long projectId, Model model) {
        Project project = projectRepository.findFirstByIdWithProjectTeamMembers(projectId);
        Integer amountOfHours = countProjectHours(projectId);
        Integer clientDefaultHourlyRate = project.getClient().getAdditionalInfo().getHourlyRateIsCharged();
        Integer potentialValueOfRenderedServices = amountOfHours * clientDefaultHourlyRate;
        Integer capOnRemuneration = project.getCapOnRemuneration();
        boolean isProjectProfitable = true;
        if (potentialValueOfRenderedServices > capOnRemuneration) {
            isProjectProfitable = false;
        }
        addModelAttributesProjectReport(model, project, amountOfHours, potentialValueOfRenderedServices, isProjectProfitable);
    }

    private Integer countProjectHours(Long projectId) {
        Integer amountOfHours = 0;
        List<TimesheetUnit> timesheets = timesheetUnitRepository.findAllByProjectIdOrderByEmployeeId(projectId);
        for (TimesheetUnit t : timesheets) {
            amountOfHours += t.countWeekHours();
        }
        return amountOfHours;
    }

    private void addModelAttributesProjectReport(Model model, Project project, Integer amountOfHours, Integer potentialValueOfRenderedServices, boolean isProjectProfitable) {
        model.addAttribute("amountOfHours", amountOfHours);
        model.addAttribute("project", project);
        model.addAttribute("potentialValueOfRenderedServices", potentialValueOfRenderedServices);
        model.addAttribute("isProjectProfitable", isProjectProfitable);
    }

    void invoicePreviewProcess(LocalDate selectedMonday, Long clientId, Model model) {
        LocalDate endDate = selectedMonday.plusDays(27);
        List<TimesheetUnit> timesheets = timesheetUnitRepository.findAllByClientInSearchPeriod(clientId, selectedMonday, endDate);
        List<Project> projectsOfClient = projectRepository.findAllByClientId(clientId);
        Client client = clientRepository.findFirstById(clientId);
        Integer amountOfHours = 0;
        for (TimesheetUnit t : timesheets) {
            amountOfHours += t.countWeekHours();
        }
        addModelAttributesInvoicePreview(model, selectedMonday, timesheets, projectsOfClient, client, amountOfHours);
    }

    private void addModelAttributesInvoicePreview(Model model, LocalDate selectedMonday, List<TimesheetUnit> timesheets, List<Project> projectsOfClient, Client client, Integer amountOfHours) {
        model.addAttribute("selectedMonday", selectedMonday);
        model.addAttribute("timesheets", timesheets);
        model.addAttribute("projectsOfClient", projectsOfClient);
        model.addAttribute("client", client);
        model.addAttribute("amountOfHours", amountOfHours);
    }

    void exportTimesheetsProcess(LocalDate start, LocalDate end, Long employeeId, Model model) {
        String filePath = null;
        try {
            List<TimesheetUnit> timesheetsAll = timesheetUnitRepository.findAllByEmployeeIdInSearchPeriod(employeeId, start, end);
            List<Project> projectsAll = projectRepository.findAllByEmployeeId(employeeId);
            Employee reportedEmployee = employeeRepository.findFirstById(employeeId);
            Workbook workbook = new XSSFWorkbook();
            CellStyle headerStyle = workbook.createCellStyle();
            XSSFFont font = ((XSSFWorkbook) workbook).createFont();
            font.setFontName("Arial");
            font.setBold(true);
            headerStyle.setFont(font);
            for (Project project : projectsAll) {
                String projectSignature = project.getSignature().replace("/", "-");
                Sheet sheet = workbook.createSheet(projectSignature);
                insertHeaderDataIntoExcel(start, end, reportedEmployee, headerStyle, sheet);
                int rowNumber = 4;
                for (TimesheetUnit timesheet : timesheetsAll) {
                    if (timesheet.getProject().equals(project)) {
                        insertTimesheetDataIntoExcel(sheet, rowNumber, timesheet);
                        rowNumber = rowNumber + 7;
                    }
                }
            }
            filePath = System.getProperty("user.home");
            String fileLocation = filePath + "/" + "timesheetReport.xlsx";
            FileOutputStream outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);
            workbook.close();
        } catch (Exception e) {
            model.addAttribute("excelError", "Error: cannot generate excel file. Cause: " + e.getMessage());
        }
        model.addAttribute("excelSuccess", "Success! Your report was saved at:");
        model.addAttribute("path", filePath + "/timesheetReport.xlsx");
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









