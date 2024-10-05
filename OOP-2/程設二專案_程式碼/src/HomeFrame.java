import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.awt.Font;
public class HomeFrame extends JFrame{

		private JButton restaurant,view_comment,comment,schoolmeal,setup,login,logout,mycomments;
		private JTextArea activities;
		private JPanel selectpanel,showpanel,mainpanel,basepanel,panel;
		private JScrollPane jsp;
		private LoginFrame loginframe;
		private SetupFrame setupframe;
		private JLabel title,label;
		private boolean success;
		Connection conn;
		private String username,userpassword,id,mail;
		String server = "jdbc:mysql://140.119.19.73:3315/";
		String database = "mongroup7";
		String url = server + database + "?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
		String userName = "mongroup7";
		String password = "qug6740";
		private ArrayList <Restaurant> restaurants = new ArrayList <Restaurant>();
		private ArrayList <String> dt = new ArrayList<String>();

		public HomeFrame() throws SQLException {
			try {
				conn = DriverManager.getConnection(url, userName, password);
				Statement stat = conn.createStatement();
				String query = "SELECT * FROM RestaurantsList;";
				boolean hasResultSet = stat.execute(query);
				if (hasResultSet) {
					ResultSet result = stat.getResultSet(); 
					showResultSet(result, restaurants);
					result.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.success=false;
			setTitle("NCCU美食地圖");
			setSize(520,375);
			createButton();
			createTextArea();
			createLayout();
		}
		public void createButton() throws SQLException {
			restaurant=new JButton("Restaurant");
			restaurant.setFont(new Font("SansSerif", Font.PLAIN, 14));
			restaurant.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					Page1 frame = new Page1(restaurants);	
				}
			});
			view_comment=new JButton("View All Comments");
			view_comment.setFont(new Font("SansSerif", Font.PLAIN, 14));
			view_comment.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					ViewRatingBoard board = new ViewRatingBoard();
				}
			});
			comment=new JButton("Write Comments");
			comment.setFont(new Font("SansSerif", Font.PLAIN, 14));
			comment.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					if(success) {
						RestaurantSurvey rs;
						try {
							rs = new RestaurantSurvey(conn, username);
							rs.setVisible(true);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
  					    JOptionPane.showMessageDialog(null,"Please login before writing comments!","Error",JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			schoolmeal=new JButton("SchoolMeal");
			schoolmeal.setFont(new Font("SansSerif", Font.PLAIN, 14));
			schoolmeal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					Menu menu=new Menu();
				}
			});
			setup=new JButton("Setting");
			setup.setFont(new Font("SansSerif", Font.PLAIN, 14));
			setup.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					
					if(success) {
						try {
							setupframe=new SetupFrame(conn, username, id, userpassword, mail);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Please login","Warning",JOptionPane.INFORMATION_MESSAGE);
						try {
							loginframe = new LoginFrame(getHome());
							loginframe.creatAll(conn);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
			});
			login=new JButton("Login");
			login.setFont(new Font("SansSerif", Font.PLAIN, 14));
			login.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					try {
						loginframe = new LoginFrame(getHome());
						loginframe.creatAll(conn);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			logout=new JButton("Logout");
			logout.setFont(new Font("SansSerif", Font.PLAIN, 14));
			logout.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					setSuccess(false);
					loginframe.clear();
					JOptionPane.showMessageDialog(null, "Successfully logout","Hint",JOptionPane.INFORMATION_MESSAGE);
					refreshAgain();
				}
			});
			mycomments=new JButton("View My Comments");
			mycomments.setFont(new Font("SansSerif", Font.PLAIN, 14));
			mycomments.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					ViewMyComments comment = new ViewMyComments(username);
				}
			});
		}
		public void setName(String name) {
			this.username=name;
		}
		public String getName() {
			return this.username;
		}
		public void setPW(String pw) {
			this.userpassword=pw;
		}
		public String getPW() {
			return this.userpassword;
		}
		public void setSuccess(boolean bool) {
			this.success = bool;
		}
		public boolean getSuccess() {
			return this.success;
		}
		public void setID(String id) {
			this.id=id;
		}
		public String getID() {
			return this.id;
		}
		public void setMail(String mail) {
			this.mail=mail;
		}
		public String getMail() {
			return this.mail;
		}
		public HomeFrame getHome() {
			return this;
		}
		public void createTextArea() {
			activities=new JTextArea(10,25);
			String rule="\n Instructions:\n";
			rule+=" 1.Some functions will not be available before login\n    Available buttons: restaurant, view all comments, school meal \n";
			rule+=" 2.Buttons that can only be used after login: write comments, setting\n";
			rule+=" 3.If you don't have an account, you can register one before you login\n    Please notes: use your real name and fill in all the required information when registering \n";
			rule+=" 4.You can write comments anonymously and decide the presented name at your own discretion \n";
			activities.setText(rule);
			activities.setEditable(false);
			activities.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			showpanel=new JPanel();
			jsp = new JScrollPane(activities);
			showpanel.add(jsp);
			showpanel.setBackground(new Color(240, 245, 255));
		}
		public void createLayout() {
			title=new JLabel("政大美食地圖",JLabel.CENTER);
			Font t=new Font("微軟正黑體",Font.ITALIC,35);
			title.setFont(t);
			basepanel=new JPanel(new BorderLayout());
			basepanel.add(title,BorderLayout.NORTH);
			basepanel.setBackground(new Color(240, 245, 255));
			mainpanel=new JPanel();
			selectpanel=new JPanel(new GridLayout(6, 1, 0, 8));
			selectpanel.add(restaurant, BorderLayout.CENTER);
			selectpanel.add(view_comment);
			selectpanel.add(comment);
			selectpanel.add(schoolmeal);
			selectpanel.add(setup);
			selectpanel.add(login);
			selectpanel.setBackground(new Color(240, 245, 255));
			mainpanel.add(selectpanel);
			mainpanel.add(showpanel);
			mainpanel.setBackground(new Color(240, 245, 255));
			basepanel.add(mainpanel,BorderLayout.CENTER);
			add(basepanel);
		}
		public void refresh() {
			selectpanel.remove(login);
			selectpanel.setLayout(new GridLayout(7,1,0,5));
			selectpanel.add(mycomments, 3);
			selectpanel.add(logout);
			selectpanel.updateUI();
			selectpanel.repaint();
		}
		public void refreshAgain() {
			selectpanel.remove(logout);
			selectpanel.remove(mycomments);
			selectpanel.setLayout(new GridLayout(6,1,0,8));
			selectpanel.add(login);
			selectpanel.updateUI();
			selectpanel.repaint();
		}
		
		public static void showResultSet(ResultSet result, ArrayList restaurants) throws SQLException { 
			ResultSetMetaData metaData = result.getMetaData();
			int columnCount = metaData.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
			}
			while (result.next()) {
				ArrayList <String> dt = new ArrayList<String>();
				for (int i = 1; i <= columnCount; i++) {
					dt.add(result.getString(i));
				}

				Restaurant r = new Restaurant(dt.get(1), dt.get(2), dt.get(3), dt.get(4), dt.get(5), dt.get(6), dt.get(7), dt.get(8), dt.get(9), dt.get(10));
				restaurants.add(r);
				System.out.println(); 
			}
		}
	}