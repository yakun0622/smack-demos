package com.acronsh;

public class Register {

    public static void main(String[] args) throws Exception {
        String account = "java1";
        String password = "123456";

        SmackUtil smackUtil = new SmackUtil();
        smackUtil.registerAccount(account, password);

    }

}