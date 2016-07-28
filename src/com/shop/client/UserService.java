package com.shop.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.DefaultListModel;
import javax.swing.JTextArea;

import com.shop.model.Info;
import com.shop.util.EnumInfoType;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Xpp3Driver;

public class UserService {
	private Socket socket;
	private DataInputStream inputStream = null;
	private DataOutputStream outputStream = null;
	private JTextArea jTextArea = null;
	private DefaultListModel listModel = null;
	
	private ClientFrame clientFrame = null;
	private LoginFrame loginFrame = null;
	private XStream xStream = null;
	
	public UserService() {
		this(null);
	}
	
	public UserService(LoginFrame loginFrame) {
		this.loginFrame = loginFrame;
		xStream = new XStream(new Xpp3Driver());
	}
	
	public void login(final String useeName, final String passWord, final String ip, final int port) {
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					socket = new Socket(ip, port);
					
					outputStream = new DataOutputStream(socket.getOutputStream());
					Info info = new Info();
					info.setFromUser(useeName);
					info.setInfoType(EnumInfoType.LOGIN);
					String xmlString = xStream.toXML(info);
					outputStream.writeUTF(xmlString);
					outputStream.flush();
					Thread thread = new Thread(new UserThread());
					thread.setDaemon(true); // 守护进程
					thread.start();
					
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		thread.start();
	}
	
	public void sendInfo(final Info info) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String xmlString = xStream.toXML(info);
				try {
					outputStream.writeUTF(xmlString);
					outputStream.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	private boolean flag = true;
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	class UserThread implements Runnable {
		public UserThread() {
			flag = true;
		}
		
		@Override
		public void run() {
			while (flag) {
				try {
					inputStream = new DataInputStream(socket.getInputStream());
					String xmlString = inputStream.readUTF();
					Info info = (Info)xStream.fromXML(xmlString);
					
					System.out.println(info);
					
					switch (info.getInfoType()) {
					case WELCOME:
						clientFrame = new ClientFrame(info, UserService.this);
						loginFrame.dispose();
						break;
						
					case SEND_INFO:
						clientFrame.setData(info);
						break;
						
					case SEND_ALL:
						
						break;
						
					case ADD_USER:
						clientFrame.addUserToList(info);
						break;
						
					case DEL_USER:
						clientFrame.delUserToList(info);
						break;
						
					case LOAD_USER:
						clientFrame.loadUserToList(info);
						break;

					default:
						break;
					}
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
