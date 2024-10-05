import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.awt.event.*;
public class ChangePassword extends JFrame{
	Connection connect;
	Statement stat;
	private JLabel before, after, pw;
	private JTextField beginpw,changepw;
	private JButton submit;
	private JPanel panel1,panel2, panel3, showpanel;
	private static ArrayList<String> pwlist,namelist;
	public ChangePassword(Connection connect, JLabel pw) throws SQLException{
		setTitle("Change Password");
		this.connect=connect;
		this.pw = pw;
		pwlist=new ArrayList<String>();
		namelist=new ArrayList<String>();
		setSize(350,250);
		setVisible(true);
		CreateLabel();
		CreateTextField();
		CreateButton();
		CreateLayout();
	}
	public void CreateLabel() {
		before=new JLabel("Original password: ");
		after=new JLabel("New password: ");
		before.setFont(new Font("Arial", Font.PLAIN, 16));
		after.setFont(new Font("Arial", Font.PLAIN, 16));
	}
	public void CreateTextField() {
		beginpw=new JTextField(15);
		changepw=new JTextField(15);
	}
	public void CreateButton() throws SQLException{
		submit=new JButton("Submit");
		submit.setFont(new Font("SansSerif", Font.PLAIN, 16));
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					stat=connect.createStatement();
					String p1=beginpw.getText();
					String p2=changepw.getText();
					String query="SELECT Password FROM personalInfo";
					boolean hasresult=stat.execute(query);
					if(hasresult==true) {
						ResultSet result=stat.getResultSet();
						showPassword(result);
						if(getPWlist().contains(p1)) {
							String que="SELECT Name FROM personalInfo";
							boolean hasre=stat.execute(que);
							if(hasre==true) {
								ResultSet re=stat.getResultSet();
								showName(re);
								String name=(String) getNlist().get(getPWlist().indexOf(p1));
								String str="UPDATE personalInfo SET Password=? WHERE Name=?";
								PreparedStatement prepare=connect.prepareStatement(str);
								prepare.setString(1, p2);
								prepare.setString(2, name);
								prepare.execute();
								setVisible(false);
								JOptionPane.showMessageDialog(null, "Successfully changing your password!", "Success", JOptionPane.INFORMATION_MESSAGE);
								pw.setText(p2);
								}
							}
						else {
							JOptionPane.showMessageDialog(null,"Original password is wrong","Error",JOptionPane.ERROR_MESSAGE);
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	}
	public void CreateLayout() {
		panel1=new JPanel(new FlowLayout());
		panel1.add(before);
		panel1.add(beginpw);
		panel1.setBackground(new Color(245, 245, 245));
		panel2=new JPanel(new FlowLayout());
		panel2.add(after);
		panel2.add(changepw);
		panel2.setBackground(new Color(245, 245, 245));
		panel3=new JPanel(new FlowLayout());
		panel3.add(submit);
		panel3.setBackground(new Color(245, 245, 245));
		showpanel=new JPanel(new GridLayout(3,1));
		showpanel.add(panel1);
		showpanel.add(panel2);
		showpanel.add(panel3);
		add(showpanel);
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
	public ArrayList getPWlist() {
		return this.pwlist;
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
	public ArrayList getNlist() {
		return this.namelist;
	}
}