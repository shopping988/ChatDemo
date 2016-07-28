package com.shop.chat;

import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
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

import java.awt.Toolkit;

public class ServerFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTextField textField;
	private JTextArea textArea;
	private ServerService serverService;
	private DefaultListModel listModel = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ServerFrame thisClass = new ServerFrame();
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

	/**
	 * This is the default constructor
	 */
	public ServerFrame() {
		super();
		setIconImage(Toolkit.getDefaultToolkit().getImage("E:\\shop\\MyEclipse\\JavaWeb_140\\src\\com\\shop\\chat\\res\\6.png"));
		initialize();
		startServer();
	}

	private void startServer() {
		textArea.setText("服务器已经启动,在8090端口监听...");
		new Thread(new Runnable() {
			@Override
			public void run() {
				serverService = new ServerService(textArea, listModel);
				serverService.startServer();
			}
		}).start();
		
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
		int x = (int)((d.getWidth() - 480)/2);
		int y = (int)((d.getHeight() - 320)/2);
		this.setLocation(x, y);
		this.setContentPane(getJContentPane());
		this.setTitle("聊天-服务器端");
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
			
			JLabel lblNewLabel = new JLabel("\u6807\u9898\u680F");
			jContentPane.add(lblNewLabel, BorderLayout.NORTH);
			
			JPanel panel = new JPanel();
			jContentPane.add(panel, BorderLayout.EAST);
			panel.setLayout(new BorderLayout(0, 0));
			
			JLabel lblNewLabel_1 = new JLabel("---\u7528\u6237\u5217\u8868----");
			panel.add(lblNewLabel_1, BorderLayout.NORTH);
			
			JScrollPane scrollPane = new JScrollPane();
			panel.add(scrollPane, BorderLayout.CENTER);
			
			JList list = new JList();
			list.setVisibleRowCount(16);
			scrollPane.setColumnHeaderView(list);
			listModel = new DefaultListModel();
			listModel.addElement("所有人");
			list.setModel(listModel);
			
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
			panel_1.add(btnNewButton, BorderLayout.EAST);
		}
		return jContentPane;
	}

}
