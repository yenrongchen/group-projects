package entities;

public class Restaurant {
    private String restID;
    private String name;
    private String address;
    private String phone;
    private String businessHour;
    private String closed;
    private String vegan;
    private String budget;
    private String diningTime;
    private String type;
    private String distance;
     
    private double score;

    public Restaurant(String id, String name, String address, String phone, String businessHour, String closed, String vegan) {
    	restID = id;
    	this.name = name;
    	this.address = address;
    	this.phone = phone;
    	this.businessHour = businessHour;
    	this.closed = closed;
    	this.vegan = vegan;
    }
    
    public Restaurant(String id, String budget, String time, String type, String distance) {
    	restID = id;
    	this.budget = budget;
    	this.diningTime = time;
    	this.type = type;
    	this.distance = distance;
    }
    
    public String getRestID() {
        return restID;
    }

    public String getName() {
        return name;
    }
    
    public String getAddress() {
    	return address;
    }
    public String getPhone() {
    	return phone;
    }
    
    public String getBusinessHour() {
    	return businessHour;
    }
    
    public String getClosed() {
    	return closed;
    }
    
    public String getVegan() {
    	return vegan;
    }
    
    public String getBudget() {
    	return budget;
    }
    
    public String getDiningTime() {
    	return diningTime;
    }
    
    public String getType() {
    	return type;
    }
    
    public String getDistance() {
    	return distance;
    }
    
    public double getScore() {
    	return score;
    }
    
    public void calScore() {
    	score = 0;
    	
    	switch(budget) {
    		case "100元以下" :
    			score += 1;
    			break;
    			
    		case "100-150" :
    			score += 0.8;
    			break;
    			
    		case "150-200" :
    			score += 0.6;
    			break;
    			
    		case "200-250" :
    			score += 0.4;
    			break;
    			
    		case "250以上" :
    			score += 0.2;
    			break;
    	}
    	
    	switch(diningTime) {
    		case "30分以下":
    			score += 0.6;
    			break;
    			
    		case "30分-1小時":
    			score += 0.4;
    			break;
    			
    		case "1小時以上":
    			score += 0.2;
    			break;
    	}
    	
    	switch(distance) {
			case "麥側":
				score += 0.2;
				break;
			
			case "正門":
				score += 0.4;
				break;
			
			case "東側門":
				score += 0.2;
				break;
    	}
    }
}
