import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class EnrollFrame extends JFrame{
	private JLabel name,gender,userID,password,phon;
	private JTextField names,ID,PW,phone;
	private JButton reset,submit;
	private JRadioButton girl,boy,neither;
	private JPanel panel1,panel5,panel2,panel3,panel4,buttonpanel,operate;
	private static ArrayList <String> namelist = new ArrayList <String>();
	Connection connect;
	Statement stat;
	
	public EnrollFrame(Connection conn) throws SQLException{
		this.connect=conn;
		
		setTitle("Enroll");
		setSize(400,450);
		setVisible(true);
		createLabel();
		createTextField();
		createRadioButton();
		createButton();
		createLayout();
	}
	public void createLabel() {
		name=new JLabel("Username: ");
		gender=new JLabel("Gender: ");
		userID=new JLabel("StudentID: ");
		password=new JLabel("Password: ");
		phon=new JLabel("Phone: ");
		name.setFont(new Font("Arial", Font.PLAIN, 16));
		gender.setFont(new Font("Arial", Font.PLAIN, 16));
		userID.setFont(new Font("Arial", Font.PLAIN, 16));
		password.setFont(new Font("Arial", Font.PLAIN, 16));
		phon.setFont(new Font("Arial", Font.PLAIN, 16));
	}
	public void createTextField() {
		names=new JTextField(10);
		ID=new JTextField(10);
		PW=new JTextField(10);
		phone=new JTextField(10);
	}
	public void createRadioButton() {
		girl=new JRadioButton("Girl");
		boy=new JRadioButton("Boy");
		neither=new JRadioButton("Neither");
		girl.setBackground(new Color(248, 248, 255));
		boy.setBackground(new Color(248, 248, 255));
		neither.setBackground(new Color(248, 248, 255));
		ButtonGroup group=new ButtonGroup();
		group.add(girl);
		group.add(boy);
		group.add(neither);
	}
	public void createButton() throws SQLException{
		submit=new JButton("Submit");
		submit.setFont(new Font("SansSerif", Font.PLAIN, 16));
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name=names.getText();
				String gender=" ";
				if(girl.isSelected()) {
					gender="Girl";
				}else if(boy.isSelected()) {
					gender="Boy";
				}else if(neither.isSelected()) {
					gender="Neither";
				}
				String UserID=ID.getText();
				String Password=PW.getText();
				String Phone=phone.getText();
				
				if(name.equals("")) {
					JOptionPane.showMessageDialog(null,"Error, name can not be empty!","Error",JOptionPane.ERROR_MESSAGE);
				} else if(gender.equals(" ")) {
					JOptionPane.showMessageDialog(null,"Error, gender can not be empty!","Error",JOptionPane.ERROR_MESSAGE);
			    } else if(UserID.equals("")) {
					JOptionPane.showMessageDialog(null,"Error, studentID can not be empty!","Error",JOptionPane.ERROR_MESSAGE);
				} else if(Password.equals("")) {
					JOptionPane.showMessageDialog(null,"Error, password can not be empty!","Error",JOptionPane.ERROR_MESSAGE);
				} else if(Phone.equals("")) {
					JOptionPane.showMessageDialog(null,"Error, phone can not be empty!","Error",JOptionPane.ERROR_MESSAGE);
				} else {
					Statement stat;
					try {
						stat = connect.createStatement();
						String query = "SELECT Name FROM personalInfo";
						boolean hasResultSet = stat.execute(query);
						if (hasResultSet) {
							ResultSet result = stat.getResultSet();
							check(result);
							result.close();
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(getNameList().contains(name)) {
						JOptionPane.showMessageDialog(null,"Error! This username has been used!","Error",JOptionPane.ERROR_MESSAGE);
					} else {
						try {
							PreparedStatement insert;
							insert=connect.prepareStatement("INSERT INTO personalInfo (Name,Gender,UserID,Password,Phone) VALUES (?,?,?,?,?)");
							insert.setString(1,name);
							insert.setString(2,gender);
							insert.setString(3,UserID);
							insert.setString(4,Password);
							insert.setString(5,Phone);
							insert.executeUpdate();
							stat=connect.createStatement();
							JOptionPane.showMessageDialog(null,"Success!","Info",JOptionPane.INFORMATION_MESSAGE);
							dispose();
						}catch(SQLException ex) {
							ex.printStackTrace();
						}
					}
				}			
			}
		});
//		reset=new JButton("Reset");
//		reset.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				
//			}
//		});
	}
	
	public void createLayout() {
		panel1=new JPanel(new FlowLayout());
		panel1.add(name);
		panel1.add(names);
		panel1.setBackground(new Color(248, 248, 255));
		panel5=new JPanel(new FlowLayout());
		panel5.add(gender);
		panel5.add(girl);
		panel5.add(boy);
		panel5.add(neither);
		panel5.setBackground(new Color(248, 248, 255));
		panel2=new JPanel(new FlowLayout());
		panel2.add(userID);
		panel2.add(ID);
		panel2.setBackground(new Color(248, 248, 255));
		panel3=new JPanel(new FlowLayout());
		panel3.add(password);
		panel3.add(PW);
		panel3.setBackground(new Color(248, 248, 255));
		panel4=new JPanel(new FlowLayout());
		panel4.add(phon);
		panel4.add(phone);
		panel4.setBackground(new Color(248, 248, 255));
		buttonpanel=new JPanel(new FlowLayout());
		buttonpanel.add(submit);
		buttonpanel.setBackground(new Color(248, 248, 255));
//		buttonpanel.add(reset);
		operate=new JPanel(new GridLayout(6,1));
		operate.add(panel1);
		operate.add(panel5);
		operate.add(panel2);
		operate.add(panel3);
		operate.add(panel4);
		operate.add(buttonpanel);
		add(operate);
	}
	
	public void check(ResultSet result) throws SQLException {
		ResultSetMetaData metaData = result.getMetaData();
		int columnCount = metaData.getColumnCount();
		while (result.next()) {			
			for (int i = 1; i <= columnCount; i++) {
				namelist.add(result.getString(i));
			}
		}
	}
	
	public ArrayList<String> getNameList() {
		return this.namelist;
	}
	
}