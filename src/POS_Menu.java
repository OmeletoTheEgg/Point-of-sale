

import javax.swing.JFrame;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import java.awt.event.ActionEvent;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.awt.FlowLayout;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javax.swing.JLayeredPane;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.JSeparator;
import java.math.RoundingMode;
import java.text.DecimalFormat;


public class POS_Menu extends AddOptions {
	// INSTANCE VARIABLES
	private static final DecimalFormat df = new DecimalFormat("0.00"); // FOR PUTTING TWO DECIMAL PLACES ON DOUBLES
	private JFrame frmPOS;
	private JTable table;
	private JLayeredPane leftPanel;
	private JPanel order;
	private JLabel GrandTotal;
	private JLabel GrandTotalLeft;
	private JLabel Qty;
	private JLabel Total;
	private JLabel VAT;
	private JLabel Discount;
	private JLabel Pay;
	private JLabel Change;
	private JButton Payment;
	private boolean toggleDiscount = false;
	private boolean toggleOrderPanel = false;
	private boolean payFirstClick = false;
	private TransparentButton trnsprntbtnRemoveItem;
	private TransparentButton trnsprntbtnAddItem;
	private JPanel itemPanel;
	private boolean deleteMode = false;
	protected boolean admin = true;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new POS_Menu();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//MENU CONSTRUCTOR
	protected POS_Menu() {
		initialize();
	}

	private void initialize() {
		df.setRoundingMode(RoundingMode.UP);
		
		frmPOS = new JFrame();
		frmPOS.setVisible(true);
		frmPOS.setResizable(false);
		frmPOS.setTitle("Point of Sale System");
		frmPOS.setBounds(100, 100, 1450, 720);
		frmPOS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPOS.getContentPane().setLayout(null);
		
		// FANCY BACKGROUND PANEL
		BackgroundPanel panel = new BackgroundPanel();
		panel.setBounds(0, 0, 1450, 720);
		frmPOS.getContentPane().add(panel);
		panel.setLayout(null);
		
		panel.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				panel.mouseX = e.getX();
				panel.mouseY = e.getY();
			}
		});
		
		// UPPER PANEL
		JPanel upperPanel = new JPanel() {
			protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        Graphics2D g2d = (Graphics2D) g;
		        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		        Rectangle r = g.getClipBounds();
		        
		        Color color1 = new Color(212, 87, 150, 70).brighter().brighter();
		        Color color2 = new Color(212, 87, 150, 70).brighter().brighter();
		        
		        GradientPaint gp = new GradientPaint(r.x+20, r.y+200, color1, r.width, r.height-200, color2);
		        g2d.setPaint(gp);
		        
		        g.fillRect(r.x, r.y, r.width, r.height);
		    }
		};
		upperPanel.setBounds(0, 0, 1450, 80);
		upperPanel.setOpaque(false);
		upperPanel.setPreferredSize(new Dimension(10, 80));
		panel.add(upperPanel);
		upperPanel.setLayout(null);
		
		trnsprntbtnRemoveItem = new TransparentButton();
		trnsprntbtnRemoveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (deleteMode) deleteMode = false; else deleteMode = true;
			}
		});
		trnsprntbtnRemoveItem.setText("Remove Item");
		trnsprntbtnRemoveItem.setPreferredSize(new Dimension(70, 40));
		trnsprntbtnRemoveItem.setForeground(Color.WHITE);
		trnsprntbtnRemoveItem.setFont(new Font("Araboto-Normal", Font.PLAIN, 20));
		trnsprntbtnRemoveItem.setContentAreaFilled(false);
		//trnsprntbtnRemoveItem.setBorderPainted(false);
		trnsprntbtnRemoveItem.setBounds(199, 11, 179, 58);
		upperPanel.add(trnsprntbtnRemoveItem);
		
		trnsprntbtnAddItem = new TransparentButton();
		trnsprntbtnAddItem.setText("Add Item");
		trnsprntbtnAddItem.setPreferredSize(new Dimension(70, 40));
		trnsprntbtnAddItem.setForeground(Color.WHITE);
		trnsprntbtnAddItem.setFont(new Font("Araboto-Normal", Font.PLAIN, 20));
		trnsprntbtnAddItem.setContentAreaFilled(false);
		//trnsprntbtnAddItem.setBorderPainted(false);
		trnsprntbtnAddItem.setBounds(10, 11, 169, 58);
		upperPanel.add(trnsprntbtnAddItem);
		AddOptions addOptions = new AddOptions();
		trnsprntbtnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addOptions.frmAddItemOptions.dispose();
				addOptions.reset();
				addOptions.picLabel.setIcon(new ImageIcon(new ImageIcon(
		    			"C:\\Users\\User\\eclipse-workspace\\Point of Sale\\img\\noentry.png").getImage().getScaledInstance(287, 150, Image.SCALE_SMOOTH)));
				addOptions.frmAddItemOptions.setVisible(true);
				addOptions.frmAddItemOptions.setLocationRelativeTo(frmPOS);
			}
		});
		
		
		addOptions.btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (addOptions.productName.getText().isBlank()) {
					showMessageDialog(null, "Product name is empty");
				} else if (addOptions.Price.getText().isBlank()) {
					showMessageDialog(null, "Price is empty");
				} else {
					Item item = new Item(addOptions.productName.getText(), addOptions.Price.getText(),
							(Integer) addOptions.spinner.getValue(), addOptions.picLabel.getIcon(), addOptions.generatedID);
					POSButton itemButton = new POSButton();
					itemPanel.add(itemButton);
					itemButton.setLayout(new BorderLayout());
					itemButton.setMargin(null);
					itemButton.setPreferredSize(new Dimension(287, 150));
					itemButton.setIcon(addOptions.picLabel.getIcon());
					JLabel itemPrice = new JLabel("₱" + df.format(Double.parseDouble(addOptions.Price.getText())));
					itemPrice.setVerticalAlignment(SwingConstants.BOTTOM);
					itemPrice.setForeground(Color.WHITE);
					itemPrice.setFont(new Font("Araboto-Medium", Font.PLAIN, 15));
					itemPrice.setHorizontalAlignment(SwingConstants.CENTER);
					itemButton.add(itemPrice, BorderLayout.SOUTH);
					
					itemButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (deleteMode) {
								itemButton.setVisible(false);
								deleteMode = false;
							} else {
								DefaultTableModel model = (DefaultTableModel) table.getModel();
								model.addRow(new Object[] {item.itemID, item.quantity, item.name, "₱" + item.price, "₱" + df.format(Double.parseDouble(item.price) * item.quantity)});
								totalUpdate();
							}
						}
					});
					addOptions.frmAddItemOptions.dispose();
				}
			}
		});
		
		
		
		
		if (!admin) {
			trnsprntbtnAddItem.setVisible(false);
			trnsprntbtnRemoveItem.setVisible(false);
		} else {
			trnsprntbtnAddItem.setVisible(true);
			trnsprntbtnRemoveItem.setVisible(true);
		}
		
		// PANEL FOR MENU/ORDER
		leftPanel = new JLayeredPane();
		leftPanel.setBounds(0, 80, 863, 611);
		leftPanel.setOpaque(false);
		panel.add(leftPanel);
		leftPanel.setLayout(null);
		
		order = new JPanel();
		order.setPreferredSize(new Dimension(500, 10));
		order.setOpaque(false);
		order.setBounds(0, 0, 863, 611);
		order.setLayout(new BorderLayout(0, 0));
		
		itemPanel = new JPanel();
		itemPanel.setPreferredSize(new Dimension(1000, 1000));
		itemPanel.setOpaque(false);
		itemPanel.setBounds(0, 0, 863, 1000);
		leftPanel.add(itemPanel);
		FlowLayout fl_itemPanel = new FlowLayout();
		fl_itemPanel.setVgap(0);
		fl_itemPanel.setHgap(0);
		fl_itemPanel.setAlignment(FlowLayout.LEFT);
		fl_itemPanel.setAlignOnBaseline(true);
		itemPanel.setLayout(fl_itemPanel);
		
		// ORDER COMPONENTS -------------------------------------------------------------------------------------------------------------
		TransparentPane panel_1 = new TransparentPane();
		panel_1.setPreferredSize(new Dimension(10, 150));
		panel_1.setOpaque(false);
		order.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 75, 863, 2);
		panel_1.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 148, 863, 2);
		panel_1.add(separator_1);
		
		JLabel TotalLabel_1 = new JLabel("Grand Total:");
		TotalLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		TotalLabel_1.setForeground(Color.WHITE);
		TotalLabel_1.setFont(new Font("Araboto-Medium", Font.PLAIN, 31));
		TotalLabel_1.setBounds(0, 11, 215, 66);
		panel_1.add(TotalLabel_1);
		
		GrandTotalLeft = new JLabel("₱0.00");
		GrandTotalLeft.setHorizontalAlignment(SwingConstants.TRAILING);
		GrandTotalLeft.setForeground(Color.WHITE);
		GrandTotalLeft.setFont(new Font("Araboto-Medium", Font.PLAIN, 31));
		GrandTotalLeft.setBounds(198, 11, 215, 66);
		panel_1.add(GrandTotalLeft);
		
		Pay = new JLabel("₱0.00");
		Pay.setHorizontalAlignment(SwingConstants.TRAILING);
		Pay.setForeground(Color.WHITE);
		Pay.setFont(new Font("Araboto-Medium", Font.PLAIN, 31));
		Pay.setBounds(638, 11, 215, 66);
		panel_1.add(Pay);
		
		JLabel TotalLabel_1_2 = new JLabel("Pay:");
		TotalLabel_1_2.setHorizontalAlignment(SwingConstants.TRAILING);
		TotalLabel_1_2.setForeground(Color.WHITE);
		TotalLabel_1_2.setFont(new Font("Araboto-Medium", Font.PLAIN, 31));
		TotalLabel_1_2.setBounds(423, 11, 215, 66);
		panel_1.add(TotalLabel_1_2);
		
		Change = new JLabel("₱0.00");
		Change.setHorizontalAlignment(SwingConstants.TRAILING);
		Change.setForeground(Color.WHITE);
		Change.setFont(new Font("Araboto-Medium", Font.PLAIN, 31));
		Change.setBounds(638, 84, 215, 66);
		panel_1.add(Change);
		
		JLabel TotalLabel_1_2_1 = new JLabel("Change:");
		TotalLabel_1_2_1.setHorizontalAlignment(SwingConstants.TRAILING);
		TotalLabel_1_2_1.setForeground(Color.WHITE);
		TotalLabel_1_2_1.setFont(new Font("Araboto-Medium", Font.PLAIN, 31));
		TotalLabel_1_2_1.setBounds(423, 84, 215, 66);
		panel_1.add(TotalLabel_1_2_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(100, 1000));
		panel_2.setOpaque(false);
		order.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		
		// CREATED BUTTONS USING LOOPING FOR ORDER
		String btnStringList[] = {"7", "8", "9", "Clear", 
								"4", "5", "6", "Erase",
								"1", "2", "3", "Senior/PWD",
								"0", "00", ".", "Go Back"};
		Vector<JButton> btnList = new Vector<JButton>();
		for (int i = 0; i < 16; i++) {
			TransparentButton btn = new TransparentButton();
			btn.setText(btnStringList[i]);
			btn.setPreferredSize(new Dimension(215, 115));
			btn.setOpaque(false);
			btn.setContentAreaFilled(false);
			//btn.setBorderPainted(false);
			btn.setForeground(new Color(255, 255, 255));
			btn.setFont(new Font("Araboto-Normal", Font.PLAIN, 32));
			panel_2.add(btn);
			btnList.add(btn);
		}
		
		// GIVE FUNCTIONALITY FOR EACH NUMBER BUTTONS
		for (int i = 0; i < 14; i++) {
			if (i != 3 && i != 7 && i != 11) {
				final Integer innerI = new Integer(i);
				btnList.get(i).addActionListener((new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (payFirstClick == true) Pay.setText("₱");
						if (Pay.getText() == "₱0.00") Pay.setText("₱");
						Pay.setText(removeLeadingZeros(Pay.getText() + btnStringList[innerI]));
						changeUpdate();
						payFirstClick = false;
					}
				}));
			}
		}
		
		// CLEAR BUTTON FUNCTIONALITY
		btnList.get(3).addActionListener((new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pay.setText("₱0.00");
				changeUpdate();
			}
		}));
		
		// ERASE BUTTON FUNCTIONALITY
		btnList.get(7).addActionListener((new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!Pay.getText().equals("₱0.00")) {
					Pay.setText(Pay.getText().substring(0,Pay.getText().length()-1));
					if (Pay.getText().equals("₱")) Pay.setText("₱0.00");
					changeUpdate();
				}
			}
		}));
		
		// TOGGLE SENIOR CITIZEN/PWD CARD FOR DISCOUNT
		btnList.get(11).addActionListener((new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (toggleDiscount) {
					toggleDiscount = false;
				} else toggleDiscount = true;
				totalUpdate();
				changeUpdate();
			}
		}));
		
		// . FUNCTION
		btnList.get(14).addActionListener((new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!Pay.getText().contains(".")) Pay.setText(Pay.getText() + btnStringList[14]);
			}
		}));
		
		// SWITCH TO MENU
		btnList.get(15).addActionListener((new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchLeftPanel(itemPanel);
				if (admin) {
					trnsprntbtnAddItem.setVisible(true);
					trnsprntbtnRemoveItem.setVisible(true);
				}
				Payment.setText("Place Order");
				toggleOrderPanel = false;
			}
		}));	
		
		// MENU SCROLL PANE FOR ITEM BUTTONS
		JScrollPane scrollPane_1 = new JScrollPane() {
			protected void paintComponent(Graphics g) {
		    	super.paintComponent(g);
		        final Graphics2D g2 = (Graphics2D) g.create();
		        Color color1 = new Color(3, 194, 146, 130).brighter();
		        g2.setPaint(color1);
		        g2.fillRect(0, 0, getWidth(), getHeight());
		        g2.dispose();
		    }
		};
		scrollPane_1.setBackground(new Color(255, 0, 0));
		scrollPane_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane_1.setOpaque(false);
		scrollPane_1.getViewport().setOpaque(false);
		scrollPane_1.setBounds(873, 91, 553, 351);
		panel.add(scrollPane_1);
		
		// TABLE LIST FOR RIGHT PANEL-----------------------------------------------------------------------------------------------
		table = new JTable();
		table.setOpaque(false);
		table.setRowHeight(40);
		table.setFont(new Font("Araboto-Normal", Font.PLAIN, 11));
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ItemID" ,"Qty.", "Item", "Price", "Amount"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class ,Integer.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(46);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(34);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(184);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(3).setPreferredWidth(42);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.getColumnModel().getColumn(4).setPreferredWidth(42);
		
		// DETECTS DOUBLE CLICK IN THE ROW OF TABLE TO DELETE
		table.addMouseListener(new MouseAdapter() {
	         public void mouseClicked(MouseEvent me) {
	            if (me.getClickCount() == 2 && !toggleOrderPanel) {     
	               JTable target = (JTable)me.getSource();
	               int row = target.getSelectedRow(); 
	               DefaultTableModel model = (DefaultTableModel) table.getModel();
	               model.removeRow(row);
	               totalUpdate();
	            }
	         }
	      });
		table.getTableHeader().setReorderingAllowed(false);
		scrollPane_1.setViewportView(table);
		
		//PAYMENT DETAILS---------------------------------------------------------------------------------------------
		JPanel TotalPane = new JPanel() {
			protected void paintComponent(Graphics g) {
		    	super.paintComponent(g);
		        final Graphics2D g2 = (Graphics2D) g.create();
		        Color color1 = new Color(3, 194, 146, 130).brighter();
		        g2.setPaint(color1);
		        g2.fillRect(0, 0, getWidth(), getHeight());
		        g2.dispose();
		    }
		};
		TotalPane.setBounds(873, 441, 553, 240);
		TotalPane.setOpaque(false);
		panel.add(TotalPane);
		TotalPane.setLayout(null);
		
		Payment = new JButton() {
			protected void paintComponent(Graphics g) {
		    	super.paintComponent(g);
		        final Graphics2D g2 = (Graphics2D) g.create();
		        Color color1 = new Color(212, 87, 150, 130).brighter().brighter();
		        g2.setPaint(color1);
		        g2.fillRect(0, 0, getWidth(), getHeight());
		        g2.dispose();
		    }
		};
		Payment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getModel().getRowCount() <= 0) {
					showMessageDialog(null, "Please input an item first.");
				} else {
					if (toggleOrderPanel == true) {
						if (Double.parseDouble(Pay.getText().substring(1)) - Double.parseDouble(GrandTotal.getText().substring(1)) < 0) {
							showMessageDialog(null, "Insufficient Funds");
						} else {
							
							showMessageDialog(null, "Change: " + Change.getText() + "\nThank you for purchasing! Please come again");
							if (admin) {
								trnsprntbtnAddItem.setVisible(true);
								trnsprntbtnRemoveItem.setVisible(true);
							}
							payFirstClick = false;
							Payment.setText("Place Order");
							switchLeftPanel(itemPanel);
							Pay.setText("₱0.00");
							DefaultTableModel dm = (DefaultTableModel) table.getModel();
							for (int i = dm.getRowCount() - 1; i >= 0; i--) dm.removeRow(i);
							totalUpdate();
							changeUpdate();
							toggleOrderPanel = false;
						}
					} else {
						switchLeftPanel(order);
						Payment.setText("Pay");
						if (admin) {
							trnsprntbtnAddItem.setVisible(false);
							trnsprntbtnRemoveItem.setVisible(false);
						}
						
						toggleOrderPanel = true;
						Pay.setText(GrandTotal.getText());
						payFirstClick = true;
						changeUpdate();
					}
				}
			}
		});
		Payment.setVerticalAlignment(SwingConstants.BOTTOM);
		Payment.setBounds(0, 161, 567, 78);
		Payment.setText("Place Order");
		Payment.setPreferredSize(new Dimension(300, 60));
		Payment.setContentAreaFilled(false);
		//Payment.setBorderPainted(false);
		Payment.setForeground(new Color(255, 255, 255));
		Payment.setFont(new Font("Araboto-Bold", Font.PLAIN, 37));
		TotalPane.add(Payment);
		
		JLabel TotalLabel = new JLabel("Grand Total:");
		TotalLabel.setForeground(Color.WHITE);
		TotalLabel.setVerticalAlignment(SwingConstants.TOP);
		TotalLabel.setFont(new Font("Araboto-Bold", Font.PLAIN, 38));
		TotalLabel.setBounds(0, 118, 338, 41);
		TotalPane.add(TotalLabel);
		
		GrandTotal = new JLabel("₱0.00");
		GrandTotal.setHorizontalAlignment(SwingConstants.TRAILING);
		GrandTotal.setVerticalAlignment(SwingConstants.TOP);
		GrandTotal.setForeground(Color.WHITE);
		GrandTotal.setFont(new Font("Araboto-Bold", Font.PLAIN, 38));
		GrandTotal.setBounds(204, 118, 349, 41);
		TotalPane.add(GrandTotal);
		
		JLabel lblQuantity = new JLabel("Quantity:");
		lblQuantity.setVerticalAlignment(SwingConstants.TOP);
		lblQuantity.setForeground(Color.WHITE);
		lblQuantity.setFont(new Font("Araboto-Bold", Font.PLAIN, 22));
		lblQuantity.setBounds(0, 14, 130, 41);
		TotalPane.add(lblQuantity);
		
		Qty = new JLabel("0");
		Qty.setVerticalAlignment(SwingConstants.TOP);
		Qty.setHorizontalAlignment(SwingConstants.TRAILING);
		Qty.setForeground(Color.WHITE);
		Qty.setFont(new Font("Araboto-Bold", Font.PLAIN, 22));
		Qty.setBounds(423, 14, 130, 41);
		TotalPane.add(Qty);
		
		JLabel lblDiscount = new JLabel("Discount:");
		lblDiscount.setVerticalAlignment(SwingConstants.TOP);
		lblDiscount.setForeground(Color.WHITE);
		lblDiscount.setFont(new Font("Araboto-Bold", Font.PLAIN, 22));
		lblDiscount.setBounds(0, 91, 130, 41);
		TotalPane.add(lblDiscount);
		
		Discount = new JLabel("₱0.00");
		Discount.setVerticalAlignment(SwingConstants.TOP);
		Discount.setHorizontalAlignment(SwingConstants.TRAILING);
		Discount.setForeground(Color.WHITE);
		Discount.setFont(new Font("Araboto-Bold", Font.PLAIN, 22));
		Discount.setBounds(423, 91, 130, 41);
		TotalPane.add(Discount);
		
		JLabel lblVat = new JLabel("VAT:");
		lblVat.setVerticalAlignment(SwingConstants.TOP);
		lblVat.setForeground(Color.WHITE);
		lblVat.setFont(new Font("Araboto-Bold", Font.PLAIN, 22));
		lblVat.setBounds(0, 66, 130, 41);
		TotalPane.add(lblVat);
		
		VAT = new JLabel("₱0.00");
		VAT.setVerticalAlignment(SwingConstants.TOP);
		VAT.setHorizontalAlignment(SwingConstants.TRAILING);
		VAT.setForeground(Color.WHITE);
		VAT.setFont(new Font("Araboto-Bold", Font.PLAIN, 22));
		VAT.setBounds(423, 66, 130, 41);
		TotalPane.add(VAT);
		
		JLabel lblTotalAmount = new JLabel("Total Amount:");
		lblTotalAmount.setVerticalAlignment(SwingConstants.TOP);
		lblTotalAmount.setForeground(Color.WHITE);
		lblTotalAmount.setFont(new Font("Araboto-Bold", Font.PLAIN, 22));
		lblTotalAmount.setBounds(0, 39, 174, 41);
		TotalPane.add(lblTotalAmount);
		
		Total = new JLabel("₱0.00");
		Total.setVerticalAlignment(SwingConstants.TOP);
		Total.setHorizontalAlignment(SwingConstants.TRAILING);
		Total.setForeground(Color.WHITE);
		Total.setFont(new Font("Araboto-Bold", Font.PLAIN, 22));
		Total.setBounds(423, 39, 130, 41);
		TotalPane.add(Total);
		
		TransparentButton LogOutButton = new TransparentButton();
		LogOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LoginUI();
				frmPOS.dispose();
			}
		});
		
		// LOGOUT BUTTON
		LogOutButton.setBounds(frmPOS.getWidth()-181, 5, 150, 69);
		LogOutButton.setText("Log Out");
		LogOutButton.setPreferredSize(new Dimension(70, 40));
		LogOutButton.setContentAreaFilled(false);
		//LogOutButton.setBorderPainted(false);
		LogOutButton.setForeground(new Color(255, 255, 255));
		LogOutButton.setFont(new Font("Araboto-Normal", Font.PLAIN, 20));
		panel.add(LogOutButton);
		
		
		// SAMPLE ITEMS ARE LISTED HERE
		JLabel picLabel1 = new JLabel("");
		frmAddItemOptions.getContentPane().add(picLabel1);
		picLabel1.setBounds(10, 102, 276, 157);
		picLabel1.setIcon(new ImageIcon(new ImageIcon(
				"C:\\Users\\User\\eclipse-workspace\\Point of Sale\\img\\apple.jpeg").getImage().getScaledInstance(287, 150, Image.SCALE_SMOOTH)));
		
		JLabel picLabel2 = new JLabel("");
		frmAddItemOptions.getContentPane().add(picLabel2);
		picLabel2.setBounds(10, 102, 276, 157);
		picLabel2.setIcon(new ImageIcon(new ImageIcon(
				"C:\\Users\\User\\eclipse-workspace\\Point of Sale\\img\\mango.jpg").getImage().getScaledInstance(287, 150, Image.SCALE_SMOOTH)));
		
		JLabel[] sampleImg = new JLabel[] {
				picLabel1,
				picLabel2

		};
			
		String[] sampleNames = new String[] {
				"Apple", 
				"Mango"
		};
			
		String[] samplePrice = new String[] {
				"30",
				"40"
		};
			
		String[] sampleID = new String[] {
				"JLK1903A",
				"DJFL02SD"
		};
			
		for(int i = 0; i < sampleImg.length; i++) {
		
			Item item = new Item(sampleNames[i], samplePrice[i],
					(Integer) 1, sampleImg[i].getIcon(), sampleID[i]);
			POSButton itemButton = new POSButton();
			
			itemButton.setLayout(new BorderLayout());
			itemButton.setMargin(null);
			itemButton.setPreferredSize(new Dimension(287, 150));
			itemButton.setIcon(sampleImg[i].getIcon());
			JLabel itemPrice = new JLabel("₱" + df.format(Double.parseDouble(samplePrice[i])));
			itemPrice.setVerticalAlignment(SwingConstants.BOTTOM);
			itemPrice.setForeground(Color.WHITE);
			itemPrice.setFont(new Font("Araboto-Medium", Font.PLAIN, 15));
			itemPrice.setHorizontalAlignment(SwingConstants.CENTER);
			itemButton.add(itemPrice, BorderLayout.SOUTH);
			
			itemButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (deleteMode) {
						itemButton.setVisible(false);
						deleteMode = false;
					} else {
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						model.addRow(new Object[] {item.itemID, item.quantity, item.name, "₱" + item.price, "₱" + df.format(Double.parseDouble(item.price) * item.quantity)});
						totalUpdate();
					}
				}
			});
			itemPanel.add(itemButton);
		}
		
	}
	
	// SWITCH PANEL METHOD FOR MENU AND ORDER
	public void switchLeftPanel(JPanel panel) {
		leftPanel.removeAll();
		leftPanel.add(panel);
		leftPanel.repaint();
		leftPanel.revalidate();
	}
	
	// UPDATE PAYMENT DETAILS
	public void totalUpdate() {
		double sum = 0;
		int qty = 0;
		double vat = 0;
		double discount = 0;
		double grandsum = 0;
		for (int i = 0; i < table.getRowCount(); i++) {
			sum += Double.parseDouble((table.getValueAt(i, 4).toString()).substring(1));
			qty += Integer.parseInt((table.getValueAt(i, 1).toString()));
		}
		vat = sum * 0.12;
		if (toggleDiscount) {
			discount = sum * 0.20;
		}
		grandsum = (sum + vat) - discount;
		Qty.setText(Integer.toString(qty));
		Total.setText("₱" + df.format(sum));
		VAT.setText("₱" + df.format(vat));
		Discount.setText("₱" + df.format(discount));
		GrandTotal.setText("₱" + df.format(grandsum));
		GrandTotalLeft.setText(GrandTotal.getText());
	}
	
	// REGULAR EXPRESSION FOR REMOVING LEADING ZEROS
	public static String removeLeadingZeros(String str)
    {
		str = str.substring(1);
        return "₱" + str.replaceAll("^0+(?!$)", "");
    }
	
	// UPDATE CHANGE
	public void changeUpdate() {
		double diff = Double.parseDouble(Pay.getText().substring(1)) - Double.parseDouble(GrandTotal.getText().substring(1));
		String negate = "";
		if (diff < 0) {
			negate = "-"; 
			Change.setForeground(Color.orange);
		} else {
			negate = "";
			Change.setForeground(Color.green);
		}
		Change.setText(negate + "₱" + df.format(Math.abs(diff)));
	}
}



