import java.sql.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewMyComments extends JFrame{
	
	private static boolean empty = false;
	static final int FRAME_WIDTH = 800;
	static final int FRAME_HEIGHT = 500;
	
	String server = "jdbc:mysql://140.119.19.73:3315/";
	String database = "mongroup7"; 
	String url = server + database + "?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
	String username = "mongroup7"; 
	String password = "qug6740";
	
	private String[] freq= {"First time","Once or twice a week","Three times a week","Above three times a week"};
	private String[] rest= {"","羹大王","浪速食鋪","Come See 披薩","Juicy Bun","大汗","華越","素還真","波波恰恰","菁英便當","美香味","私房麵","東京小城",
			"提洛斯","左撇子","原丼力","MY 麵屋","飽飽食府","樂山食堂","高句麗","滇味廚房","小木屋","摩斯漢堡","麥當勞","吉野家","八方雲集","Subway"};
	
	public ViewMyComments(String name) {
		
		setTitle("My Comments");
		JTextArea printComment = new JTextArea(50, 2);
		JLabel label1 = new JLabel("My comments", SwingConstants.CENTER);
		JButton button1 = new JButton("Back to home page");
		JButton button2 = new JButton("View all comments");
		JButton button3 = new JButton("Modify my comments");
		JButton button4 = new JButton("Delete my comments");
		button1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		button2.setFont(new Font("SansSerif", Font.PLAIN, 14));
		button3.setFont(new Font("SansSerif", Font.PLAIN, 14));
		button4.setFont(new Font("SansSerif", Font.PLAIN, 14));
		printComment.setEditable(false);
		label1.setFont(new Font("Arial", Font.BOLD, 40));
		
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			String query = String.format("SELECT Restaurant, Date, ID, Frequency, Rate, Comments FROM RateSurvey WHERE Name = \'%s\'", name);
			Statement stat = conn.createStatement();
			boolean hasResultSet = stat.execute(query);
			if (hasResultSet) {
				ResultSet result = stat.getResultSet();
				printComment.setText(showResultSet(result));	
				printComment.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
				result.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewRatingBoard board = new ViewRatingBoard();
			}
		});
		
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conn;
				try {
					conn = DriverManager.getConnection(url, username, password);
					String query = String.format("SELECT Restaurant, Date, ID, Frequency, Rate, Comments FROM RateSurvey WHERE Name = \'%s\'", name);
					Statement stat = conn.createStatement();
					boolean hasResultSet = stat.execute(query);
					if (hasResultSet) {
						ResultSet result = stat.getResultSet();
						empty = check(result);
						result.close();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if(empty == false) {
					JOptionPane.showMessageDialog(null, "You don't have any comment, please write some first.","Hint",JOptionPane.INFORMATION_MESSAGE);
					RestaurantSurvey rs;
					try {
						conn = DriverManager.getConnection(url, username, password);
						rs = new RestaurantSurvey(conn, name, printComment);
						rs.setVisible(true);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					ModifyFrame mf = new ModifyFrame(name, printComment);
				}
			}
		});
		
		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try (Connection conn = DriverManager.getConnection(url, username, password)) {
					String query = String.format("SELECT Restaurant, Date, ID, Frequency, Rate, Comments FROM RateSurvey WHERE Name = \'%s\'", name);
					Statement stat = conn.createStatement();
					boolean hasResultSet = stat.execute(query);
					if (hasResultSet) {
						ResultSet result = stat.getResultSet();
						empty = check(result);
						result.close();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if(empty == false) {
					JOptionPane.showMessageDialog(null, "You don't have any comment, please write some first.","Hint",JOptionPane.INFORMATION_MESSAGE);
					try {
						Connection conn = DriverManager.getConnection(url, username, password);
						RestaurantSurvey rs = new RestaurantSurvey(conn, name, printComment);
						rs.setVisible(true);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					DeleteFrame df = new DeleteFrame(name, printComment);
				}
			}
		});
		
		JPanel panel1 = new JPanel();
		panel1.add(button1);
		panel1.add(button2);
		panel1.add(button3);
		panel1.add(button4);
		panel1.setBackground(new Color(240, 255, 255));
		add(label1, BorderLayout.NORTH);
		add(new JScrollPane(printComment), BorderLayout.CENTER);
		add(panel1, BorderLayout.SOUTH);
		getContentPane().setBackground(new Color(240, 255, 255));
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
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
	
	public static boolean check(ResultSet result) throws SQLException {
		ResultSetMetaData metaData = result.getMetaData();
		int columnCount = metaData.getColumnCount();
		int count = 0;
		
		for (int i = 1; i <= columnCount; i++) {
			
		}
		
		while (result.next()) {
			count++;
		}
		
		if(count == 0) {
			return false;
		} else {
			return true;
		}
	}
}