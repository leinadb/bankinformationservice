package com.paymentator.bankinformationservice.server;

import javax.ws.rs.core.Application;

import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;

import io.undertow.Undertow;

public class RESTServer {


    private UndertowJaxrsServer server;

    public RESTServer(String serverHost, int serverPort) {
        server = new UndertowJaxrsServer()
                .start(Undertow.builder().addHttpListener(serverPort, serverHost));
    }

    public void deploy(Application application) {
        server.deploy(application);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                server.stop();
            }
        });
    }

    public void stop() {
        server.stop();
    }
}
