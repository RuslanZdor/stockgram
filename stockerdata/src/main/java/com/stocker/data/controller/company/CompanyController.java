package com.stocker.data.controller.company;

import com.stocker.symbol.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CompanyController {

    @Autowired
    private CompanyCacheService cacheFacade;

    @RequestMapping("/company/{symbol}/")
    public ModelAndView helloWorld(@PathVariable("symbol") String symbol, @RequestParam(value = "interval", defaultValue = "daily") String interval) {
        Company company = cacheFacade.read(symbol.toUpperCase());
        return null;
    }
}
