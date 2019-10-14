package com.acronsh;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import java.io.IOException;

public class Login {
    public static void main(String[] args) throws InterruptedException, XMPPException, SmackException, IOException {
        String account = "java1";
        String password = "123456";
        SmackUtil smackUtil = new SmackUtil();
        smackUtil.login(account, password);
        smackUtil.close();
    }
}