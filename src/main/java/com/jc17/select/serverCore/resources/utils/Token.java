package com.jc17.select.serverCore.resources.utils;

import java.util.Date;

public class Token {

    private static String encode(String str){
        return str;
    }

    private static String decode(String str){
        return str;
    }

    public static String makeAToken(String userId) {
        Long t = new Date().getTime();
        return encode(t.toString()+userId);
    }

    public static String[] analyzeAToken(String token){
        String[] result = new String[2];
        String p_token = decode(token);
        result[0] = p_token.substring(0,13);
        result[1] = p_token.substring(13);
        return result;
    }
}
