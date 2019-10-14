package com.acronsh;

import java.util.Scanner;

public class SendMessage{
    public static void main(String[] args) {
        String account = "test";
        String password = "123456";

        SmackUtil smarkUtil = new SmackUtil();
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入发送内容（回车发送，输入exit退出）：");
        try {
            smarkUtil.login(account, password);
            while (true){
                String content = scanner.nextLine();
                if ("exit".equals(content)){
                    break;
                }
                smarkUtil.sendMessage("java", content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            smarkUtil.close();
        }
    }

}