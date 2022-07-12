

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;

public class AddOptions extends Item {
	
	protected JButton btnAddItem;
	protected JTextField productName;
	protected JTextField Price;
	protected JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
	protected JLabel picLabel;
	
	protected String generatedID = getRandomString();
	
	
	protected JFrame frmAddItemOptions;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddOptions window = new AddOptions();
					window.frmAddItemOptions.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public AddOptions() {
		initialize();
	}


	private void initialize() {
		frmAddItemOptions = new JFrame();
		frmAddItemOptions.setResizable(false);
		frmAddItemOptions.setTitle("Add Item Options");
		frmAddItemOptions.setBounds(100, 100, 311, 348);
		frmAddItemOptions.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmAddItemOptions.getContentPane().setLayout(null);
		
			
		productName = new JTextField();
		productName.setBounds(106, 11, 180, 20);
		frmAddItemOptions.getContentPane().add(productName);
		productName.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Name                :");
		lblNewLabel.setBounds(10, 14, 86, 14);
		frmAddItemOptions.getContentPane().add(lblNewLabel);
		
		Price = new JTextField();
		Price.setColumns(10);
		Price.setBounds(106, 42, 180, 20);
		Price.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
			        e.consume();
			    } 
				if (Price.getText().length() > 8) {
					Price.setText("999999999");
			    	e.consume();
			    }
			}
		});
		frmAddItemOptions.getContentPane().add(Price);
		
		JLabel lblPrice = new JLabel("Price                 :");
		lblPrice.setBounds(10, 45, 86, 14);
		frmAddItemOptions.getContentPane().add(lblPrice);
		
		JLabel lblQuantity = new JLabel("Quantity           :");
		lblQuantity.setBounds(10, 77, 86, 14);
		frmAddItemOptions.getContentPane().add(lblQuantity);
		
		spinner.setBounds(106, 73, 45, 20);
		frmAddItemOptions.getContentPane().add(spinner);
		
		picLabel = new JLabel("");
		frmAddItemOptions.getContentPane().add(picLabel);
		picLabel.setBounds(10, 102, 276, 157);
		
	
		
		
		JButton btnUploadIcon = new JButton("Upload Icon");
		btnUploadIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
	            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
	            int result = fileChooser.showOpenDialog(frmAddItemOptions.getParent());
	            if (result == JFileChooser.APPROVE_OPTION) {
	                try {
	                	File file = fileChooser.getSelectedFile();
	                    BufferedImage picture = ImageIO.read(file);
	                    picLabel.setIcon(new ImageIcon(new ImageIcon(picture).getImage().getScaledInstance(287, 150, Image.SCALE_SMOOTH)));
	                } catch (IOException g) {
	                    JOptionPane.showMessageDialog(null,"ERROR");
	                }
	            }
			}
		});
		btnUploadIcon.setBounds(173, 73, 113, 23);
		frmAddItemOptions.getContentPane().add(btnUploadIcon);
		
		btnAddItem = new JButton("Add Item");
		btnAddItem.setBounds(106, 273, 93, 23);
		frmAddItemOptions.getContentPane().add(btnAddItem);
		
		
	}
	
	protected String getRandomString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
	
	protected void reset() {
		productName.setText("");
		Price.setText("");
		spinner.setValue((Integer) 1);
		picLabel.setIcon(new ImageIcon(new ImageIcon(
				"C:\\Users\\User\\eclipse-workspace\\Point of Sale\\img\\noentry.png").getImage().getScaledInstance(287, 150, Image.SCALE_SMOOTH)));
		generatedID = getRandomString();
	}
}
