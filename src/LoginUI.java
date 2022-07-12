


import javax.swing.JFrame;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.BoxLayout;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.Box;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.border.LineBorder;


import java.awt.Insets;
import javax.swing.SwingConstants;

public class LoginUI {
	private JFrame frmStarFurnitureLogin;
	private TransparentTextField txtusername;
	private JPasswordField txtpassword;
	
	// Temporary Username and Password as of now
	final private String adminUsername = "DefaultUsername";
	final private String adminPassword = "DefaultPassword";
	private int logincounter = 0;
	
	// Entry Point
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new LoginUI();
				} catch (Exception e) {	
					e.printStackTrace();
				}
			}
		});
	}

	public LoginUI() {
		initialize();
	}

	private void initialize() {
		
		// FRAME
		frmStarFurnitureLogin = new JFrame();
		frmStarFurnitureLogin.setVisible(true);
		frmStarFurnitureLogin.setTitle("Point of Sale");
		frmStarFurnitureLogin.setBounds(100, 100, 1450, 720);
		frmStarFurnitureLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmStarFurnitureLogin.getContentPane().setLayout(new BorderLayout(0, 0));
		
		// FANCY BLUE GRADIENT BACKGROUND WITH INTERACTIVE CONNECTING THE DOTS ANIMATION
		BackgroundPanel panel = new BackgroundPanel();
		frmStarFurnitureLogin.getContentPane().add(panel);
		panel.setLayout(new BorderLayout());
		panel.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				panel.mouseX = e.getX();
				panel.mouseY = e.getY();
			}
		});
		
		//BODY PANEL
		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.red);
		panel1.setPreferredSize(new Dimension(100, 100));
		panel1.setOpaque(false);
		panel.add(panel1, BorderLayout.SOUTH);
		
		//HEAD PANEL
		TransparentPane panel1_1 = new TransparentPane();
		panel1_1.setPreferredSize(new Dimension(100, 100));
		panel1_1.setBackground(Color.RED);
		panel1_1.setOpaque(false);
		panel.add(panel1_1, BorderLayout.NORTH);
		panel1_1.setLayout(new BoxLayout(panel1_1, BoxLayout.X_AXIS));
		
		// TEMP PANELS
		JPanel panel1_2 = new JPanel();
		panel1_2.setPreferredSize(new Dimension(200, 100));
		panel1_2.setBackground(Color.RED);
		panel1_2.setOpaque(false);
		panel.add(panel1_2, BorderLayout.WEST);
		
		JPanel panel1_3 = new JPanel();
		panel1_3.setPreferredSize(new Dimension(200, 100));
		panel1_3.setBackground(Color.RED);
		panel1_3.setOpaque(false);
		panel.add(panel1_3, BorderLayout.EAST);
		
		// BODY COMPONENTS
		JPanel panel1_4 = new JPanel();
		panel1_4.setPreferredSize(new Dimension(100, 100));
		panel1_4.setBackground(UIManager.getColor("TextPane.disabledBackground"));
		panel1_4.setOpaque(false);
		panel.add(panel1_4, BorderLayout.CENTER);
		panel1_4.setLayout(new BoxLayout(panel1_4, BoxLayout.Y_AXIS));
		
		Component verticalGlue_1_1 = Box.createVerticalGlue();
		verticalGlue_1_1.setPreferredSize(new Dimension(0, 80));
		verticalGlue_1_1.setMinimumSize(new Dimension(0, 100));
		panel1_4.add(verticalGlue_1_1);
		
		JLabel lblNewLabel_1 = new JLabel("Login to your account");
		lblNewLabel_1.setForeground(new Color(128, 255, 223));
		lblNewLabel_1.setFont(new Font("Araboto-Bold", Font.PLAIN, 39));
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel1_4.add(lblNewLabel_1);
		
		Component verticalGlue_1 = Box.createVerticalGlue();
		verticalGlue_1.setPreferredSize(new Dimension(0, 40));
		verticalGlue_1.setMinimumSize(new Dimension(0, 100));
		panel1_4.add(verticalGlue_1);
		
		
		txtusername = new TransparentTextField();
		txtusername.setText("Username");
		txtusername.setForeground(new Color(230, 230, 250));
		txtusername.setFont(new Font("Araboto-Normal", Font.PLAIN, 20));
		txtusername.setPreferredSize(new Dimension(7, 50));
		txtusername.setOpaque(false);
		txtusername.setBorder(new LineBorder(new Color(230, 230, 250, 130), 1, true));
		panel1_4.add(txtusername);
		txtusername.setColumns(10);
		
		// LOGIN BUTTON
		TransparentButton btnLogin = new TransparentButton();
		btnLogin.setText("Log in");
		btnLogin.setOpaque(false);
		btnLogin.setFont(new Font("Araboto-Bold", Font.PLAIN, 36));
		btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnLogin.setContentAreaFilled(false);
		//btnNewButton.setBorderPainted(false);
		btnLogin.setForeground(new Color(255, 255, 255));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnLogin) {
					if (new String(txtusername.getText()).equals(adminUsername) && new String(txtpassword.getPassword()).equals(adminPassword)) {
						showMessageDialog(null, "Login Successful");
							
						try {
							new POS_Menu();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						frmStarFurnitureLogin.dispose();
					} else {
						if (logincounter >= 2) { //After 3 attempts
							showMessageDialog(null, "You already had 3 attempts. Exiting program");
							frmStarFurnitureLogin.dispose();
						} else {
							logincounter++;
							showMessageDialog(null, "Login Failed");
						}
							
					}
				}
			}
		});
		
		Component verticalGlue = Box.createVerticalGlue();
		verticalGlue.setPreferredSize(new Dimension(0, 40));
		verticalGlue.setMinimumSize(new Dimension(0, 100));
		panel1_4.add(verticalGlue);
		
		txtpassword = new JPasswordField() {
			protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        Graphics2D g2d = (Graphics2D) g;
		        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		        Rectangle r = g.getClipBounds();
		        
		        Color color1 = new Color(3, 194, 146, 130).brighter().brighter();
		        Color color2 = new Color(3, 194, 146, 0).brighter();
		        
		        GradientPaint gp = new GradientPaint(r.x+20, r.y+200, color1, r.width, r.height-200, color2);
		        g2d.setPaint(gp);

		        g.fillRect(r.x, r.y, r.width, r.height);
		    }
		};
		txtpassword.setForeground(new Color(230, 230, 250));
		txtpassword.setHorizontalAlignment(SwingConstants.LEFT);
		txtpassword.setMargin(new Insets(2, 20, 2, 2));
		txtpassword.setText("Password");
		txtpassword.setFont(new Font("Araboto-Normal", Font.PLAIN, 20));
		txtpassword.setBorder(new LineBorder(new Color(230, 230, 250, 130), 1, true));
		txtpassword.setCaretColor(Color.GREEN);
		txtpassword.setPreferredSize(new Dimension(7, 50));
		txtpassword.setColumns(10);
		txtpassword.setOpaque(false);
		panel1_4.add(txtpassword);
		
		Component verticalGlue_2 = Box.createVerticalGlue();
		verticalGlue_2.setPreferredSize(new Dimension(0, 40));
		verticalGlue_2.setMinimumSize(new Dimension(0, 100));
		panel1_4.add(verticalGlue_2);
		btnLogin.setPreferredSize(new Dimension(500, 120));
		panel1_4.add(btnLogin);

	}
}
