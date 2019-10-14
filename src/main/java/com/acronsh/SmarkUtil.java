package com.acronsh;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterGroup;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jivesoftware.smackx.pubsub.*;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.jivesoftware.smackx.xdata.packet.DataForm;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Localpart;

import java.util.*;

public class SmarkUtil {

	private XMPPTCPConnection connection;

	private String userName;

	private String password;

	private String xmppDomain;

	private String serverName;

	public SmarkUtil(String userName, String password, String xmppDomain, String serverName) {
		this.userName = userName;
		this.password = password;
		this.xmppDomain = xmppDomain;
		this.serverName = serverName;
		try {
			if (connection == null) {
				getConnection(userName, password, xmppDomain, serverName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取连接
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public void getConnection(String userName, String password, String xmppDomain, String serverName) throws Exception {
		try {
			XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
			configBuilder.setUsernameAndPassword(userName, password);
			configBuilder.setXmppDomain(xmppDomain);
			configBuilder.setSecurityMode(XMPPTCPConnectionConfiguration.SecurityMode.disabled);

			connection = new XMPPTCPConnection(configBuilder.build());
			// 连接服务器
			connection.connect();
			// 登录服务器
			connection.login();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建一个新用户
	 * 
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @param attr
	 *            用户资料
	 * @return
	 * @throws Exception
	 */
	public boolean registerAccount(String userName, String password, Map<String, String> attr) throws Exception {
		AccountManager manager = AccountManager.getInstance(connection);
		manager.sensitiveOperationOverInsecureConnection(true);
		Localpart l_username = Localpart.from(userName);
		if (attr == null) {
			manager.createAccount(l_username, password);
		} else {
			manager.createAccount(l_username, password, attr);
		}

		return true;
	}

	/**
	 * 修改当前登陆用户密码
	 * 
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public boolean changePassword(String password) throws Exception {
		AccountManager manager = AccountManager.getInstance(connection);
		manager.sensitiveOperationOverInsecureConnection(true);
		manager.changePassword(password);
		return true;
	}

	/**
	 * 删除当前登录用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean deleteAccount() throws Exception {
		AccountManager manager = AccountManager.getInstance(connection);
		manager.sensitiveOperationOverInsecureConnection(true);
		manager.deleteAccount();
		return true;
	}

	/**
	 * 获取用户属性名称
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getAccountInfo() throws Exception {
		List<String> list = new ArrayList<String>();
		AccountManager manager = AccountManager.getInstance(connection);
		manager.sensitiveOperationOverInsecureConnection(true);
		Set<String> set = manager.getAccountAttributes();
		list.addAll(set);
		return list;
	}

	/**
	 * 获取所有组
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<RosterGroup> getGroups() throws Exception {
		List<RosterGroup> grouplist = new ArrayList<RosterGroup>();
		Roster roster = Roster.getInstanceFor(connection);
		Collection<RosterGroup> rosterGroup = roster.getGroups();
		Iterator<RosterGroup> i = rosterGroup.iterator();
		while (i.hasNext()) {
			grouplist.add(i.next());
		}
		return grouplist;
	}

	/**
	 * 添加分组
	 * 
	 * @param groupName
	 * @return
	 * @throws Exception
	 */
	public boolean addGroup(String groupName) throws Exception {
		Roster roster = Roster.getInstanceFor(connection);
		roster.createGroup(groupName);
		return true;

	}

	/**
	 * 获取指定分组的好友
	 * 
	 * @param groupName
	 * @return
	 * @throws Exception
	 */
	public List<RosterEntry> getEntriesByGroup(String groupName) throws Exception {
		Roster roster = Roster.getInstanceFor(connection);
		List<RosterEntry> Entrieslist = new ArrayList<RosterEntry>();
		RosterGroup rosterGroup = roster.getGroup(groupName);
		Collection<RosterEntry> rosterEntry = rosterGroup.getEntries();
		Iterator<RosterEntry> i = rosterEntry.iterator();
		while (i.hasNext()) {
			Entrieslist.add(i.next());
		}
		return Entrieslist;
	}

	/**
	 * 获取全部好友
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<RosterEntry> getAllEntries() throws Exception {
		Roster roster = Roster.getInstanceFor(connection);
		List<RosterEntry> Entrieslist = new ArrayList<RosterEntry>();
		Collection<RosterEntry> rosterEntry = roster.getEntries();
		Iterator<RosterEntry> i = rosterEntry.iterator();
		while (i.hasNext()) {
			Entrieslist.add(i.next());
		}
		return Entrieslist;
	}

	/**
	 * 获取用户VCard信息
	 * 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public VCard getUserVCard(String userName) throws Exception {
		VCard vcard = new VCard();
		EntityBareJid jid = JidCreate.entityBareFrom(userName + "@" + this.serverName);
		vcard.load(connection, jid);
		return vcard;
	}

	/**
	 * 添加好友 无分组
	 * 
	 * @param userName
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public boolean addUser(String userName, String name) throws Exception {
		Roster roster = Roster.getInstanceFor(connection);
		EntityBareJid jid = JidCreate.entityBareFrom(userName + "@" + this.serverName);
		roster.createEntry(jid, name, null);
		return true;

	}

	/**
	 * 添加好友 有分组
	 * 
	 * @param userName
	 * @param name
	 * @param groupName
	 * @return
	 * @throws Exception
	 */
	public boolean addUser(String userName, String name, String groupName) throws Exception {
		Roster roster = Roster.getInstanceFor(connection);
		EntityBareJid jid = JidCreate.entityBareFrom(userName + "@" + this.serverName);
		roster.createEntry(jid, name, new String[] { groupName });
		return true;

	}

	/**
	 * 删除好友
	 * 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public boolean removeUser(String userName) throws Exception {
		Roster roster = Roster.getInstanceFor(connection);
		EntityBareJid jid = JidCreate.entityBareFrom(userName + "@" + this.serverName);
		RosterEntry entry = roster.getEntry(jid);
		System.out.println("删除好友：" + userName);
		System.out.println("User." + roster.getEntry(jid) == null);
		roster.removeEntry(entry);

		return true;

	}

	/**
	 * 创建发布订阅节点
	 * 
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	public boolean createPubSubNode(String nodeId) throws Exception {
		PubSubManager mgr = PubSubManager.getInstance(connection);
		// Create the node
		LeafNode leaf = mgr.createNode(nodeId);
		ConfigureForm form = new ConfigureForm(DataForm.Type.submit);
		form.setAccessModel(AccessModel.open);
		form.setDeliverPayloads(true);
		form.setNotifyRetract(true);
		form.setPersistentItems(true);
		form.setPublishModel(PublishModel.open);
		form.setMaxItems(10000000);// 设置最大的持久化消息数量

		leaf.sendConfigurationForm(form);
		return true;
	}

	/**
	 * 创建发布订阅节点
	 * 
	 * @param nodeId
	 * @param title
	 * @return
	 * @throws Exception
	 */
	public boolean createPubSubNode(String nodeId, String title) throws Exception {
		PubSubManager mgr = PubSubManager.getInstance(connection);
		// Create the node
		LeafNode leaf = mgr.createNode(nodeId);
		ConfigureForm form = new ConfigureForm(DataForm.Type.submit);
		form.setAccessModel(AccessModel.open);
		form.setDeliverPayloads(true);
		form.setNotifyRetract(true);
		form.setPersistentItems(true);
		form.setPublishModel(PublishModel.open);
		form.setTitle(title);
		form.setBodyXSLT(nodeId);
		form.setMaxItems(10000000);// 设置最大的持久化消息数量
		form.setMaxPayloadSize(1024*12);//最大的有效载荷字节大小
		leaf.sendConfigurationForm(form);
		return true;
	}

	public boolean deletePubSubNode(String nodeId) throws Exception {
		PubSubManager mgr = PubSubManager.getInstance(connection);
		mgr.deleteNode(nodeId);
		return true;
	}

	/**
	 * 发布消息
	 * 
	 * @param nodeId
	 *            主题ID
	 * @param eventId
	 *            事件ID
	 * @param messageType
	 *            消息类型：publish（发布）/receipt（回执）/state（状态）
	 * @param messageLevel
	 *            0/1/2
	 * @param messageSource
	 *            消息来源
	 * @param messageCount
	 *            消息数量
	 * @param packageCount
	 *            总包数
	 * @param packageNumber
	 *            当前包数
	 * @param createTime
	 *            创建时间 2018-06-07 09:43:06
	 * @param messageContent
	 *            消息内容
	 * @return
	 * @throws Exception
	 */
	public boolean publish(String nodeId, String eventId, String messageType, int messageLevel, String messageSource,
			int messageCount, int packageCount, int packageNumber, String createTime, String messageContent)
			throws Exception {

		if (messageContent.length() > 1024 * 10) {
			throw new Exception("消息内容长度超出1024*10，需要进行分包发布");
		}
		PubSubManager mgr = PubSubManager.getInstance(connection);
		LeafNode node = null;

		node = mgr.getNode(nodeId);

		StringBuffer xml = new StringBuffer();
		xml.append("<pubmessage xmlns='pub:message'>");
		xml.append("<nodeId>" + nodeId + "</nodeId>");
		xml.append("<eventId>" + eventId + "</eventId>");
		xml.append("<messageType>" + messageType + "</messageType>");
		xml.append("<messageLevel>" + messageLevel + "</messageLevel>");
		xml.append("<messageSource>" + messageSource + "</messageSource>");
		xml.append("<messageCount>" + messageCount + "</messageCount>");
		xml.append("<packageCount>" + packageCount + "</packageCount>");
		xml.append("<packageNumber>" + packageNumber + "</packageNumber>");
		xml.append("<createTime>" + createTime + "</createTime>");
		xml.append("<messageContent>" + messageContent + "</messageContent>");
		xml.append("</pubmessage>");

		SimplePayload payload = new SimplePayload("pubmessage", "pub:message", xml.toString().toLowerCase());
		PayloadItem<SimplePayload> item = new PayloadItem<SimplePayload>(System.currentTimeMillis() + "", payload);
		node.publish(item);
		return true;
	}

	/**
	 * 订阅主题
	 * 
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	public boolean subscribe(String nodeId) throws Exception {

		PubSubManager mgr = PubSubManager.getInstance(connection);

		// Get the node
		LeafNode node = mgr.getNode(nodeId);
		SubscribeForm subscriptionForm = new SubscribeForm(DataForm.Type.submit);
		subscriptionForm.setDeliverOn(true);
		subscriptionForm.setDigestFrequency(5000);
		subscriptionForm.setDigestOn(true);
		subscriptionForm.setIncludeBody(true);

		List<Subscription> subscriptions = node.getSubscriptions();

		boolean flag = true;
		for (Subscription s : subscriptions) {
			if (s.getJid().toString().toLowerCase().equals(connection.getUser().asEntityBareJidString().toLowerCase())) {// 已订阅过
				flag = false;
				break;
			}
		}
		if (flag) {// 未订阅，开始订阅
			node.subscribe(userName + "@" + this.serverName, subscriptionForm);
		}
		return true;
	}

	/**
	 * 获取订阅的全部主题
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Subscription> querySubscriptions() throws Exception {
		PubSubManager mgr = PubSubManager.getInstance(connection);
		List<Subscription> subs = mgr.getSubscriptions();
		return subs;
	}

	/**
	 * 获取订阅节点的配置信息
	 * 
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	public ConfigureForm getConfig(String nodeId) throws Exception {
		PubSubManager mgr = PubSubManager.getInstance(connection);
		LeafNode node = mgr.getNode(nodeId);
		ConfigureForm config = node.getNodeConfiguration();
		return config;
	}

	/**
	 * 获取订阅主题的全部历史消息
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Item> queryHistoryMeassage() throws Exception {
		List<Item> result = new ArrayList<Item>();
		PubSubManager mgr = PubSubManager.getInstance(connection);
		List<Subscription> subs = mgr.getSubscriptions();
		if (subs != null && subs.size() > 0) {
			for (Subscription sub : subs) {
				String nodeId = sub.getNode();
				LeafNode node = mgr.getNode(nodeId);
				List<Item> list = node.getItems();
				result.addAll(list);
			}
		}

		/*
		 * for (Item item : result) { System.out.println(item.toXML()); }
		 */
		return result;
	}

	/**
	 * 获取指定主题的全部历史消息
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Item> queryHistoryMeassage(String nodeId) throws Exception {
		List<Item> result = new ArrayList<Item>();
		PubSubManager mgr = PubSubManager.getInstance(connection);

		LeafNode node = mgr.getNode(nodeId);
		List<Item> list = node.getItems();
		result.addAll(list);

		/*
		 * for (Item item : result) { System.out.println(item.toXML()); }
		 */
		return result;
	}

	/**
	 * 获取指定主题指定数量的历史消息
	 * 
	 * @param nodeId
	 * @param num
	 * @return
	 * @throws Exception
	 */
	public List<Item> queryHistoryMeassage(String nodeId, int num) throws Exception {
		List<Item> result = new ArrayList<Item>();
		PubSubManager mgr = PubSubManager.getInstance(connection);

		LeafNode node = mgr.getNode(nodeId);
		List<Item> list = node.getItems(num);
		result.addAll(list);

		/*
		 * for (Item item : result) { System.out.println(item.toXML()); }
		 */
		return result;
	}

	/**
	 * 向指定用户发送消息
	 * 
	 * @param username
	 * @param message
	 * @throws Exception
	 */
	public void sendMessage(String username, String message) throws Exception {
		ChatManager chatManager = ChatManager.getInstanceFor(connection);
		EntityBareJid jid = JidCreate.entityBareFrom(username + "@" + serverName);
		Chat chat = chatManager.createChat(jid);
		Message newMessage = new Message();
		newMessage.setBody(message);
		chat.sendMessage(newMessage);
	}

	/**
	 * 添加聊天消息监听
	 * 
	 * @param chatManagerListener
	 * @throws Exception
	 */
	public void addChatMessageListener(ChatManagerListener chatManagerListener) throws Exception {
		ChatManager chatManager = ChatManager.getInstanceFor(connection);
		chatManager.addChatListener(chatManagerListener);
	}

	/**
	 * 断开连接
	 */
	public void close() {
		connection.disconnect();
	}

}