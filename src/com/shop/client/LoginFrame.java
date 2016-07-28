package com.shop.client;

import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPasswordField;

import com.shop.model.Info;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTextField textField;
	private JPasswordField passwordField;
	private JTextField ipTextField;
	private JTextField portTextField;
	


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				LoginFrame thisClass = new LoginFrame();
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
	public LoginFrame() {
		super();
		setIconImage(Toolkit.getDefaultToolkit().getImage("E:\\shop\\MyEclipse\\JavaWeb_140\\src\\com\\shop\\chat\\res\\6.png"));
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(350, 200);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		int x = (int)((d.getWidth() - 350)/2);
		int y = (int)((d.getHeight() - 200)/2);
		this.setLocation(x, y);
		this.setContentPane(getJContentPane());
		this.setTitle("\u767B\u5F55");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridLayout(4, 1, 50, 5));
			
			JPanel panel = new JPanel();
			jContentPane.add(panel);
			
			JLabel lblNewLabel = new JLabel("\u7528\u6237\u8D26\u53F7:");
			panel.add(lblNewLabel);
			
			textField = new JTextField();
			panel.add(textField);
			textField.setColumns(28);
			
			JPanel panel_1 = new JPanel();
			jContentPane.add(panel_1);
			
			JLabel lblNewLabel_1 = new JLabel("\u7528\u6237\u5BC6\u7801:");
			panel_1.add(lblNewLabel_1);
			
			passwordField = new JPasswordField();
			passwordField.setColumns(28);
			panel_1.add(passwordField);
			
			JPanel panel_3 = new JPanel();
			jContentPane.add(panel_3);
			
			JLabel lblNewLabel_2 = new JLabel("\u670D\u52A1\u5668\u5730\u5740:");
			panel_3.add(lblNewLabel_2);
			
			ipTextField = new JTextField();
			panel_3.add(ipTextField);
			ipTextField.setColumns(24);
			ipTextField.setText("192.168.1.192");
			
			JLabel lblNewLabel_3 = new JLabel("\u7AEF\u53E3:");
			panel_3.add(lblNewLabel_3);
			
			portTextField = new JTextField();
			panel_3.add(portTextField);
			portTextField.setColumns(8);
			portTextField.setText("8090");
			
			JPanel panel_2 = new JPanel();
			jContentPane.add(panel_2);
			
			JButton btnNewButton = new JButton("\u767B\u5F55");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String userName = textField.getText().trim();
					String passWord = new String(passwordField.getPassword());
					String ipString = ipTextField.getText().trim();
					int port = Integer.parseInt(portTextField.getText().trim());
					UserService userService = new UserService(LoginFrame.this);
					userService.login(userName, passWord, ipString, port);
				}
			});
			panel_2.add(btnNewButton);
			
			JButton btnNewButton_1 = new JButton("\u9000\u51FA");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int value = JOptionPane.showConfirmDialog(LoginFrame.this, "真的要退出吗?", "退出", JOptionPane.YES_NO_OPTION);
				    if(value == JOptionPane.YES_OPTION) {
				    	System.exit(0);
				    }
				}
			});
			panel_2.add(btnNewButton_1);
		}
		return jContentPane;
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "SwingAction_1");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
