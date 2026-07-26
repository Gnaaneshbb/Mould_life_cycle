package com.example.service;

import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.entity.Operator;
import com.example.repository.OperatorRepository;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final OperatorRepository operatorRepository;

    public EmailService(JavaMailSender mailSender,
                        OperatorRepository operatorRepository) {

        this.mailSender = mailSender;
        this.operatorRepository = operatorRepository;
    }

    public void sendInspectionAlert(String mouldId, int cycles) {

        List<Operator> users =
                operatorRepository.findByRoleIn(
                        List.of("ROLE_SUPERVISOR", "ROLE_ADMIN")
                );

        List<String> emails = users.stream()
                .map(Operator::getEmail)
                .filter(e -> e != null)
                .toList();

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(emails.toArray(new String[0]));

        message.setSubject("Inspection Alert");

        message.setText(
                "Mould " + mouldId +
                " reached " + cycles +
                " cycles. Inspection required."
        );

        mailSender.send(message);
    }
}