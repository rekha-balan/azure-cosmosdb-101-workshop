package org.cjoakim.azure.cosmos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This class is the Spring Boot application itself, which is started as follows:
 * mvn spring-boot:run
 *
 * Chris Joakim, Microsoft, 2018/04/11
 */

@SpringBootApplication
public class CosmosApplication {

	public static void main(String[] args) {

//        Signal.handle(new Signal("INT"), new SignalHandler() {
//            public void handle(Signal sig) {
//                System.out.println("Signal.handle INT");
//                System.exit(0);
//            }
//        });

		SpringApplication.run(CosmosApplication.class, args);
	}
}
