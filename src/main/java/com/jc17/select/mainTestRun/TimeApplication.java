package com.jc17.select.mainTestRun;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;

public class TimeApplication extends Application<TimeZoneConfigure> {
    public static void main(String[] args) throws Exception {
        new TimeApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap timezoneConfigurationBootstrap) {
    }

    @Override
    public void run(TimeZoneConfigure timeZoneConfigure, io.dropwizard.setup.Environment environment) throws Exception {
        final TimeResource resource = new TimeResource(timeZoneConfigure.getDefaultTimezone());
        environment.jersey().register(resource);
    }
}
