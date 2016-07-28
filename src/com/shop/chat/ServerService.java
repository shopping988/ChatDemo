package com.shop.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.DefaultListModel;
import javax.swing.JTextArea;

import com.shop.model.Info;
import com.shop.model.User;
import com.shop.util.DateFormatUtil;
import com.shop.util.EnumInfoType;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Xpp3Driver;

public class ServerService {
	ExecutorService executorService = Executors.newFixedThreadPool(10);
	private Vector<UserServiceThread> userServiceThreads = new Vector<UserServiceThread>();
	private JTextArea jTextArea;
	private DefaultListModel listModel;
	
	public ServerService(JTextArea jTextArea, DefaultListModel listModel) {
		this.jTextArea = jTextArea;
		this.listModel = listModel;
	}
	
	public void startServer() {
		boolean flag = true;
		
		try {
			ServerSocket serverSocket = new ServerSocket(8090);
			while (flag) {
				Socket client = serverSocket.accept();
				UserServiceThread userServiceThread = new UserServiceThread(client);
				userServiceThreads.add(userServiceThread);
				executorService.execute(userServiceThread);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class UserServiceThread implements Runnable {
		private Socket clientSocket;
		private DataInputStream inputStream = null;
		private DataOutputStream outputStream = null;
		private boolean flag = true;
		private User currentUser;
		
		public UserServiceThread(Socket clientSocket) {
			this.clientSocket = clientSocket;
			try {
				inputStream = new DataInputStream(clientSocket.getInputStream());
				outputStream = new DataOutputStream(clientSocket.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		@Override
		public void run() {
			XStream xStream = new XStream(new Xpp3Driver());
			Info info = null;
			while (flag) {
				try {
					String infoXML = inputStream.readUTF();
					
					info = (Info)xStream.fromXML(infoXML);
					switch (info.getInfoType()) {
					case LOGIN:
						jTextArea.setText(jTextArea.getText() + "\n欢迎你, " + info.getFromUser());
						
						Info loginInfo = new Info();
						loginInfo.setContent("欢迎你, " + info.getFromUser());
						loginInfo.setFromUser("管理员");
						loginInfo.setSendTime(DateFormatUtil.getTime(new Date()));
						loginInfo.setToUser(info.getFromUser());
						loginInfo.setInfoType(EnumInfoType.WELCOME);
						currentUser = new User();
						currentUser.setName(info.getFromUser());
						outputStream.writeUTF(xStream.toXML(loginInfo));
						outputStream.flush();
						listModel.addElement(info.getFromUser());
						
						StringBuffer stringBuffer = new StringBuffer();

						for (UserServiceThread user : userServiceThreads) {
							//stringBuffer.append(user.currentUser.getName()).append(",");
							Info updateUserListInfo = new Info();
							updateUserListInfo.setContent(info.getFromUser());
							updateUserListInfo.setInfoType(EnumInfoType.ADD_USER);
							if (!info.getFromUser().equals(user.currentUser.getName())) {
								stringBuffer.append(user.currentUser.getName()).append(",");
							    user.outputStream.writeUTF(xStream.toXML(updateUserListInfo));
							    user.outputStream.flush();
							}
						}
						
						Info updateClientInfo = new Info();
						updateClientInfo.setContent(stringBuffer.toString());
						updateClientInfo.setInfoType(EnumInfoType.LOAD_USER);
						outputStream.writeUTF(xStream.toXML(updateClientInfo));
						outputStream.flush();
						break;

					case SEND_INFO:
						for (UserServiceThread user : userServiceThreads) {
							if (user.currentUser.getName().equals(info.getToUser())) {
								user.outputStream.writeUTF(xStream.toXML(info));
								user.outputStream.flush();
								break;
							}
						}	
						break;
						
					case SEND_ALL:
						for (UserServiceThread user : userServiceThreads) {
							if (!user.currentUser.getName().equals(info.getFromUser())) {
								info.setInfoType(EnumInfoType.SEND_INFO);
								user.outputStream.writeUTF(xStream.toXML(info));
								user.outputStream.flush();
							}
						}	
						break;
						
					case ADD_USER:
						
						break;
						
                    case DEL_USER:
                    	exitUser(xStream, info);
						break;
						
					case WELCOME:
						
						break;
						
					default:
						break;
					}
					
				} catch (IOException e) {
					e.printStackTrace();
					try {
						exitUser(xStream, info);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			
		}
		
		private void exitUser(XStream xStream, Info info) throws IOException {
			jTextArea.setText(jTextArea.getText() + "\n" + currentUser.getName() + "下线了");
			listModel.removeElement(currentUser.getName());
			for (UserServiceThread user : userServiceThreads) {
				if (!user.currentUser.getName().equals(info.getFromUser())) {
					info.setToUser(user.currentUser.getName());
					info.setContent(info.getFromUser());
					info.setFromUser("管理员");
					info.setSendTime(DateFormatUtil.getTime(new Date()));
					user.outputStream.writeUTF(xStream.toXML(info));
					user.outputStream.flush();
				}
			}
			
			flag = false;
			userServiceThreads.remove(UserServiceThread.this);
		}
		
	}
}
