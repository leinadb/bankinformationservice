package com.paymentator.bankinformationservice.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static final String SERVER_HOST = "localhost";
    public static final int SERVER_PORT = 8090;

    public static void main(String[] args) {
        RESTServer server = new RESTServer(SERVER_HOST, SERVER_PORT);
        server.deploy(new BankApplication());
        log.info("REST WS is listening on http://{}:{}", SERVER_HOST, SERVER_PORT);
    }
}
