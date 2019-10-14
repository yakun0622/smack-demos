package com.acronsh;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.chat2.ChatManager;

public class SendMessage{
    public static void main(String[] args) {
        String account = "test";
        String password = "123456";
        AbstractXMPPConnection conn = null;
        try {
            conn = GetXMPPConnection.getConnection();
            conn.login(account, password);
            // 发送消息
            ChatManager chatmanager = ChatManager.getInstanceFor(conn);
//            Chat newChat = chatmanager.createChat("java3@admin-PC");
//            newChat.sendMessage("java3 , my name is java");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            GetXMPPConnection.closeConnection(conn);;
        }
    }

}