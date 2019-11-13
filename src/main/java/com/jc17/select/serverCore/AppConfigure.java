package com.jc17.select.serverCore;

import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AppConfigure extends Configuration {
    @NotEmpty
    @JsonProperty
    private String defaultTimezone;

    public String getDefaultTimezone() {
        return defaultTimezone;
    }

}
