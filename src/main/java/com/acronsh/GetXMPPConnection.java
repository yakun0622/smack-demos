package com.acronsh;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class GetXMPPConnection {
    //获取连接
    public static AbstractXMPPConnection getConnection() {
        XMPPTCPConnectionConfiguration.Builder config = XMPPTCPConnectionConfiguration.builder();
        XMPPTCPConnection connection = null;
        //设置openfire主机IP
        try {
            config.setHostAddress(InetAddress.getByName("im.wanwan.com"));
            //设置openfire服务器名称
            config.setXmppDomain("im.wanwan.com");
            //设置端口号：默认5222
            config.setPort(5222);
            //禁用SSL连接
            config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled).setCompressionEnabled(false);

            //设置离线状态
            config.setSendPresence(false);
            //需要经过同意才可以添加好友
            Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.manual);
            connection = new XMPPTCPConnection(config.build());
            connection.connect();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("成功链接上tigase 服务器");
        return connection;
    }

    //断开连接
    public static void closeConnection(AbstractXMPPConnection connection) {
        if (connection != null) {
            try {
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}