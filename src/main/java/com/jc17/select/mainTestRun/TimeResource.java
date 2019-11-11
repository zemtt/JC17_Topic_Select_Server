package com.jc17.select.mainTestRun;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.common.base.Optional;

@Path("/time")
@Produces(MediaType.APPLICATION_JSON)
public class TimeResource {
    private final String defaultTimezone;

    public TimeResource(String defaultTimezone) {
        this.defaultTimezone = defaultTimezone;
    }

    @GET
    public String getTime(@QueryParam("timezone") Optional timezone) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("配置文件中配置:" + defaultTimezone);//配置文件中配置
        TimeZone timeZone = null;
        if (timezone.isPresent()) {
            timeZone = TimeZone.getTimeZone(timezone.get().toString());
            System.out.println("请求中配置:" + timezone.get().toString());
        } else {
            timeZone = TimeZone.getTimeZone(defaultTimezone);
        }
        formatter.setTimeZone(timeZone);
        String formatted = formatter.format(new Date());
        return new Time(formatted).toString();
    }
}