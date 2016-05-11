package com.teapopo.life.model;

/**
 * Created by louiszgm on 2016/4/20 0020.
 */
public class PostKeyValue {
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

    private String key;
    private String value;

    public PostKeyValue(String key,String value){
        this.key = key;
        this.value = value;
    }
}
