package com.acronsh;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.chat2.ChatManager;

public class SendMessage{
    public static void main(String[] args) {
        String account = "test";
        String password = "123456";
        AbstractXMPPConnection conn = null;
        try {
            SmarkUtil smarkUtil = new SmarkUtil(account, password, "im.wanwan.com", "im.wanwan.com");
//            Chat newChat = chatmanager.createChat("java3@admin-PC");
//            newChat.sendMessage("java3 , my name is java");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            GetXMPPConnection.closeConnection(conn);;
        }
    }

}