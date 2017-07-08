package de.tub.ise.anwsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("de.tub.ise.anwsys")
@EnableAutoConfiguration
public class Server {

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }

}
