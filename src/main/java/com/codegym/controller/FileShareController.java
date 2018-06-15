package com.codegym.controller;

import com.codegym.model.FileShare;
import com.codegym.model.FileShareForm;
import com.codegym.service.FileShareService;
import com.codegym.utils.StorageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Controller
public class FileShareController {

    @Autowired
    private FileShareService fileShareService;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/")
    public ModelAndView showIndex() {
        Iterable<FileShare> fileShares = fileShareService.findAll();

        ModelAndView modelAndView = new ModelAndView("/index");
        modelAndView.addObject("fileShares", fileShares);
        return modelAndView;
    }

    @GetMapping("/upload-file")
    public ModelAndView showFormUploadFile() {
        ModelAndView modelAndView = new ModelAndView("/upload");
        modelAndView.addObject("fileShareForm", new FileShareForm());
        return modelAndView;
    }

    @PostMapping("/upload-file")
    public ModelAndView uploadFile(@ModelAttribute("fileShareForm") FileShareForm fileShareForm) {
        try {
            String randomCode = UUID.randomUUID().toString();
            String originFileName = fileShareForm.getFile().getOriginalFilename();
            String randomName = randomCode + StorageUtils.getFileExtension(originFileName);
            fileShareForm.getFile().transferTo(new File(StorageUtils.FEATURE_LOCATION + "/" + randomName));

            FileShare fileShare = new FileShare();
            fileShare.setName(fileShareForm.getName());
            fileShare.setDescription(fileShareForm.getDescription());
            fileShare.setFileUrl(randomName);
            fileShare.setShare(fileShareForm.isShare());

            fileShareService.save(fileShare);

        } catch (IOException e) {
            e.printStackTrace();
        }
        ModelAndView modelAndView = new ModelAndView("/upload");
        modelAndView.addObject("fileShareForm", new FileShareForm());
        modelAndView.addObject("message", "Upload success!!!");
        return modelAndView;
    }

    @GetMapping("/{id}/edit-file")
    public ModelAndView showEditForm(@PathVariable("id") Long id) {
        FileShare fileShare = fileShareService.findById(id);

        FileShareForm fileShareForm = new FileShareForm();
        fileShareForm.setId(id);
        fileShareForm.setName(fileShare.getName());
        fileShareForm.setDescription(fileShare.getDescription());
        fileShareForm.setShare(fileShare.isShare());
        fileShareForm.setFileUrl(fileShare.getFileUrl());

        ModelAndView modelAndView = new ModelAndView("/edit");
        modelAndView.addObject("fileShareForm", fileShareForm);
        return modelAndView;
    }

    @PostMapping("/{id}/edit-file")
    public ModelAndView editFileShare(@PathVariable("id") Long id, @ModelAttribute("fileShareForm") FileShareForm fileShareForm) {
        try {
            FileShare fileShare = fileShareService.findById(id);
            if (!fileShareForm.getFile().isEmpty()) {
                StorageUtils.removeFile(fileShare.getFileUrl());
                String randomCode = UUID.randomUUID().toString();
                String originFileName = fileShareForm.getFile().getOriginalFilename();
                String randomName = randomCode + StorageUtils.getFileExtension(originFileName);
                fileShareForm.getFile().transferTo(new File(StorageUtils.FEATURE_LOCATION + "/" + randomName));
                fileShare.setFileUrl(randomName);
                fileShareForm.setFileUrl(randomName);
            }
            fileShare.setName(fileShareForm.getName());
            fileShare.setDescription(fileShareForm.getDescription());
            fileShare.setShare(fileShareForm.isShare());

            fileShareService.save(fileShare);

        } catch (IOException e) {
            e.printStackTrace();
        }
        ModelAndView modelAndView = new ModelAndView("/edit");
        modelAndView.addObject("fileShareForm", fileShareForm);
        modelAndView.addObject("message", "Update success!!!");
        return modelAndView;
    }

    @GetMapping("/{id}/share-download-link")
    public ModelAndView shareFileByEmail(@PathVariable("id") Long id) {

        ModelAndView modelAndView = new ModelAndView("/share");
        modelAndView.addObject("id", id);
        return modelAndView;
    }

    @PostMapping("/{id}/sendEmail.do")
    public ModelAndView doSendEmail(@PathVariable("id") Long id, @RequestParam("email") String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(email);
        mailMessage.setSubject("link download file");
        mailMessage.setText("http://localhost:8080/"+ id + "/download-file");

        mailSender.send(mailMessage);

        ModelAndView modelAndView = new ModelAndView("/share");
        modelAndView.addObject("message", "Link download was send!!!");

        return modelAndView;
    }

    @GetMapping("/{id}/download-file")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("id") Long id) throws IOException {
        FileShare fileShare = fileShareService.findById(id);

        File file = new File(StorageUtils.FEATURE_LOCATION + "/" + fileShare.getFileUrl());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(file.length())
                .body(resource);
    }
}
