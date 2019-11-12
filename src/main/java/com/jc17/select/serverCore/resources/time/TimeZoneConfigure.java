package com.jc17.select.serverCore.resources.time;

import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TimeZoneConfigure extends Configuration {
    @NotEmpty
    @JsonProperty
    private String defaultTimezone;

    public String getDefaultTimezone() {
        return defaultTimezone;
    }

}
