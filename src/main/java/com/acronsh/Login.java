package com.acronsh;

import org.jivesoftware.smack.AbstractXMPPConnection;

public class Login {
    public static void main(String[] args) {
        AbstractXMPPConnection conn = null;
        String account = "test";
        String password = "123456";
        try {
            // 登录
            conn = GetXMPPConnection.getConnection();
            conn.login(account, password);
            System.out.println("登录成功");
        } catch (Exception e) {
            // SASLError using SCRAM-SHA-1: not-authorized 密码错或者用户不存在都是这个
            e.printStackTrace();
        } finally {
            GetXMPPConnection.closeConnection(conn);
        }
    }
}