package com.jobintech.registration.controller;

import com.jobintech.registration.service.EmailService;
import com.jobintech.registration.utils.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    @Autowired
    private EmailService emailService;

    // Sending a simple Email
    @PostMapping("/sendMail")
    public void
    sendMail(@RequestBody EmailDetails details)
    {
        emailService.sendSimpleMail(details);
    }
}
