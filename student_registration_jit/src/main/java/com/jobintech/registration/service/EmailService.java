package com.jobintech.registration.service;

import com.jobintech.registration.utils.EmailDetails;

public interface EmailService {

    void sendSimpleMail(EmailDetails details);

}
