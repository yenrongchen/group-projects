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
		titleLabel.setFont(new Font("微軟正黑體", Font.BOLD, 25));
		price = new JLabel("		價位：" + r.getPrice());
		price.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
		schoolGate = new JLabel("		距離政大最近校門：" + r.getSchoolGate());
		schoolGate.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
		cousine = new JLabel("		餐點類型：" + r.getCuisine());
		cousine.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
		vegan = new JLabel("		是否提供素食：" + r.getVegan());
		vegan.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
		takeOut = new JLabel("		是否提供外帶：" + r.getTakeOut());
		takeOut.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
		seats = new JLabel("		內用座位數：" + r.getSeats());
		seats.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
		openTime = new JLabel("		營業時間：" + r.getOpenTime());
		openTime.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
		telefon = new JLabel("		電話：" + r.getTelefon());
		telefon.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
		address = new JLabel("		地址：" + r.getAddress());
		address.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
		
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