package com.jobintech.registration.service.imp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobintech.registration.dto.StudentDto;
import com.jobintech.registration.entity.UserMessage;
import com.jobintech.registration.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.print.attribute.standard.Destination;

@Component
public class SendServiceImpl implements SendService {

    private final JmsTemplate jmsTemplate;

    @Autowired
    public SendServiceImpl(JmsTemplate jmsTemplate){
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void send(String destination, StudentDto student) throws JsonProcessingException {
        jmsTemplate.convertAndSend(destination,student);
    }
}
