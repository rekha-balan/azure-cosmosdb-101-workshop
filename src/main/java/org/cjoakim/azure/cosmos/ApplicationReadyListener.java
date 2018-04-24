package org.cjoakim.azure.cosmos;

import org.cjoakim.azure.cosmos.dao.PostalCodeDao;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * This class listens for Spring Boot application startup, and initializes
 * application state at startup.
 *
 * Chris Joakim, Microsoft, 2018/04/11
 */

@Component
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        String classname = event.getClass().getName();
        System.out.println("ApplicationReadyListener#onApplicationEvent: " + classname + " -> " + event);
        PostalCodeDao.init();
        return;
    }

}
