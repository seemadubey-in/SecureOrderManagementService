package com.soms.service.controller;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.soms.service.businessService.CSVHelper;
import com.soms.service.businessService.OrderInLoadService;
import com.soms.service.businessService.ResponseMessage;

@RestController
@RequestMapping("/api/csv")
public class OrderInLoadController {
     @Autowired
     OrderInLoadService orderinloadService;
     
     @PostMapping("/upload")
     public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
       String message = "";
       System.out.println("inside controller");
       if (CSVHelper.hasCSVFormat(file)) { 
         try {
        	 orderinloadService.deleteAllRecords();
        	 orderinloadService.save(file);

           message = "Uploaded the file successfully: " + file.getOriginalFilename();
           return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
         } 
         catch (ConstraintViolationException e) {
             message = "Could not upload the file status must have only values N,I,C: " + file.getOriginalFilename() + "! ";
             e.printStackTrace();
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
           }
         catch (Exception e) {
           message = "Could not upload the file: " + file.getOriginalFilename() + "! ";
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
         }
       }

       message = "Please upload a csv file!";
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
     }
}
