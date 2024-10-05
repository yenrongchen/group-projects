import java.util.ArrayList;

public class Restaurant{
	private String name;
	private String price;
	private String schoolGate;
	private String cuisine;
	private String vegan, takeOut; 
	private String seats;
	private String openTime;
	private String telefon;
	private String address;
	
	public Restaurant(String name, String price, String schoolGate, String cuisine, String vegan, String takeOut, String seats, String openTime, String telefon, String address) {
		this.name = name;
		this.price = price;
		this.schoolGate = schoolGate;
		this.cuisine = cuisine;
		this.vegan = vegan;
		this.takeOut = takeOut;
		this.seats = seats;
		this.openTime = openTime;
		this.telefon = telefon;
		this.address = address;
	}
	
	public String getName() {
		return name;
	}
	public String getPrice() {
		return price;
	}
	public String getSchoolGate() {
		return schoolGate;
	}
	public String getCuisine() {
		return cuisine;
	}
	public String getVegan() {
		return vegan;
	}
	public String getTakeOut() {
		return takeOut;
	}
	public String getSeats() {
		return seats;
	}
	public String getOpenTime() {
		return openTime;
	}
	public String getTelefon() {
		return telefon;
	}
	public String getAddress() {
		return address;
	}
	
}