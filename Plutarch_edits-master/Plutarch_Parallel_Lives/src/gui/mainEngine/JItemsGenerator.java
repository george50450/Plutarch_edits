package gui.mainEngine;

import java.awt.Color;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;


public class JItemsGenerator {
	

	public JPanel createJPanel() {
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 280, 600);
		panel.setBackground(Color.DARK_GRAY);
		
		GroupLayout gl_sideMenu = createGroupLayout(panel);
		panel.setLayout(gl_sideMenu);
		
		return panel;
		
	}
	
	public JDialog createJDialog(int x, int y, int width, int height) {
		
		JDialog dialog = new JDialog();
		dialog.setBounds(x, y, width, height);
		
		return dialog;
	}
	
	public JScrollPane createJScrollPane(int x, int y, int width, int height) {
		
		JScrollPane scrollPane = new JScrollPane();

		scrollPane.setBounds(x, y, width, height);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		return scrollPane;
	}
	
	public void setJScrollPanePosition(JScrollPane pane, int x, int y, int width, int height) {
		
		pane.setAlignmentX(0);
		pane.setAlignmentY(0);
		pane.setBounds(x, y, width, height);
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
	}
	
	public JButton createJButton(String btn_name, int x, int y, int width, int height) {
		
		JButton button = new JButton(btn_name);
		button.setBounds(x, y, width, height);
		
		button.setVisible(false);
		
		return button;
	}
	
	public JLabel createJLabel(String lbl_name, String lbl_text, int x, int y, int width, int height, Color fg) {
		
		JLabel label = new JLabel(lbl_name);
		label.setBounds(x, y, width, height);
		label.setForeground(fg);
		label.setText(lbl_text);
		
		return label;
	}

	
	
	
	public JTextArea createTextArea(String text, int x, int y, int width, int height, Color foregrColor, Color bgColor) {
		
		JTextArea descriptionText = new JTextArea();
		
		descriptionText.setBounds(x, y, width, height);
		descriptionText.setForeground(foregrColor);
		descriptionText.setText(text);
		descriptionText.setBackground(bgColor);
		
		return descriptionText;
		
	}
	
	
	private GroupLayout createGroupLayout(JPanel panel) {
		
		GroupLayout layout = new GroupLayout(panel);
		layout.setHorizontalGroup(
				layout.createParallelGroup(Alignment.LEADING)
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(Alignment.LEADING)
		);
		
		return layout;
		
	}

}
