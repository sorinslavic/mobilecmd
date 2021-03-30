package com.sorin.mobilecmd.desktopcmd;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.sorin.mobilecmd.desktopcmd.util.DesktopCMDPropertiesFacade;
import com.sorin.mobilecmd.desktopcmd.ws.WSUtil;
import com.sorin.mobilecmd.desktopcmd.ws.exception.RequiredParameterException;
import com.sorin.mobilecmd.desktopcmd.ws.server.WaitForServer;


public class DesktopCMD {	
	private static final Logger log = Logger.getLogger(DesktopCMD.class);

	private JFrame frmMobileCmd;
	private JTextField username;
	private JPasswordField password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		log.debug("START");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DesktopCMD window = new DesktopCMD();
					window.frmMobileCmd.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		log.debug("waitforserver");
		
		(new Thread(new WaitForServer())).start();
	}

	/**
	 * Create the application.
	 */
	public DesktopCMD() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		log.debug("initialize - open Login window");
		
		frmMobileCmd = new JFrame();
		frmMobileCmd.setTitle("Mobile CMD - Desktop Client");
		frmMobileCmd.setBounds(100, 100, 450, 300);
		frmMobileCmd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMobileCmd.getContentPane().setLayout(null);
		
		final JLabel lblUserName = new JLabel("User name");
		lblUserName.setFont(new Font("Verdana", Font.BOLD, 11));
		lblUserName.setBounds(86, 102, 85, 21);
		frmMobileCmd.getContentPane().add(lblUserName);
		
		final JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Verdana", Font.BOLD, 11));
		lblPassword.setBounds(96, 134, 85, 21);
		frmMobileCmd.getContentPane().add(lblPassword);
		
		username = new JTextField();
		username.setBounds(181, 103, 175, 20);
		frmMobileCmd.getContentPane().add(username);
		username.setColumns(10);
		
		final JLabel lblMobileCmd = new JLabel("Mobile CMD");
		lblMobileCmd.setFont(new Font("Verdana", Font.BOLD, 12));
		lblMobileCmd.setForeground(new Color(0, 102, 153));
		lblMobileCmd.setBounds(113, 43, 85, 27);
		frmMobileCmd.getContentPane().add(lblMobileCmd);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String usernamex = username.getText();
					char[] passwordx = password.getPassword();
					
					if (usernamex.isEmpty())
						throw new RequiredParameterException(lblUserName.getText());
					if (passwordx.length == 0)
						throw new RequiredParameterException(lblPassword.getText());
				
					if (WSUtil.login(usernamex, passwordx)) {
						JFrame frame = new FileSelect();
						frame.setVisible(true);
						frmMobileCmd.dispose();
					}
				} catch (RequiredParameterException rpe) {
					JOptionPane.showMessageDialog(frmMobileCmd, rpe.getMessage());
					rpe.printStackTrace();
					
					return;
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frmMobileCmd, ex.getMessage());
					ex.printStackTrace();
				}
			}
		});
		btnLogin.setBounds(181, 172, 65, 23);
		frmMobileCmd.getContentPane().add(btnLogin);
		
		JButton btnRegister = new JButton("<HTML><u>Register</u></HTML>");
		btnRegister.setBackground(Color.WHITE);
		
		btnRegister.setBorderPainted(false);
		btnRegister.setOpaque(false);

		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Desktop.isDesktopSupported()) {
					try {
						Desktop.getDesktop().browse(DesktopCMDPropertiesFacade.getRegistrationPath());
					} catch (IOException ioe) {						
					} catch (URISyntaxException urise) {
					}
				}
			}
		});
		btnRegister.setToolTipText("Go to web page Registration");
		btnRegister.setBounds(260, 172, 85, 23);
		frmMobileCmd.getContentPane().add(btnRegister);
		
		password = new JPasswordField();
		password.setBounds(181, 135, 175, 20);
		frmMobileCmd.getContentPane().add(password);
	}
}
