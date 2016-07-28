package com.shop.client;

import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.shop.model.Info;
import com.shop.util.DateFormatUtil;
import com.shop.util.EnumInfoType;

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTextField textField;
	private JTextArea textArea;
	private JLabel lblNewLabel;
	private Info info;
	private JList list;
	private DefaultListModel clientListModel;
	private UserService userService;
	private ClientFrame thisClass;
	
	
	public ClientFrame(Info info, UserService userService) {
		super();
		this.info = info;
		this.userService = userService;
		thisClass = this;
		setIconImage(Toolkit.getDefaultToolkit().getImage("E:\\shop\\MyEclipse\\JavaWeb_140\\src\\com\\shop\\chat\\res\\6.png"));
		initialize();
		lblNewLabel.setText("当前用户: " + info.getToUser());
		setData(info);
		startFrame();
		
		
	}

	private void startFrame() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					SwingUtilities.updateComponentTreeUI(thisClass);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
	}

	public void setData(final Info info) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(textArea.getText()).append("时间: ").append(info.getSendTime()).append("\n")
		.append(info.getFromUser()).append("说:").append(info.getContent()).append("\n\n");
		textArea.setText(stringBuffer.toString());
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Info exitInfo = new Info();
				exitInfo.setFromUser(info.getToUser());
				exitInfo.setInfoType(EnumInfoType.DEL_USER);
				userService.sendInfo(exitInfo);
				userService.setFlag(false);
				ClientFrame.this.dispose();
			}
		});
	}
	
	public void addUserToList(Info info) {
		clientListModel.addElement(info.getContent());
	}
	
	public void loadUserToList(Info info) {
		String[] names = info.getContent().split(",");
		for (String name : names) {
			clientListModel.addElement(name);
		}
	}

	public void delUserToList(Info info) {
		clientListModel.removeElement(info.getContent());
		info.setContent(info.getContent() + "下线了");
		//info.setSendTime(DateFormatUtil.getTime(new Date()));
		setData(info);
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(480, 320);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		int x = (int)((d.getWidth() - 300)/2);
		int y = (int)((d.getHeight() - 200)/2);
		this.setLocation(x, y);
		this.setContentPane(getJContentPane());
		this.setTitle("聊天-客户端");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			
			lblNewLabel = new JLabel("\u6807\u9898\u680F");
			jContentPane.add(lblNewLabel, BorderLayout.NORTH);
			
			JPanel panel = new JPanel();
			jContentPane.add(panel, BorderLayout.EAST);
			panel.setLayout(new BorderLayout(0, 0));
			
			JLabel lblNewLabel_1 = new JLabel("---\u7528\u6237\u5217\u8868----");
			panel.add(lblNewLabel_1, BorderLayout.NORTH);
			
			JScrollPane scrollPane = new JScrollPane();
			panel.add(scrollPane, BorderLayout.CENTER);
			
			list = new JList();
			list.setVisibleRowCount(16);
			scrollPane.setColumnHeaderView(list);
			clientListModel = new DefaultListModel();
			clientListModel.addElement("所有人");
			list.setModel(clientListModel);
			
			JScrollPane scrollPane_1 = new JScrollPane();
			jContentPane.add(scrollPane_1, BorderLayout.CENTER);
			
			//JTextArea textArea = new JTextArea();
			textArea = new JTextArea();
			scrollPane_1.setViewportView(textArea);
			
			JPanel panel_1 = new JPanel();
			jContentPane.add(panel_1, BorderLayout.SOUTH);
			panel_1.setLayout(new BorderLayout(0, 0));
			
			JLabel lblNewLabel_2 = new JLabel("\u8BF7\u8F93\u5165");
			panel_1.add(lblNewLabel_2, BorderLayout.WEST);
			
			textField = new JTextField();
			panel_1.add(textField, BorderLayout.CENTER);
			textField.setColumns(10);
			
			JButton btnNewButton = new JButton("\u53D1\u9001");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String content = textField.getText().trim();
					if ("".equals(content)) {
						JOptionPane.showMessageDialog(ClientFrame.this, "不能发送空消息");
						return;
					}
					String toUser = list.getSelectedValue().toString();
					if ("".equals(toUser) || toUser == null) {
						JOptionPane.showMessageDialog(ClientFrame.this, "请选择聊天对象");
						return;
					}
					
					Info sendinfo = new Info();
					sendinfo.setContent(content);
					sendinfo.setFromUser(info.getToUser());
					sendinfo.setToUser(toUser);
					
					if ("所有人".equals(toUser)) {
						sendinfo.setInfoType(EnumInfoType.SEND_ALL);
					} else {
						sendinfo.setInfoType(EnumInfoType.SEND_INFO);
					}
					
					sendinfo.setSendTime(DateFormatUtil.getTime(new Date()));
					userService.sendInfo(sendinfo);
					textField.setText("");
				}
			});
			panel_1.add(btnNewButton, BorderLayout.EAST);
		}
		return jContentPane;
	}

}
