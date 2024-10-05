import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RestaurantSurvey extends JFrame {
    Connection conn;
    JButton submit,reset,home;     
    JTextField id;
    JTextArea comments;
    Container cp;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    JComboBox<String> restaurant,frequency;
    JRadioButton one;
    JRadioButton two;
    JRadioButton three;
    JRadioButton four;
    JRadioButton five;
    ButtonGroup rating;
    String[] rest= {"","羹大王","浪速食鋪","Come See 披薩","Juicy Bun","大汗","華越","素還真","波波恰恰","菁英便當","美香味","私房麵","東京小城",
		"提洛斯","左撇子","原丼力","MY 麵屋","飽飽食府","樂山食堂","高句麗","滇味廚房","小木屋","摩斯漢堡","麥當勞","吉野家","八方雲集","Subway"};
    String[] freq= {"First time","Once or twice a week","Three times a week","Above three times a week"};
    private String NAME;
        
    public RestaurantSurvey(Connection conn, String NAME) throws SQLException{
    	JOptionPane.showMessageDialog(null, "ID can be entered at will, but you will not be able to modify it.", "Hint", JOptionPane.INFORMATION_MESSAGE);
    	this.conn=conn;
        this.setTitle("Feedback Form");
        this.setResizable(false);
        this.setSize(540, 550);
        cp= getContentPane();
        this.setLayout(null);
        createHeading();
        createRestaurant();
        createID();
        createFrequency();
        createRate();
        createComments();
        createButton();
        this.cp.setBackground(new java.awt.Color(0xA6E3D8));
        this.NAME = NAME;
    }
    
    public RestaurantSurvey(Connection conn, String NAME, JTextArea text) throws SQLException{
    	JOptionPane.showMessageDialog(null, "ID can be entered at will, but you will not be able to modify it.", "Hint", JOptionPane.INFORMATION_MESSAGE);
    	this.conn=conn;
        this.setTitle("Feedback Form");
        this.setResizable(false);
        this.setSize(540, 550);
        cp= getContentPane();
        this.setLayout(null);
        createHeading();
        createRestaurant();
        createID();
        createFrequency();
        createRate();
        createComments();
        createButtonAndUpdate(text);
        this.cp.setBackground(new java.awt.Color(0xA6E3D8));
        this.NAME = NAME;
    }

    public void createHeading() {
        JLabel heading= new JLabel();
        heading.setText("FEEDBACK SURVEY");
        heading.setFont(new Font("Arial", Font.BOLD, 36));
        heading.setBounds(80,0,440,45);
        this.cp.add(heading);
    }
    public void createRestaurant() {
        JLabel Restaurant=new JLabel();
        Restaurant.setText("Restaurant: ");
        Restaurant.setFont(new Font("Calibri",Font.PLAIN, 18));
        Restaurant.setBounds(50,80,150,28);
        this.cp.add(Restaurant);
        
        restaurant=new JComboBox<String>(rest);
        restaurant.setBounds(230,80,240,28);
        this.cp.add(restaurant);
    } 

    public void createID() {
        JLabel ID= new JLabel();
        ID.setText("ID: ");
        ID.setFont(new Font("Calibri", Font.PLAIN, 18));
        ID.setBounds(50,125,150, 28);
        this.cp.add(ID);

        id = new JTextField();
        id.setBounds(230, 125, 240, 28);
        this.cp.add(id);
    }
    
    public void createFrequency() {
        JLabel Frequency= new JLabel();
        Frequency.setText("Frequency: ");
        Frequency.setFont(new Font("Calibri", Font.PLAIN, 18));
        Frequency.setBounds(50,175,250,28);
        this.cp.add(Frequency);

        frequency= new JComboBox<String>(freq);
        frequency.setBounds(230,175,200,28);
        this.cp.add(frequency);
    }

    public void createRate() {
        JLabel Rating= new JLabel();
        Rating.setText("Rate: ");
        Rating.setFont(new Font("Calibri", Font.PLAIN, 18));
        Rating.setBounds(50,235,100,28);
        this.cp.add(Rating);

        one=new JRadioButton("1");
        two=new JRadioButton("2");
        three=new JRadioButton("3");
        four=new JRadioButton("4");
        five=new JRadioButton("5");
        five.setSelected(true);
        
        one.setBackground(new java.awt.Color(0xA6E3D8));
        two.setBackground(new java.awt.Color(0xA6E3D8));
        three.setBackground(new java.awt.Color(0xA6E3D8));
        four.setBackground(new java.awt.Color(0xA6E3D8));
        five.setBackground(new java.awt.Color(0xA6E3D8));

        one.setBounds(230, 235, 40, 28);
        two.setBounds(280, 235, 40, 28);
        three.setBounds(330, 235, 40, 28);
        four.setBounds(380, 235, 40, 28);
        five.setBounds(430, 235, 40, 28);
        five.setSelected(true);

        this.add(one);
        this.add(two);
        this.add(three);
        this.add(four);
        this.add(five);

        rating= new ButtonGroup(); 
        rating.add(one);
        rating.add(two);
        rating.add(three);
        rating.add(four);
        rating.add(five);
    }
    
    public void createComments() {
        JLabel Comments= new JLabel();
        Comments.setText("Comments : ");
        Comments.setFont(new Font("Calibri", Font.PLAIN, 18));
        Comments.setBounds(50,285,170,28);
        this.cp.add(Comments);

        comments = new JTextArea(20,40);
        comments.setBounds(230, 285, 240, 84);
        this.cp.add(comments);
     }
    
    public void createButton() throws SQLException {
        submit= new JButton("Submit");
        submit.setFont(new Font("Calibri", Font.PLAIN, 18));
        submit.setBounds(165, 450, 100, 28);
        this.cp.add(submit);
        submit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e){
                boolean flag=false;
                
                	if(restaurant.getSelectedItem().toString().equals(""))
                    {
                    	JOptionPane.showMessageDialog(null, "Restaurant cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else if((id.getText().isEmpty()) || (id.getText() == null))
                    {
                        JOptionPane.showMessageDialog(null, "ID cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        flag=true; 
                        
	                    if(flag)
	                    {
	                        String r;
	                        int r2;
	                        if(one.isSelected()) {
	                            r="One star";
	                            r2=1;
	                        }
	                        else if(two.isSelected()) {
	                            r="Two stars";
	                            r2=2;
	                        }    
	                        else if(three.isSelected()) {
	                            r="Three stars";   
	                            r2=3;
	                        }
	                        else if(four.isSelected()) {
	                            r="Four stars";
	                            r2=4;
	                        }
	                        else {
	                        	r="Five stars";
	                        	r2=5;
	                        }
	                        
	                        String s1= "Thank you for your valuable Feedback!\n\nYour Responses:\n";
	                        String s2= "Restaurant: "+restaurant.getSelectedItem().toString()+"\nName: "+NAME+"\nID: "+id.getText()+"\nFrequency: "+(String)frequency.getSelectedItem()+"\nRate: "+r+"\nComments: "+comments.getText();
	                        String s3= "\nTime: "+dtf.format(LocalDateTime.now());
	                        String disp=s1+s2+s3;
	                        JOptionPane.showMessageDialog(null, disp, "Success", JOptionPane.INFORMATION_MESSAGE);
	                        setVisible(false);
	                        
	                        String r1=restaurant.getSelectedItem().toString();
	                        String t=dtf.format(LocalDateTime.now());
	                        String n = NAME;
	                        String i=id.getText(); 
	                        String f=frequency.getSelectedItem().toString();
	                        String c=comments.getText();
	                        
	                        
	                        java.sql.PreparedStatement insert;
	                        
	                        try {
	                        	insert=conn.prepareStatement("INSERT INTO RateSurvey(Restaurant, Date, Name, ID, Frequency, Rate, Comments) VALUES (?,?,?,?,?,?,?)");
	                        	insert.setString(1, r1);
	    						insert.setString(2, t);
	    						insert.setString(3, n);
	    						insert.setString(4, i);
	    						insert.setString(5, f);
	    						insert.setInt(6, r2);
	    						insert.setString(7, c);
	    						insert.executeUpdate();
	                        }
	                        catch(SQLException ex) {
	                        	ex.printStackTrace();
	                        }
	                    } 
	                } 
	        }
        });

        reset= new JButton("Reset");
        reset.setFont(new Font("Calibri", Font.PLAIN, 18));
        reset.setBounds(275, 450, 100, 28);
        this.cp.add(reset);

        reset.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                 id.setText(null);
                 comments.setText(null);
                 frequency.setSelectedIndex(0);
                 restaurant.setSelectedIndex(0);
                 one.setSelected(false); 
                 two.setSelected(false);   
                 three.setSelected(false); 
                 four.setSelected(false); 
                 five.setSelected(true); 
        	}
             
        });
        
        home=new JButton("Home");
        home.setFont(new Font("Calibri",Font.PLAIN,18));
        home.setBounds(385,450,100,28);
        this.cp.add(home);
        home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
        	
        });
        
    }
    
    public void createButtonAndUpdate(JTextArea text) throws SQLException {
        submit= new JButton("Submit");
        submit.setFont(new Font("Calibri", Font.PLAIN, 18));
        submit.setBounds(165, 450, 100, 28);
        this.cp.add(submit);
        submit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e){
                boolean flag=false;
                
                	if(restaurant.getSelectedItem().toString().equals(""))
                    {
                    	JOptionPane.showMessageDialog(null, "Restaurant cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else if((id.getText().isEmpty()) || (id.getText() == null))
                    {
                        JOptionPane.showMessageDialog(null, "ID cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        flag=true; 
                        
	                    if(flag)
	                    {
	                        String r;
	                        int r2;
	                        if(one.isSelected()) {
	                            r="One star";
	                            r2=1;
	                        }
	                        else if(two.isSelected()) {
	                            r="Two stars";
	                            r2=2;
	                        }    
	                        else if(three.isSelected()) {
	                            r="Three stars";   
	                            r2=3;
	                        }
	                        else if(four.isSelected()) {
	                            r="Four stars";
	                            r2=4;
	                        }
	                        else {
	                        	r="Five stars";
	                        	r2=5;
	                        }
	                        
	                        String s1= "Thank you for your valuable Feedback!\n\nYour Responses:\n";
	                        String s2= "Restaurant: "+restaurant.getSelectedItem().toString()+"\nName: "+NAME+"\nID: "+id.getText()+"\nFrequency: "+(String)frequency.getSelectedItem()+"\nRate: "+r+"\nComments: "+comments.getText();
	                        String s3= "\nTime: "+dtf.format(LocalDateTime.now());
	                        String disp=s1+s2+s3;
	                        JOptionPane.showMessageDialog(null, disp, "Success", JOptionPane.INFORMATION_MESSAGE);
	                        setVisible(false);
	                        
	                        String r1=restaurant.getSelectedItem().toString();
	                        String t=dtf.format(LocalDateTime.now());
	                        String n = NAME;
	                        String i=id.getText(); 
	                        String f=frequency.getSelectedItem().toString();
	                        String c=comments.getText();
	                        
	                        
	                        java.sql.PreparedStatement insert;
	                        
	                        try {
	                        	insert=conn.prepareStatement("INSERT INTO RateSurvey(Restaurant, Date, Name, ID, Frequency, Rate, Comments) VALUES (?,?,?,?,?,?,?)");
	                        	insert.setString(1, r1);
	    						insert.setString(2, t);
	    						insert.setString(3, n);
	    						insert.setString(4, i);
	    						insert.setString(5, f);
	    						insert.setInt(6, r2);
	    						insert.setString(7, c);
	    						insert.executeUpdate();
	                        }
	                        catch(SQLException ex) {
	                        	ex.printStackTrace();
	                        }
	                        try {
	                			String query = String.format("SELECT Restaurant, Date, ID, Frequency, Rate, Comments FROM RateSurvey WHERE Name = \'%s\'", NAME);
	                			Statement stat = conn.createStatement();
	                			boolean hasResultSet = stat.execute(query);
	                			if (hasResultSet) {
	                				ResultSet result = stat.getResultSet();
	                				text.setText(showResultSet(result));	
	                				text.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
	                				result.close();
	                			}
	                		} catch (SQLException e1) {
	                			e1.printStackTrace();
	                		}
	                    } 
	                } 
	        }
        });

        reset= new JButton("Reset");
        reset.setFont(new Font("Calibri", Font.PLAIN, 18));
        reset.setBounds(275, 450, 100, 28);
        this.cp.add(reset);

        reset.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                 id.setText(null);
                 comments.setText(null);
                 frequency.setSelectedIndex(0);
                 restaurant.setSelectedIndex(0);
                 one.setSelected(false); 
                 two.setSelected(false);   
                 three.setSelected(false); 
                 four.setSelected(false); 
                 five.setSelected(true); 
        	}
             
        });
        
        home=new JButton("Home");
        home.setFont(new Font("Calibri",Font.PLAIN,18));
        home.setBounds(385,450,100,28);
        this.cp.add(home);
        home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
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