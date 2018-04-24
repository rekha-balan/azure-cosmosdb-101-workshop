package org.cjoakim.azure.cosmos.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.documentdb.Document;
import org.cjoakim.azure.cosmos.util.SampleDocGenerator;
import org.cjoakim.azure.cosmos.AppConfig;
import org.cjoakim.azure.cosmos.dao.DocumentDbDao;
import org.cjoakim.azure.cosmos.models.InsertForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.Map;

/**
 * This is a Spring Boot HTTP Controller class for the /insert path.
 *
 * Chris Joakim, Microsoft, 2018/04/11
 */

@Controller
public class InsertController {

    private static final Logger logger = LoggerFactory.getLogger(InsertController.class);

    // inject via application.properties
    // @Value("${welcome.message:test}")
    private String message = "Insert";

    @RequestMapping("/insert")
    public String insert(Map<String, Object> model) {

        logger.warn("InsertController#insert");

        Object newDocJson = model.get("newDocJson");
        if (newDocJson == null) {
            SampleDocGenerator gen = new SampleDocGenerator();
            model.put("newDocJson", gen.newDoc());
        }
        model.put("dateNow", new Date());

        return "insert"; // return the name of the template to render
    }

    @PostMapping("/post_insert")
    public String postQuery(@ModelAttribute InsertForm formData, RedirectAttributes attributes) {

        logger.warn("InsertController#post_insert: " + formData.getJson());
        String newDocJson = "{}";

        try {
            String dbName = AppConfig.getDocDbDefaultDbName();
            String collName = AppConfig.getDocDbDefaultCollName();
            String formJson = formData.getScrubbedJson();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(formJson);
            logger.warn("jsonNode: " + jsonNode.toString());

            DocumentDbDao dao = new DocumentDbDao();
            Document doc = dao.upsertDocument(dbName, collName, jsonNode);
            logger.warn("doc: " + doc);
            newDocJson = doc.toJson();
        }
        catch (Exception e) {
            logger.error(e.getClass().getName() + " - " + e.getMessage());
        }
        attributes.addFlashAttribute("newDocJson", newDocJson);

        return "redirect:/insert";
    }
}