package org.cjoakim.azure.cosmos;

import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * This class listens for Spring Boot application events.
 *
 * Chris Joakim, Microsoft, 2018/04/11
 */

@Component
public class SpringApplicationEventListener implements ApplicationListener<SpringApplicationEvent> {

    @Override
    public void onApplicationEvent(final SpringApplicationEvent event) {

        String classname = event.getClass().getName();
        System.out.println("SpringApplicationEventListener#onApplicationEvent: " + classname + " -> " + event);
        return;
    }

}