package com.credit.app.core.util;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Random;

public class GeneralUtils {

    private static GeneralUtils utils = null;
    private JavaMailSender javaMailSender;

    public GeneralUtils(){}

    public GeneralUtils(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public static GeneralUtils newInstance() {
        if (utils == null) {
            utils = new GeneralUtils();
        }
        return utils;
    }

    public boolean sendEmail(String email, String message) {

        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(email);

            msg.setSubject("Credit Application Result");
            msg.setText(message);

            javaMailSender.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public static int generateRandom(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
