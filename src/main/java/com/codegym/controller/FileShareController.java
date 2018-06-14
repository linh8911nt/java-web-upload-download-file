package com.codegym.controller;

import com.codegym.model.FileShare;
import com.codegym.model.FileShareForm;
import com.codegym.service.FileShareService;
import com.codegym.utils.StorageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class FileShareController {

    @Autowired
    private FileShareService fileShareService;

    @GetMapping("/")
    public ModelAndView showIndex(){
        Iterable<FileShare> fileShares = fileShareService.findAll();

        ModelAndView modelAndView = new ModelAndView("/index");
        modelAndView.addObject("fileShares", fileShares);
        return modelAndView;
    }

    @GetMapping("/upload-file")
    public ModelAndView showFormUploadFile(){
        ModelAndView modelAndView = new ModelAndView("/upload");
        modelAndView.addObject("fileShareForm", new FileShareForm());
        return modelAndView;
    }

    @PostMapping("/upload-file")
    public ModelAndView uploadFile(@ModelAttribute("fileShareForm") FileShareForm fileShareForm){
        try {
            String randomCode = UUID.randomUUID().toString();
            String originFileName = fileShareForm.getFile().getOriginalFilename();
            String randomName = randomCode + StorageUtils.getFileExtension(originFileName);
            fileShareForm.getFile().transferTo(new File(StorageUtils.FEATURE_LOCATION + "/" + originFileName));

            FileShare fileShare = new FileShare();
            fileShare.setName(fileShareForm.getName());
            fileShare.setDescription(fileShareForm.getDescription());
            fileShare.setFileUrl(randomName);
            fileShare.setStatus(fileShareForm.isShare());

            fileShareService.save(fileShare);

        } catch (IOException e){
            e.printStackTrace();
        }
        ModelAndView modelAndView = new ModelAndView("/upload");
        modelAndView.addObject("fileShareForm", new FileShareForm());
        modelAndView.addObject("message", "Upload success!!!");
        return modelAndView;
    }

}
