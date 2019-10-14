package com.acronsh;

public class ReceiveMessage {
    public static void main(String[] args) {
        String account = "java";
        String password = "123456";

        SmackUtil smarkUtil = new SmackUtil();
        try {
            smarkUtil.login(account, password);
            System.out.println("等待接受消息...");
            smarkUtil.addChatMessageListener((chat, createdLocally) -> {
                chat.addMessageListener((chat1, message) -> {
                    if (message != null) {
                        String friendName = message.getFrom().toString().split("@")[0];
                        String content = message.getBody();
                        System.out.println(friendName + " ：" + content);
                    }
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
            smarkUtil.close();
        }

        while (true) ;
    }
}