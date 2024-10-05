import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import java.util.ArrayList;
public class ChangePhone extends JFrame{
	Connection connect;
	Statement stat;
	private JLabel before, after, phone;
	private JTextField beginphone,changephone;
	private JButton submit;
	private JPanel panel1,panel2, panel3, showpanel;
	private static ArrayList <String> phonelist,namelist;
	
	public ChangePhone(Connection connect, JLabel ph) throws SQLException{
		setTitle("Change Phone");
		this.connect=connect;
		this.phone = ph;
		phonelist=new ArrayList<String>();
		namelist=new ArrayList<String>();
		setSize(400,300);
		setVisible(true);
		CreateLabel();
		CreateTextField();
		CreateButton();
		CreateLayout();
	}
	public void CreateLabel() {
		before=new JLabel("Original phone number: ");
		after=new JLabel("New phone number: ");
		before.setFont(new Font("Arial", Font.PLAIN, 16));
		after.setFont(new Font("Arial", Font.PLAIN, 16));
	}
	public void CreateTextField() {
		beginphone=new JTextField(15);
		changephone=new JTextField(15);
	}
	public void CreateButton() throws SQLException{
		submit=new JButton("Submit");
		submit.setFont(new Font("SansSerif", Font.PLAIN, 16));
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					stat=connect.createStatement();
					String p1=beginphone.getText();
					String p2=changephone.getText();
					String query="SELECT Phone FROM personalInfo";
					boolean hasresult=stat.execute(query);
					if(hasresult==true) {
						ResultSet result=stat.getResultSet();
						showPhone(result);
						if(getPhonelist().contains(p1)) {
							String que="SELECT Name FROM personalInfo";
							boolean hasre=stat.execute(que);
							if(hasre==true) {
								ResultSet re=stat.getResultSet();
								showName(re);
								String name=(String) getNlist().get(getPhonelist().indexOf(p1));
								String str="UPDATE personalInfo SET Phone=? WHERE Name=?";
								PreparedStatement prepare=connect.prepareStatement(str);
								prepare.setString(1, p2);
								prepare.setString(2, name);
								prepare.execute();
								setVisible(false);
								JOptionPane.showMessageDialog(null, "Successfully changing your phone!", "Success", JOptionPane.INFORMATION_MESSAGE);
								phone.setText(p2);
							}
						} else {
								JOptionPane.showMessageDialog(null, "Original phone number is wrong","Error",JOptionPane.ERROR_MESSAGE);
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
		panel1.add(beginphone);
		panel1.setBackground(new Color(245, 245, 245));
		panel2=new JPanel(new FlowLayout());
		panel2.add(after);
		panel2.add(changephone);
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
	public static void showPhone(ResultSet result)throws SQLException{
		ResultSetMetaData metaData = result.getMetaData();
		int columnCount = metaData.getColumnCount();
		while (result.next()) {		
			for (int i = 1; i <= columnCount; i++) {
				phonelist.add(result.getString(i));	
		}
	}
	}

	public ArrayList getPhonelist() {
		return this.phonelist;
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