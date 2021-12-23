package edu.egg.AgendaJJ.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.username}")

    private String from;
    private static final String SUBJECT = "Agenda Pública de Juicios por Jurado";
    private static final String TEXT = "Bienvenido/a a la Agenda Pública de Juicios por Jurado de la provincia de Buenos Aires.\n" +
            "\n" +
            "Gracias por registrarte.\n" +
            "\n" +
            "Grupo 8, Proyecto Final Fullstack Java. Egg Education\n" +
            "Mantovani, Yanela\n" +
            "Molina, Aylén\n" +
            "Mouríz, Florencia\n" +
            "Paniagua, Eliana\n" +
            "Pereyra, Gabriel\n" +
            "Reyna, Cintia\n" +
            "Santis, Leandro\n" +
            "Vargas Gómez, Daniela";

    public void sendThread(String to) {
        new Thread(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setFrom(from);
            message.setSubject(SUBJECT);
            message.setText(TEXT);
            sender.send(message);
        }).start();
    }
}
