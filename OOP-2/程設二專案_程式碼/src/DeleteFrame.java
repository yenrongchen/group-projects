import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class DeleteFrame extends JFrame {

	static final int FRAME_WIDTH = 800;
	static final int FRAME_HEIGHT = 600;
	//private String name;
	
	String server = "jdbc:mysql://140.119.19.73:3315/";
	String database = "mongroup7"; 
	String url = server + database + "?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
	String username = "mongroup7"; 
	String password = "qug6740";
	
	private String[] rest= {"","羹大王","浪速食鋪","Come See 披薩","Juicy Bun","大汗","華越","素還真","波波恰恰","菁英便當","美香味","私房麵","東京小城",
			"提洛斯","左撇子","原丼力","MY 麵屋","飽飽食府","樂山食堂","高句麗","滇味廚房","小木屋","摩斯漢堡","麥當勞","吉野家","八方雲集","Subway"};
	
	public DeleteFrame(String name, JTextArea printComment) {
		
		JLabel label1 = new JLabel("Please select the restaurant you want to delete:");
		JLabel label2 = new JLabel("Here is your comments:");
		JLabel label3 = new JLabel("Are you sure to delete these comments?");
		JComboBox<String> restaurant = new JComboBox<String>(rest);
		JTextArea text = new JTextArea(50,75);
		text.setEditable(false);
		label1.setFont(new Font("Arial", Font.PLAIN, 16));
		label2.setFont(new Font("Arial", Font.PLAIN, 16));
		label3.setFont(new Font("Arial", Font.PLAIN, 16));
		
		JButton button1 = new JButton("View corresponding comments");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try (Connection conn = DriverManager.getConnection(url, username, password)) {
					String query = String.format("SELECT Restaurant, Date, ID, Frequency, Rate, Comments FROM RateSurvey WHERE Restaurant = \'%s\' AND Name = \'%s\'", (String)restaurant.getSelectedItem(), name);
					Statement stat = conn.createStatement();
					boolean hasResultSet = stat.execute(query);
					if (hasResultSet) {
						ResultSet result = stat.getResultSet();
						text.setText(showSearchResult(result));	
						text.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
						result.close();
						
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JButton button2 = new JButton("Delete");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try (Connection conn = DriverManager.getConnection(url, username, password)) {
					String query = String.format("DELETE FROM RateSurvey WHERE Restaurant = \'%s\' AND Name = \'%s\'", (String)restaurant.getSelectedItem(), name);
					Statement stat = conn.createStatement();
					int hasResultSet = stat.executeUpdate(query);
					if (hasResultSet == 0) {
						JOptionPane.showMessageDialog(null, "There is no corresponding comments found.", "Warning", JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Successfully deleting your comments.", "Process completed", JOptionPane.INFORMATION_MESSAGE);
						dispose();
						String query1 = String.format("SELECT Restaurant, Date, ID, Frequency, Rate, Comments FROM RateSurvey WHERE Name = \'%s\'", name);
						Statement stat1 = conn.createStatement();
						boolean hasResultSet1 = stat1.execute(query1);
						if (hasResultSet1) {
							ResultSet result1 = stat1.getResultSet();
							printComment.setText(showResultSet(result1));
							printComment.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
							result1.close();
						}
					}
					
					
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		button1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		button2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		JPanel panel1 = new JPanel();
		panel1.add(label1);
		panel1.add(restaurant);
		panel1.setBackground(new Color(253, 245, 230));
		label1.setFont(new Font("Arial", Font.PLAIN, 14));
		
		JPanel panel2 = new JPanel();
		panel2.add(button1, BorderLayout.CENTER);
		panel2.setBackground(new Color(253, 245, 230));
		
		JPanel panel4 = new JPanel();
		panel4.add(label2, BorderLayout.SOUTH);
		panel4.setBackground(new Color(253, 245, 230));
		
		JPanel panel3 = new JPanel(new GridLayout(3,1));
		panel3.add(panel1);
		panel3.add(panel2);
		panel3.add(panel4);
		
		JPanel panel5 = new JPanel();
		panel5.add(label3, BorderLayout.CENTER);
		panel5.setBackground(new Color(253, 245, 230));
		
		
		JPanel panel8 = new JPanel();
		panel8.add(button2);
		panel8.setBackground(new Color(253, 245, 230));
		
		JPanel panel9 = new JPanel(new GridLayout(1,2));
		panel9.add(panel5);
		panel9.add(panel8);
		
		setTitle("Deleting my comments");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		add(panel3, BorderLayout.NORTH);
		add(new JScrollPane(text), BorderLayout.CENTER);
		add(panel9, BorderLayout.SOUTH);
		
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
	
	public static String showSearchResult(ResultSet result) throws SQLException {
	
		ResultSetMetaData metaData = result.getMetaData();
		int columnCount = metaData.getColumnCount();
		String str = "";
		int count = 0;
		
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
			count++;
		}
		
		if(count == 0) {
			str += "No results found";
		}
		
		return str;
	}

}
