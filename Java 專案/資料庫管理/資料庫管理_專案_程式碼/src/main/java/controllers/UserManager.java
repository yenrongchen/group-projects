package controllers;

import entities.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class UserManager {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/db_project?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASS = "000000";
	
	private Connection conn;

	public UserManager() {
		mysql_connect();
	}

	public void mysql_connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (ClassNotFoundException e) {
			System.out.println("Can't find driver");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean login(String account, String password) {
		try {
			PreparedStatement stat = conn.prepareStatement("SELECT * FROM `user` "
														 + "WHERE Account = ? "
														 + "AND Password = ?");
			stat.setString(1, account);
			stat.setString(2, password);

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String register(String account, String email, String password, String repassword) {
		try {
			PreparedStatement stat = conn.prepareStatement("SELECT * "
														 + "FROM `user` WHERE Account = ?");
			stat.setString(1, account);
			ResultSet rs = stat.executeQuery();
			if (rs.next()) {
				return "此名稱已被使用，請使用別的名稱";
			}

			stat = conn.prepareStatement("SELECT * "
									   + "FROM `user` WHERE Email = ?");
			stat.setString(1, email);
			rs = stat.executeQuery();
			if (rs.next()) {
				return "此Email已被使用，請使用另一個Email";
			}
			
			if(!password.equals(repassword)) {
				return "您輸入的密碼前後不一致";
			}

			stat = conn.prepareStatement("INSERT INTO user(Account, Email, Password)"
									   + "VALUES (?, ?, ?);");
			stat.setString(1, account);
			stat.setString(2, email);
			stat.setString(3, password);
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return "發生未知錯誤";
		}
		return "註冊成功！";
	}
	
	public void sendVerification(String email, String code) {
		String account = "restaurantchooserdbms@gmail.com";
		String password = "hljuiqiciufrhjfp";
		
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        
        Session session = Session.getInstance(prop,
        	new javax.mail.Authenticator() {
            	protected PasswordAuthentication getPasswordAuthentication() {
            		return new PasswordAuthentication(account, password);
                }
            }
        );
        
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("restaurantchooserdbms@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email));
            message.setSubject(code + "是您的驗證碼");
            message.setText("非常感謝您使用餐廳選擇器！\n"
      			  			+ code + "是您的驗證碼，"
      			  			+ "請在餐廳選擇器登入頁面輸入驗證碼，即可完成身分確認！\n");

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
	}
	
	public void activeAccount(String email) {
		try {
			PreparedStatement stat = conn.prepareStatement("UPDATE user "
														 + "SET Status = ? "
														 + "WHERE Email = ? ");
			stat.setString(1, "Activated");
			stat.setString(2, email);
			stat.executeUpdate();
		}catch(Exception e) {
			
		}
	}
	
	public int getUserId(String account) {
		try {
			PreparedStatement stat = conn.prepareStatement("SELECT UserID "
														 + "FROM `user` "
														 + "WHERE Account = ?");
			stat.setString(1, account);
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				return Integer.parseInt(rs.getString("UserID"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public boolean writeReview(String uid, String rid, String comment, String star) {
		try {
			PreparedStatement stat = conn.prepareStatement("INSERT INTO Review(UserID, RestID, Comment, Stars)"
														 + " VALUES(?, ?, ?, ?)");
			stat.setString(1, uid);
			stat.setString(2, rid);
			stat.setString(3, comment);
			stat.setString(4, star);
			stat.executeUpdate();
			
			return true;
			
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String addFavorite(String uid, String rid) {
		try {
			if(uid.equals("1")) {
				return "不好意思，體驗時無法使用此功能。";
			}
			
			PreparedStatement stat = conn.prepareStatement("SELECT COUNT(*) "
															+ "FROM Collection "
														 	+ "WHERE UserID = ?");
			stat.setString(1, uid);
			ResultSet rs = stat.executeQuery();
			
			PreparedStatement stat1 = conn.prepareStatement("SELECT COUNT(*) AS count "
					 										+ "FROM Collection "
					 										+ "WHERE UserID = ? AND RestID = ?");
			stat1.setString(1, uid);
			stat1.setString(2, rid);
			ResultSet checkRepeat = stat1.executeQuery();
			
			if(checkRepeat.next()) {
				if(Integer.parseInt(checkRepeat.getString("count")) >= 1) {
					return "您已收藏過這家餐廳!";
				}
			}
			
			if(rs.next()) {
				if(Integer.parseInt(rs.getString("COUNT(*)")) >= 3) {
					return "您收藏的餐廳數量已達上限!";
				}
			}
			
			stat = conn.prepareStatement("INSERT INTO Collection (UserID, RestID)"
					 				   + "VALUE(?,?)");
			stat.setString(1, uid);
			stat.setString(2, rid);
			stat.executeUpdate();
			return "新增成功";
		} catch(Exception e) {
			e.printStackTrace();
			return "新增失敗。";
		}
	}
	
	public ArrayList<String> getFavorite(String uid){
		ArrayList<String> res = new ArrayList<String>();
		int count = 0;
		try {
			PreparedStatement stat = conn.prepareStatement("SELECT r.NAME "
														 + "FROM restaurant AS r, collection AS c "
														 + "WHERE c.UserID = ? "
														 + "AND c.RestID = r.RestID");
			stat.setString(1, uid);
			ResultSet rs = stat.executeQuery();
			
			while(rs.next() && count < 3) {
				res.add(rs.getString("Name"));
				count++;
			}
			return res;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String deleteFavorite(String uid, String rid) {
		try {
			PreparedStatement stat = conn.prepareStatement("DELETE "
														 + "FROM Collection "
														 + "WHERE UserID = ? "
														 + "AND RestID = ?;");
			stat.setString(1, uid);
			stat.setString(2, rid);
			stat.executeUpdate();
			return "刪除成功";

		} catch (SQLException e) {
			e.printStackTrace();
			return "刪除失敗";
		}
	}
	
	public User getUserByID(String uid) {
		try {
			PreparedStatement stat = conn.prepareStatement("SELECT * "
														 + "FROM user "
														 + "WHERE UserID = ?");
			stat.setString(1, uid);
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				User u = new User(rs.getString("UserID"), rs.getString("Account"), rs.getString("Password"), rs.getString("Email"));
				return u;
			}else {
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String changeAccount(String uid, String account) {
		try {
			PreparedStatement stat = conn.prepareStatement("UPDATE user "
														 + "SET Account = ? "
														 + "WHERE UserID = ? ");
			stat.setString(1, account);
			stat.setString(2, uid);
			stat.executeUpdate();
			return "修改成功!";
		}catch(Exception e) {
			e.printStackTrace();
			return "修改失敗";
		}
	}
	
	public String changeEmail(String uid, String email) {
		try {
			PreparedStatement stat = conn.prepareStatement("UPDATE user "
														 + "SET Email = ? "
														 + "WHERE UserID = ? ");
			stat.setString(1, email);
			stat.setString(2, uid);
			stat.executeUpdate();
			stat.close();
			return "修改成功!";
		}catch(Exception e) {
			e.printStackTrace();
			return "修改失敗";
		}
	}
	
	public String changePassword(String uid, String password) {
		try {
			PreparedStatement stat = conn.prepareStatement("UPDATE user "
														 + "SET Password = ? "
														 + "WHERE UserID = ? ");
			stat.setString(1, password);
			stat.setString(2, uid);
			stat.executeUpdate();
			stat.close();
			return "修改成功!";
		}catch(Exception e) {
			e.printStackTrace();
			return "修改失敗";
		}
	}
	
	public void close(){
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}
}
