package com.webapp.lora.util;

import org.apache.log4j.Logger;

public class Mailer {
    private static final Logger logger = Logger.getLogger(Mailer.class);

    public void logMail(String mailString){
        logger.info(mailString);
    }
}
