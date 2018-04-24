package org.cjoakim.azure.cosmos.models;

/**
 * POJO class representing the HTTP POSTed 'query' form.
 *
 * Chris Joakim, Microsoft, 2018/04/11
 */

public class QueryForm {

    // Instance variables:
    private String query;

    public QueryForm() {
        super();
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
