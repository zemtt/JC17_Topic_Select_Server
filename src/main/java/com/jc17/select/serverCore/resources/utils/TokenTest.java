package com.jc17.select.serverCore.resources.utils;

import java.security.MessageDigest;
import java.util.Date;

public class TokenTest {

    public static void main(String[] args) {
        Token t = new Token();
        String tok = t.makeAToken("asdfghjkl");
        String[] res;
        res = t.analyzeAToken(tok);
        System.out.println(res[0]+"\n"+res[1]);
    }
}
