package org.cjoakim.azure.cosmos.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * This is a Spring Boot HTTP Controller class for the / path.
 *
 * Chris Joakim, Microsoft, 2018/04/11
 */

@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    // inject via application.properties
    // @Value("${welcome.message:test}")
    private String message = "Index";

    @RequestMapping("/")
    public String index(Map<String, Object> model) {

        logger.warn("IndexController#index");

        model.put("message", this.message);

        return "index"; // return the name of the template to render
    }

}