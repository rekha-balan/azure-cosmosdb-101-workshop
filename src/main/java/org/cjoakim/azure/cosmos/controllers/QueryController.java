package org.cjoakim.azure.cosmos.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.cjoakim.azure.cosmos.AppConfig;
import org.cjoakim.azure.cosmos.dao.DocumentDbDao;
import org.cjoakim.azure.cosmos.models.QueryForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Map;

/**
 * This is a Spring Boot HTTP Controller class for the /query path.
 *
 * Chris Joakim, Microsoft, 2018/04/11
 */

@Controller
public class QueryController {

    private static final Logger logger = LoggerFactory.getLogger(QueryController.class);

    private String message = "Query";

    @RequestMapping("/query")
    public String query(Map<String, Object> model, RedirectAttributes redirectAttributes) {

        // SELECT TOP 10 c.postalCode.cityName from c
        // SELECT * from c where c.postalCode.stateAbbrv = "NC"
        Object query = model.get("query");
        if (query == null) {
            model.put("query", "SELECT TOP 10 c.postalCode.cityName from c");
        }
        model.put("dateNow", new java.util.Date());
        logger.warn("QueryController#query, model query: " + model.get("query"));
        logger.warn("QueryController#query, model results: " + model.get("results"));

        return "query";
    }

    @PostMapping("/post_query")
    public String postQuery(@ModelAttribute QueryForm formData, RedirectAttributes attributes) {

        logger.warn("QueryController#postQuery: " + formData.getQuery());

        String dbName = AppConfig.getDocDbDefaultDbName();
        String collName = AppConfig.getDocDbDefaultCollName();
        String sql = formData.getQuery();

        // SELECT TOP 24 c.postalCode.cityName from c

        DocumentDbDao dao = new DocumentDbDao();
        ArrayList<String> results = dao.queryAsJsonList(dbName, collName, sql);
        String json = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(results);
        }
        catch (Exception e) {
            json = e.getClass().getName() + " - " + e.getMessage();
        }
        logger.warn("json: " + json);
        attributes.addFlashAttribute("query", formData.getQuery());
        attributes.addFlashAttribute("results", json);

        return "redirect:/query";
    }

}
