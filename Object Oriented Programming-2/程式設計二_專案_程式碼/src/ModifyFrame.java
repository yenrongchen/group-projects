import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;

public class ModifyFrame extends JFrame{
	
	private String[] rest= {"","羹大王","浪速食鋪","Come See 披薩","Juicy Bun","大汗","華越","素還真","波波恰恰","菁英便當","美香味","私房麵","東京小城",
			"提洛斯","左撇子","原丼力","MY 麵屋","飽飽食府","樂山食堂","高句麗","滇味廚房","小木屋","摩斯漢堡","麥當勞","吉野家","八方雲集","Subway"};
	private String[] freq= {"First time","Once or twice a week","Three times a week","Above three times a week"};
	String server = "jdbc:mysql://140.119.19.73:3315/";
	String database = "mongroup7"; 
	String url = server + database + "?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
	String username = "mongroup7"; 
	String password = "qug6740";
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	
	public ModifyFrame(String name, JTextArea text) {
		

		JLabel label1 = new JLabel("Please select the restaurant you want to modify:");
		JComboBox<String> restaurant = new JComboBox<String>(rest);
		JPanel panel1 = new JPanel();
		panel1.add(label1);
		panel1.add(restaurant);
		panel1.setBackground(new Color(240, 255, 240));
		label1.setFont(new Font("Arial", Font.PLAIN, 14));
		
		JLabel label2 = new JLabel("Please enter your new comments:", SwingConstants.CENTER);
		JLabel Frequency= new JLabel("Frequency: ");
	    Frequency.setFont(new Font("Calibri", Font.PLAIN, 18));
	    label2.setFont(new Font("Arial", Font.PLAIN, 16));
	    JComboBox<String> frequency= new JComboBox<String>(freq);
	    JPanel panel2 = new JPanel();
	    panel2.add(Frequency);
	    panel2.add(frequency);
	    panel2.setBackground(new Color(240, 255, 240));
		
	    JLabel Rating= new JLabel("Rate: ");
	    Rating.setFont(new Font("Calibri", Font.PLAIN, 18));
	    JRadioButton one = new JRadioButton("1");
	    JRadioButton two = new JRadioButton("2");
	    JRadioButton three = new JRadioButton("3");
	    JRadioButton four = new JRadioButton("4");
	    JRadioButton five = new JRadioButton("5");
	    five.setSelected(true);
	    one.setBackground(new Color(240, 255, 240));
	    two.setBackground(new Color(240, 255, 240));
	    three.setBackground(new Color(240, 255, 240));
	    four.setBackground(new Color(240, 255, 240));
	    five.setBackground(new Color(240, 255, 240));
	    ButtonGroup rating = new ButtonGroup(); 
	    rating.add(one);
	    rating.add(two);
	    rating.add(three);
	    rating.add(four);
	    rating.add(five);
	    JPanel panel3 = new JPanel();
	    panel3.add(Rating);
	    panel3.add(one);
	    panel3.add(two);
	    panel3.add(three);
	    panel3.add(four);
	    panel3.add(five);
	    panel3.setBackground(new Color(240, 255, 240));
	    
	    JLabel Comments = new JLabel("Comments: ", SwingConstants.CENTER);
	    Comments.setFont(new Font("Calibri", Font.PLAIN, 18));
	    JTextArea comments = new JTextArea(20,40);
	    JPanel panel4 = new JPanel();
	    panel4.add(Comments, BorderLayout.NORTH);
	    panel4.add(comments, BorderLayout.CENTER);
	    panel4.setBackground(new Color(240, 255, 240));
	    
	    JButton button1 = new JButton("reset");
	    JButton button2 = new JButton("submit");
	    button1.setFont(new Font("SansSerif", Font.PLAIN, 16));
	    button2.setFont(new Font("SansSerif", Font.PLAIN, 16));
	    JPanel panel5 = new JPanel();
	    panel5.add(button1);
	    panel5.add(button2);
	    panel5.setBackground(new Color(240, 255, 240));
	    
	    JPanel panel6 = new JPanel(new GridLayout(4,1));
	    panel6.add(panel1);
	    panel6.add(label2);
	    panel6.add(panel2);
	    panel6.add(panel3);
	    panel6.setBackground(new Color(240, 255, 240));
	    
	    JPanel panel7 = new JPanel(new GridLayout(2,1));
	    panel7.add(panel6);
	    panel7.add(panel4);
	    panel7.setBackground(new Color(240, 255, 240));
	    
	    add(panel7, BorderLayout.CENTER);
	    add(panel5, BorderLayout.SOUTH);
		setSize(500, 600);
		setTitle("Modifying my comments");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restaurant.setSelectedIndex(0);
	            comments.setText("");
	            frequency.setSelectedIndex(0);
	            one.setSelected(false); 
	            two.setSelected(false);   
	            three.setSelected(false); 
	            four.setSelected(false); 
	            five.setSelected(true); 
			}
		});
		
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int r;
				if(one.isSelected()) {
		    		r = 1;
		    	} else if(two.isSelected()) {
		    		r = 2;
		    	} else if(three.isSelected()) {
		    		r = 3;
		    	} else if(four.isSelected()) {
		    		r = 4;
		    	} else {
		    		r = 5;
		    	}
				
				try (Connection conn = DriverManager.getConnection(url, username, password)) {
					String query = String.format("UPDATE RateSurvey SET Date = ?, Frequency = ?, Rate = ?, Comments = ? "
							+ "WHERE Name = \'%s\' AND Restaurant = \'%s\'", name, (String)restaurant.getSelectedItem());
					PreparedStatement stat = conn.prepareStatement(query);
					stat.setString(1, dtf.format(LocalDateTime.now()));
					stat.setString(2, frequency.getSelectedItem().toString());
					stat.setInt(3, r);
					stat.setString(4, comments.getText());
					int result = stat.executeUpdate();
					if (result == 0) {
						JOptionPane.showMessageDialog(null, "There is no corresponding comments found.", "Warning", JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Successfully updating your comments!", "Success", JOptionPane.INFORMATION_MESSAGE);
						dispose();
						
						String query1 = String.format("SELECT Restaurant, Date, ID, Frequency, Rate, Comments FROM RateSurvey WHERE Name = \'%s\'", name);
						Statement stat1 = conn.createStatement();
						boolean hasResultSet = stat1.execute(query1);
						if (hasResultSet) {
							ResultSet result1 = stat1.getResultSet();
							text.setText(showResultSet(result1));
							text.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
							result1.close();
						}
					} 
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		
	}
	
	public static String showResultSet(ResultSet result) throws SQLException {
		
		ResultSetMetaData metaData = result.getMetaData();
		int columnCount = metaData.getColumnCount();
		String str = "";
		
		for (int i = 1; i <= columnCount; i++) {
			if(i == 2 || i == 4) {
				str += String.format("%-32s", metaData.getColumnLabel(i));
			} else {
				str += String.format("%-20s", metaData.getColumnLabel(i));
			}
			str += "\t";
		}
		str += "\n";
		
		while (result.next()) {
			for (int i = 1; i <= columnCount; i++) {
				if(i == 2) {
					str += result.getString(i).substring(0, result.getString(i).length()-2);
				} else if(i == 4) {
					str += String.format("%-32s", result.getString(i));
				} else if(i == 6) {
					str += String.format("%-64s", result.getString(i));
			    } else {
					str += result.getString(i);
				}
				str += "\t";
			}
			str += "\n";
		}
		
		return str;
	}
}
