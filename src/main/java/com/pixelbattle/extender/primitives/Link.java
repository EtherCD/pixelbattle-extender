package com.pixelbattle.extender.primitives;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Link {
    @JsonProperty("$numberLong")
    private final String value;

    public Link(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
