package com.pixelbattle.extender.primitives;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

//@JsonDeserialize(using = LinkDeserializer.class)
public class Link {
    @JsonProperty("$numberLong")
    private String value;

    public Link() {}

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
