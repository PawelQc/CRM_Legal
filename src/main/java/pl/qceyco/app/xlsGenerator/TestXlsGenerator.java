package pl.qceyco.app.xlsGenerator;

import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class TestXlsGenerator {
}


//public class ExcelCostListReportView extends AbstractXlsView {
//
//    @Override
//    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
//                                      HttpServletResponse response) throws Exception {
//
//        response.setHeader("Content-disposition", "attachment; filename=\"report.xls\"");
//
//        @SuppressWarnings("unchecked")
//        List<Cost> list = (List<Cost>) model.get("costList");
//
//        Sheet sheet = workbook.createSheet("Cost List");
//
//        Row header = sheet.createRow(0);
//        header.createCell(0).setCellValue("ID");
//        header.createCell(1).setCellValue("Date");
//        header.createCell(2).setCellValue("Cost Center");
//        header.createCell(3).setCellValue("Account");
//        header.createCell(4).setCellValue("Client");
//        header.createCell(5).setCellValue("Amount");
//        header.createCell(6).setCellValue("Description");
//        header.createCell(7).setCellValue("Payment");
//        header.createCell(8).setCellValue("Invoice Number");
//        header.createCell(9).setCellValue("Department");
//
//
//        int rowNum = 1;
//
//        for (Cost c : list) {
//            Row row = sheet.createRow(rowNum++);
//            row.createCell(0).setCellValue(c.getId());
//            row.createCell(1).setCellValue(c.getSalesDate());
//            row.createCell(2).setCellValue(String.valueOf(c.getMpk().getCode()));
//            row.createCell(3).setCellValue(String.valueOf(c.getAccount().getCode()));
//            row.createCell(4).setCellValue(String.valueOf(c.getClient().getName()));
//            row.createCell(5).setCellValue(String.valueOf(c.getAmount()));
//            row.createCell(6).setCellValue(c.getDescription());
//            row.createCell(7).setCellValue(String.valueOf(c.getPayment().getType()));
//            row.createCell(8).setCellValue(c.getInvoiceNumber());
//            row.createCell(9).setCellValue(String.valueOf(c.getDepartment().getName()));
//        }
//
//    }
//
//}