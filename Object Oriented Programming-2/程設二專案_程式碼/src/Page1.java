import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class Page1 extends JFrame{  
	private JPanel panel;
	private JTextArea list, information;
	private JScrollPane scrollPane; 

	ArrayList <Restaurant> restaurants = new ArrayList <Restaurant>();
	ArrayList <JButton> btns = new ArrayList <JButton>();  	
	
	public Page1(ArrayList restaurants) {
		this.restaurants = restaurants;
		menu();
	}
	public void createButton() {
		ButtonGroup group = new ButtonGroup();
		for (int i = 0; i < restaurants.size(); i++) {
			JButton btn = new JButton();
			btn = new JButton(restaurants.get(i).getName());  
			btns.add(btn);
			Restaurant r = restaurants.get(i);
			btn.addActionListener (new ActionListener() {
				public void actionPerformed(ActionEvent event) {  
					InformationFrame frame = new InformationFrame(r);  
				}
			});
			group.add(btn);
		}
		JButton addButton = new JButton();
		addButton.setText("Add Restaurant");
		group.add(addButton);
	}
	
	
	public void createLayout() {
		JPanel p = new JPanel(new GridLayout(4,4));
		for(int i = 0; i < btns.size(); i++) {
			btns.get(i).setFont(new Font("·L³n¥¿¶ÂÅé", Font.BOLD, 13));
			p.add(btns.get(i));
		}
		add(p);  
		
	}
	
	
	public void addRestaurant(Restaurant r) {
		restaurants.add(r);
	}
	public void menu(){
		createButton();
		createLayout();  
		setSize(650, 650);
		setTitle("Restaurants");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);	
	}
}