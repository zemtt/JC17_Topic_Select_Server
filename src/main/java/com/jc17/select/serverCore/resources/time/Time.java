package com.jc17.select.serverCore.resources.time;

public class Time extends Object {
    private String t;

    Time(String t){
        this.t = t;
    }

    @Override
    public String toString() {
        return t.toString();
    }
}
