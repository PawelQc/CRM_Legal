package pl.qceyco.app.xlsGenerator;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.qceyco.app.client.Client;
import pl.qceyco.app.client.ClientService;
import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.timesheet.unit.TimesheetUnit;
import pl.qceyco.app.timesheet.unit.TimesheetUnitRepository;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;

@Controller
@RequestMapping("/")

public class ExcelController {

    private final TimesheetUnitRepository timesheetUnitRepository;

    public ExcelController(TimesheetUnitRepository timesheetUnitRepository) {
        this.timesheetUnitRepository = timesheetUnitRepository;
    }


    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public String writeDataToExcel(HttpSession session, Model model) {

        TimesheetUnit timesheetUnit = timesheetUnitRepository.findFirstById(1l);
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Persons");
        Row header = sheet.createRow(0);

        header.createCell(0).setCellValue("No");
        header.createCell(1).setCellValue("Employee");
        header.createCell(2).setCellValue("Project");
        header.createCell(3).setCellValue("Monday");
        header.createCell(4).setCellValue("Tuesday");
        header.createCell(5).setCellValue("Wednesday");
        header.createCell(6).setCellValue("Thursday");
        header.createCell(7).setCellValue("Friday");
        header.createCell(8).setCellValue("Saturday");
        header.createCell(9).setCellValue("Sunday");


        Row row = sheet.createRow(1);
        row.createCell(0).setCellValue(1);
        row.createCell(1).setCellValue(timesheetUnit.getEmployee().getNameDisplay());
        row.createCell(2).setCellValue(timesheetUnit.getProject().getSignature());
        row.createCell(3).setCellValue(timesheetUnit.getWorkWeek().getDateMonday().toString());
        row.createCell(4).setCellValue(timesheetUnit.getWorkWeek().getDateMonday().plusDays(1).toString());
        row.createCell(5).setCellValue(timesheetUnit.getWorkWeek().getDateMonday().plusDays(2).toString());
        row.createCell(6).setCellValue(timesheetUnit.getWorkWeek().getDateMonday().plusDays(3).toString());
        row.createCell(7).setCellValue(timesheetUnit.getWorkWeek().getDateMonday().plusDays(4).toString());
        row.createCell(8).setCellValue(timesheetUnit.getWorkWeek().getDateMonday().plusDays(5).toString());
        row.createCell(9).setCellValue(timesheetUnit.getWorkWeek().getDateMonday().plusDays(6).toString());



        String path = System.getProperty("user.home");
        String fileLocation = path + "/" + "report.xlsx";
        try {
            FileOutputStream outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "";
    }


}
