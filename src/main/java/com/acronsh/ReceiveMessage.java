package com.acronsh;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.ChatManager;

import java.io.IOException;

public class ReceiveMessage{
    public static void main(String[] args) throws IOException, SmackException, XMPPException {
        String account = "java";
        String password = "123456";
        AbstractXMPPConnection conn = GetXMPPConnection.getConnection();
        try {
            conn.connect();
            conn.login(account, password);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ChatManager chatmanager = ChatManager.getInstanceFor(conn);
        System.out.println("等待接受消息...");

//        chatmanager.addIncomingListener((chat) -> chat.addMessageListener((chat1, msg) -> {
//            if (null != msg.getBody()) {
//                String friendName = msg.getFrom().split("@")[0];
//                String content = msg.getBody();
//                System.out.println(friendName +" ：" + content);
//            }
//        }));
        while (true);
    }
}