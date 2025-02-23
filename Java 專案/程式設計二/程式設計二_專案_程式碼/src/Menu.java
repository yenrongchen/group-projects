import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Menu extends JFrame{
	
	
	public Menu() {
		
		setTitle("School Meal");
		setVisible(true);
		setSize(515,700);
		JLabel show = new JLabel();
		ImageIcon icon = new ImageIcon("menu.jpeg");
		Image image1 =icon.getImage();
		icon = new ImageIcon(image1);
		show.setIcon(icon);
		
		JButton button1 = new JButton("This week");
		JButton button2 = new JButton("Next week");
		JButton button3 = new JButton("Next next week");
		
		button1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		button2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		button3.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImageIcon icon = new ImageIcon("menu.jpeg");
				Image image1 =icon.getImage();
				icon = new ImageIcon(image1);
				show.setIcon(icon);
			}
		});
		
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImageIcon icon = new ImageIcon("menu2.jpeg");
				Image image1 =icon.getImage();
				icon = new ImageIcon(image1);
				show.setIcon(icon);
			}
		});
		
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImageIcon icon = new ImageIcon("menu1.jpg");
				Image image1 =icon.getImage();
				icon = new ImageIcon(image1);
				show.setIcon(icon);
			}
		});
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		panel1.add(button1);
		panel2.add(button2);
		panel3.add(button3);
		panel1.setBackground(new Color(255, 255, 255));
		panel2.setBackground(new Color(255, 255, 255));
		panel3.setBackground(new Color(255, 255, 255));
		
		JPanel panel4 = new JPanel(new GridLayout(1,3));
		panel4.add(panel1);
		panel4.add(panel2);
		panel4.add(panel3);
		
		add(panel4, BorderLayout.NORTH);
		add(show, BorderLayout.CENTER);
		
	}
	
}