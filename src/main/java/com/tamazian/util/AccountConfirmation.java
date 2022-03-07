package com.tamazian.util;

import java.util.Random;

public class AccountConfirmation {

    private static final AccountConfirmation INSTANCE = new AccountConfirmation();
    SendEmailUtil sendEmailUtil = SendEmailUtil.getInstance();

    private static final String MESSAGE = "Click here to confirm email: <a href=http://localhost:8081/registration?confirm=";

    public String isAccountConfirmationSuccessful(String emailTo) {
        String registrationKey = String.valueOf(generateRegistrationKey());
        String message = MESSAGE + registrationKey + ">link</a>";
        sendEmailUtil.sendEmail(emailTo, message);

        return registrationKey;

    }

    private int generateRegistrationKey() {
        return new Random().nextInt((999999 - 100000) + 1) + 100000;
    }

    public static AccountConfirmation getInstance() {
        return INSTANCE;
    }
}
