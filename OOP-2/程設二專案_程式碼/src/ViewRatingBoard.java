import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

public class ViewRatingBoard extends JFrame{

	static final int FRAME_WIDTH = 1000;
	static final int FRAME_HEIGHT = 700;
	public ViewRatingBoard() {
		String server = "jdbc:mysql://140.119.19.73:3315/";
		String database = "mongroup7"; 
		String url = server + database + "?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
		String username = "mongroup7"; 
		String password = "qug6740";
		
		ImageIcon icon = new ImageIcon("search.png");
		Image image = icon.getImage().getScaledInstance(20, 20, icon.getImage().SCALE_SMOOTH);
		icon = new ImageIcon(image);
		
		setTitle("Comments");
		JTextArea printRating = new JTextArea(50, 2);
		JLabel label1 = new JLabel("Comments", SwingConstants.CENTER);
		JLabel label2 = new JLabel("Search:");
		JButton button1 = new JButton("Back to home page");
		JButton button2 = new JButton(icon);
		JButton button3 = new JButton("Back to all comments");
		JTextField text = new JTextField(30);
		button2.setSize(20, 20);
		printRating.setEditable(false);
		label1.setFont(new Font("Arial", Font.BOLD, 40));
		label2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		button1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		button3.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			Statement stat = conn.createStatement();
			String query = "SELECT Restaurant, Date, ID, Frequency, Rate, Comments FROM RateSurvey";
			boolean hasResultSet = stat.execute(query);
			if (hasResultSet) {
				ResultSet result = stat.getResultSet();
				printRating.setText(showResultSet(result));
				printRating.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
				result.close();
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		setLayout(new BorderLayout());
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		panel1.add(label1, BorderLayout.CENTER);
		panel2.add(label2);
		panel2.add(text);
		panel2.add(button2);
		panel4.setLayout(new GridLayout(2,1));
		panel4.add(panel1);
		panel4.add(panel2);
		panel3.add(button1, BorderLayout.SOUTH);
		
		panel1.setBackground(new Color(240, 255, 255));
		panel2.setBackground(new Color(240, 255, 255));
		add(panel4, BorderLayout.NORTH);
		
		add(new JScrollPane(printRating), BorderLayout.CENTER);
		
		panel3.setBackground(new Color(240, 255, 255));
		add(panel3, BorderLayout.SOUTH);
		
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try (Connection conn = DriverManager.getConnection(url, username, password)) {
					String query = "SELECT Restaurant, Date, ID, Frequency, Rate, Comments FROM RateSurvey WHERE Restaurant LIKE ?";
					PreparedStatement stat = conn.prepareStatement(query);
					stat.setString(1, "%" + text.getText() + "%");
					boolean hasResultSet = stat.execute();
					if (hasResultSet) {
						ResultSet result = stat.getResultSet();
						printRating.setText(showSearchResult(result));	
						printRating.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
						result.close();
					}
				
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			    panel3.setLayout(new FlowLayout());
			    panel3.add(button1);
			    panel3.add(button3);
			    panel3.updateUI();
			    panel3.repaint();
			}
		});
		
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try (Connection conn = DriverManager.getConnection(url, username, password)) {
					Statement stat = conn.createStatement();
					String query = "SELECT Restaurant, Date, ID, Frequency, Rate, Comments FROM RateSurvey";
					boolean hasResultSet = stat.execute(query);
					if (hasResultSet) {
						ResultSet result = stat.getResultSet();
						printRating.setText(showResultSet(result));
						printRating.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
						result.close();
					}
				
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				panel3.remove(button3);
				panel3.updateUI();
			    panel3.repaint();
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
