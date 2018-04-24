package org.cjoakim.azure.cosmos.util;

/**
 * Simple POJO class for Key-Value pairs.  Used in ConfigController.
 *
 * Chris Joakim, Microsoft, 2018/04/11
 */

public class KVPair implements Comparable<KVPair> {

    // Instance variables:
    protected String key = null;
    protected String value = null;

    public KVPair() {
        super();
    }

    public KVPair(String k, String v) {
        super();
        this.key = k;
        this.value = v;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int compareTo(KVPair another) {

        return this.getKey().compareTo(another.getKey());
    }
}
