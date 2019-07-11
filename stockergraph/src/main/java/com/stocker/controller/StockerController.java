package com.stocker.controller;

import com.stocker.runner.FindVolataileStocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StockerController {

    @Autowired
    private FindVolataileStocks findVolataileStocks;

    @RequestMapping("/findBestRising")
    public ModelAndView findBestRising() {
        ModelAndView model = new ModelAndView("companyList");
        model.addObject("companies", findVolataileStocks.main());
        return model;
    }
}
