import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
;public class LoginFrame extends JFrame{

	private String username,passw,id,mail;
	private JLabel user,password;
	private JTextField u,passwords;
	private JButton login,enroll,forget;
	private JPanel panel1,panel2,panel3,operatepanel;
	private EnrollFrame enrollf;
	private SetupFrame set,setf;
	private HomeFrame home;

	private boolean haslogin;
	Connection connect;
	Statement stat,stat1;
	private static ArrayList <String> namelist,pwlist,idlist,maillist;
	
	public LoginFrame(HomeFrame home)throws SQLException {
		this.home = home;
	}
	public void creatAll(Connection conn) throws SQLException {
		this.connect=conn;
		setTitle("Login");
		setSize(350,250);
		setVisible(true);
		createLabel();
		createTextField();
		createButton();
		createLayout();
		haslogin = false;
		namelist=new ArrayList<String>();
		pwlist=new ArrayList<String>();
		idlist=new ArrayList<String>();
		maillist=new ArrayList<String>();
	}
	public void createLabel() {
		user=new JLabel("Username: ");
		password=new JLabel("Passwords: ");
		user.setFont(new Font("Arial", Font.PLAIN, 16));
		password.setFont(new Font("Arial", Font.PLAIN, 16));
	}
	public void createTextField() {
		u=new JTextField(15);
		passwords=new JTextField(15);
	}
	public void createButton() throws SQLException{
		login=new JButton("Login");
		login.setFont(new Font("SansSerif", Font.PLAIN, 16));
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String id="";
				String mail="";
				try {
					stat=connect.createStatement();
					String user=u.getText();
					String PW=passwords.getText();
					String query="SELECT Name FROM personalInfo";
					boolean hasresult=stat.execute(query);
					
					if(hasresult==true) {
						ResultSet result=stat.getResultSet();
						showName(result);
						
						if(getNlist().contains(user)) {
							
							String que="SELECT Password FROM personalInfo";
							boolean right=stat.execute(que);
							if(right==true) {
								ResultSet re=stat.getResultSet();
								showPassword(re);
								if(getPWlist().get(getNlist().indexOf(user)).equals(PW)) {
									JOptionPane.showMessageDialog(null, "Welcome!");
									
									String s="SELECT UserID FROM personalInfo";
									boolean has=stat.execute(s);
									if(has==true) {
										ResultSet r=stat.getResultSet();
										showID(r);
										id=(String)getIDlist().get(getNlist().indexOf(user));
									}
									String st="SELECT Phone FROM personalInfo";
									boolean hasre=stat.execute(st);
									if(hasre==true) {
										ResultSet rs=stat.getResultSet();
										showMail(rs);
										mail=(String)getMaillist().get(getNlist().indexOf(user));
									}
									
									home.refresh();
									home.setSuccess(true);
									home.setName(user);
									home.setID(id);
									home.setPW(PW);
									home.setMail(mail);
									dispose();
									re.close();
									result.close();
									stat.close();
								}else {
									JOptionPane.showMessageDialog(null,"Password is wrong","Error",JOptionPane.ERROR_MESSAGE);
								}
							}
						}else {
							JOptionPane.showMessageDialog(null,"Please enroll before login or your name is wrong","Error",JOptionPane.ERROR_MESSAGE);
						}
						setID(id);
						
						setMail(mail);
						}
					}
//					
				catch (SQLException e1) {
						e1.printStackTrace();
				}
				
			}
			
		});
		enroll=new JButton("Enroll");
		enroll.setFont(new Font("SansSerif", Font.PLAIN, 16));
		enroll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					enrollf=new EnrollFrame(connect);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
//		forget=new JButton("Forget password");
		
	}
	public void createLayout() {
		panel1=new JPanel(new FlowLayout());
		panel1.add(user);
		panel1.add(u);
		panel1.setBackground(new Color(255, 250, 240));
		panel2=new JPanel(new FlowLayout());
		panel2.add(password);
		panel2.add(passwords);
		panel2.setBackground(new Color(255, 250, 240));
		panel3=new JPanel(new FlowLayout());
		panel3.add(login);
		panel3.add(enroll);
		panel3.setBackground(new Color(255, 250, 240));
//		panel3.add(forget);
		operatepanel=new JPanel(new GridLayout(3,1));
		operatepanel.add(panel1);
		operatepanel.add(panel2);
		operatepanel.add(panel3);
		add(operatepanel);
	}
	public void setName(String name) {
		this.username=name;
	}
	public void setPW(String pw) {
		this.passw=pw;	
	}
	public String getName() {
		return this.username;
	}
	public String getPW() {
		return this.passw;
	}
	public void setLogin() {
		this.haslogin=true;
	}
	public boolean getLogin() {
		return this.haslogin;
	}
	
	public static void showName(ResultSet result)throws SQLException{
		ResultSetMetaData metaData = result.getMetaData();
		int columnCount = metaData.getColumnCount();
		while (result.next()) {			
			for (int i = 1; i <= columnCount; i++) {
				namelist.add(result.getString(i));
			}
		}	
	}
	public ArrayList<String> getNlist() {
		return this.namelist;
	}
	public static void showPassword(ResultSet result)throws SQLException{
		ResultSetMetaData metaData = result.getMetaData();
		int columnCount = metaData.getColumnCount();
		while (result.next()) {
			for (int i = 1; i <= columnCount; i++) {
				pwlist.add(result.getString(i));
		}
	}
	}
	public ArrayList<String> getPWlist() {
		return this.pwlist;
	}
	public static void showID(ResultSet result)throws SQLException{
		ResultSetMetaData metaData = result.getMetaData();
		int columnCount = metaData.getColumnCount();
		while (result.next()) {		
			for (int i = 1; i <= columnCount; i++) {
				idlist.add(result.getString(i));	
		}
	}
	}
	public ArrayList<String> getIDlist() {
		return this.idlist;
	}
	public void setID(String id) {
		this.id=id;
	}
	public String getID() {
		return this.id;
	}
	public static void showMail(ResultSet result)throws SQLException{
		ResultSetMetaData metaData = result.getMetaData();
		int columnCount = metaData.getColumnCount();
		while (result.next()) {		
			for (int i = 1; i <= columnCount; i++) {
				maillist.add(result.getString(i));	
		}
	}
	}
	public ArrayList<String> getMaillist() {
		return this.maillist;
	}
	public void setMail(String mail) {
		this.mail=mail;
	}
	public String getMail() {
		return this.mail;
	}
	public void clear() {
		u.setText("");
		passwords.setText("");
	}
}