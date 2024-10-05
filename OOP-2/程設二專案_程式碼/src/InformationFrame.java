import javax.swing.*; 
import javax.swing.JFrame;
import java.awt.Font;
import javax.swing.JOptionPane;   //not a must in my part
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
  
public class InformationFrame extends JFrame{
	JFrame frame = new JFrame();
	private JLabel titleLabel, price, schoolGate, cousine, vegan, takeOut, seats, openTime, telefon, address;
	private JPanel titlePanel, infoPanel; 

	
	public InformationFrame(Restaurant r) {
		titlePanel = new JPanel();
		infoPanel = new JPanel();
		frame.setTitle("Information");
		GridLayout g = new GridLayout(10,1); 
		infoPanel.setLayout(g);
		titleLabel = new JLabel(r.getName());
		titleLabel.setFont(new Font("�L�n������", Font.BOLD, 25));
		price = new JLabel("		����G" + r.getPrice());
		price.setFont(new Font("�L�n������", Font.PLAIN, 15));
		schoolGate = new JLabel("		�Z���F�j�̪�ժ��G" + r.getSchoolGate());
		schoolGate.setFont(new Font("�L�n������", Font.PLAIN, 15));
		cousine = new JLabel("		�\�I�����G" + r.getCuisine());
		cousine.setFont(new Font("�L�n������", Font.PLAIN, 15));
		vegan = new JLabel("		�O�_���ѯ����G" + r.getVegan());
		vegan.setFont(new Font("�L�n������", Font.PLAIN, 15));
		takeOut = new JLabel("		�O�_���ѥ~�a�G" + r.getTakeOut());
		takeOut.setFont(new Font("�L�n������", Font.PLAIN, 15));
		seats = new JLabel("		���ήy��ơG" + r.getSeats());
		seats.setFont(new Font("�L�n������", Font.PLAIN, 15));
		openTime = new JLabel("		��~�ɶ��G" + r.getOpenTime());
		openTime.setFont(new Font("�L�n������", Font.PLAIN, 15));
		telefon = new JLabel("		�q�ܡG" + r.getTelefon());
		telefon.setFont(new Font("�L�n������", Font.PLAIN, 15));
		address = new JLabel("		�a�}�G" + r.getAddress());
		address.setFont(new Font("�L�n������", Font.PLAIN, 15));
		
		titlePanel.add(titleLabel);
		infoPanel.add(price);
		infoPanel.add(schoolGate);
		infoPanel.add(cousine);
		infoPanel.add(vegan);
		infoPanel.add(takeOut);
		infoPanel.add(seats);
		infoPanel.add(openTime);
		infoPanel.add(telefon);
		infoPanel.add(address);
		
		titlePanel.setBackground(new Color(255, 255, 255));
		infoPanel.setBackground(new Color(255, 255, 255));
		frame.add(titlePanel, BorderLayout.NORTH);
		frame.add(infoPanel, BorderLayout.CENTER);
		
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setVisible(true);
	}
}