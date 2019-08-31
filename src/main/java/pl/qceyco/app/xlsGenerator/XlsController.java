package pl.qceyco.app.xlsGenerator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class XlsController {
}


/*
@Controller
@RequestMapping(value = "/")
public class ExcelController {

    private final CostRepository costRepository;

    public ExcelController(CostRepository costRepository) {
        this.costRepository = costRepository;
    }

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public ModelAndView costListReport(HttpServletRequest req, HttpServletResponse res) {

        String typeReport = req.getParameter("type");

        List<Cost> list = costRepository.findAll();


        if (typeReport != null && typeReport.equals("xls")) {
            return new ModelAndView(new ExcelCostListReportView(), "costList", list);
        }

        return new ModelAndView("costlist", "costList", list);
    }
}*/
