package com.acronsh;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jxmpp.jid.parts.Localpart;

import java.io.IOException;

public class Register {

    public static void main(String[] args) throws IOException, XMPPException, SmackException, InterruptedException {
        String account = "java";
        String password = "123456";

        AbstractXMPPConnection conn = GetXMPPConnection.getConnection();
        try {
            AccountManager.sensitiveOperationOverInsecureConnectionDefault(true);
            AccountManager.getInstance(conn).createAccount(Localpart.from(account), password);
            System.out.println("注册成功");
        } catch (XMPPException | SmackException e) {
            e.printStackTrace();
        }finally {
            GetXMPPConnection.closeConnection(conn);
            System.out.println("断开了连接");
        }

    }

}