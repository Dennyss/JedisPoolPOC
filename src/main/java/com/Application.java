package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Created by Denys Kovalenko on 10/16/2015.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(Application.class);
        ApplicationContext ctx = app.run(args);

        ApplicationProcessor applicationProcessor = ctx.getBean(ApplicationProcessor.class);
        applicationProcessor.process(args[0]);
    }
}
