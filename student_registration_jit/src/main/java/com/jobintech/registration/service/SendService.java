package com.jobintech.registration.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jobintech.registration.dto.StudentDto;
import com.jobintech.registration.entity.UserMessage;

public interface SendService {
    void send(String destination, StudentDto student) throws JsonProcessingException;
}
