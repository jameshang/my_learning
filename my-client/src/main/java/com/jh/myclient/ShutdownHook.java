package com.jh.myclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShutdownHook implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ShutdownHook.class);

    @Override
    public void run() {
        log.info("MyClient shutdown!!!");
    }

}
