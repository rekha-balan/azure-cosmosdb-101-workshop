package org.cjoakim.azure.cosmos.controllers;

import org.cjoakim.azure.cosmos.models.QueryForm;
import org.cjoakim.azure.cosmos.util.MapDataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * This is a Spring Boot HTTP Controller class for the /map path.
 *
 * Chris Joakim, Microsoft, 2018/04/11
 */

@Controller
public class MapController {

    private static final Logger logger = LoggerFactory.getLogger(MapController.class);

    private String message = "Map";

    @RequestMapping("/map")
    public String query(Map<String, Object> model, RedirectAttributes redirectAttributes) {

        model.put("dateNow", new java.util.Date());
        logger.warn("MapController#query, model dateNow: " + model.get("dateNow"));
        return "map";
    }

    @PostMapping("/post_map")
    public String postQuery(@ModelAttribute QueryForm formData, RedirectAttributes attributes) {

        logger.warn("MapController#postQuery: " + formData.getQuery());
        return "redirect:/map";
    }

    // curl -v "http://localhost:8080/map_states_csv"
    @RequestMapping(value = "/map_states_csv", produces = "text/csv")
    public void statesCsv(HttpServletResponse response) throws IOException {

        logger.warn("MapController#states_csv");

        ArrayList<String> lines = MapDataUtil.getMapStatesCsv();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < lines.size(); i++) {
            sb.append(lines.get(i) + "\n");
        }
        response.getWriter().print(sb.toString());
    }

    // curl -v "http://localhost:8080/map_states_csv"
    @RequestMapping(value = "/map_latest_csv", produces = "text/csv")
    public void latestCsv(HttpServletResponse response) throws IOException {

        logger.warn("MapController#latest_csv");

        ArrayList<String> lines = MapDataUtil.getMapLatestCsv();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < lines.size(); i++) {
            sb.append(lines.get(i) + "\n");
        }
        response.getWriter().print(sb.toString());
    }

}
