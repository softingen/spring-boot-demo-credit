package com.credit.app.core.common.utils;

import com.credit.app.core.common.constants.Constants;
import com.credit.app.core.common.constants.ServiceLinks;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class Utils {

    private static Utils utils = null;
    private JavaMailSender javaMailSender;

    public Utils(){}

    public Utils(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public static Utils newInstance() {
        if (utils == null) {
            utils = new Utils();
        }
        return utils;
    }

    public int getCreditScore() {
        URL url = null;
        try {
            url = new URL(ServiceLinks.CREDIT_SCORE_SERVICE);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String creditScore = "0";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), Constants.ENCODING_UTF8))) {
            for (String score; (score = reader.readLine()) != null; ) {
                creditScore = score;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(creditScore);
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
}
