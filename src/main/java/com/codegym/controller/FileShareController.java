package com.codegym.controller;

import com.codegym.model.FileShareForm;
import com.codegym.service.FileShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileShareController {

    @Autowired
    private FileShareService fileShareService;

    @GetMapping("/")
    public ModelAndView showIndex(){
        ModelAndView modelAndView = new ModelAndView("/index");
        return modelAndView;
    }

    @GetMapping("/upload-file")
    public ModelAndView showFormUploadFile(){
        ModelAndView modelAndView = new ModelAndView("/upload");
        modelAndView.addObject("fileShareForm", new FileShareForm());
        return modelAndView;
    }
}
