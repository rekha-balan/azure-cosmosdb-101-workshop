package org.cjoakim.azure.cosmos.models;

/**
 * POJO class representing the HTTP POSTed 'insert' form.
 *
 * Chris Joakim, Microsoft, 2018/04/11
 */

public class InsertForm {

    // Instance variables:
    private String json;

    public InsertForm() {
        super();
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getScrubbedJson() {
        String s1 = this.json.replace('\n', ' ');
        return s1.replace('\r',' ');
    }
}
