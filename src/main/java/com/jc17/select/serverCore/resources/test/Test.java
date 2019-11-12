package com.jc17.select.serverCore.resources.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jc17.select.serverCore.userAuth.SysUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


class myClass {
    private Map<String, Object> myScore;

    public myClass() {
        // Jackson deserialization
    }

    public myClass(String name, Integer score) {
        myScore.put("name", name);
        myScore.put("score", score);
    }

    @JsonProperty
    public Map<String, Object> getMyScore(){
        return  myScore;
    }
}

public class Test {
    private String id;
    private String name;
    private List<Map> classes;

    public Test() {
        // Jackson deserialization
    }

    public Test(SysUser user) {
        name = user.getName();
        id = user.getName() + "heihei";
        classes = new ArrayList<Map>();
        classes.add(new myClass("Math", 100).getMyScore());
        classes.add(new myClass("Engl", 98).getMyScore());
    }

    @JsonProperty
    public String getId() {
        return id;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public List<Map> getClasses(){
        return classes;
    }
}
