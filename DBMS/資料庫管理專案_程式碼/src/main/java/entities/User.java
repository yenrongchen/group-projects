package entities;

public class User {
	private String userID;
	private String account;
	private String password;
	private String email;
	private String type;
 
	public User(String id) {
		this.userID = id;
	}
	
	public User(String id, String account, String password) {
		userID = id;
		this.account = account;
		this.password = password;
	}
	
	public User(String id, String account, String password, String email) {
		userID = id;
		this.account = account;
		this.password = password;
		this.email = email;
	}

	public String getUserID() {
		return userID;
	}
	
	public String getAccount() {
		return account;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getEmail() {
		return email;
	}

	public String getType() {
		return type;
	}
}