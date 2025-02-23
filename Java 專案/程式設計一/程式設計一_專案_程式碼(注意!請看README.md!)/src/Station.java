import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.LinkedHashMap;
public class Station {
	//key-->nextStation, value-->price
	//each station is a node
	private LinkedHashMap<Station, Integer> nextStationsMap;
	private String name;
	private String relationString;
	
	public Station(String name)
	{
		this.name = name;
		this.nextStationsMap = new LinkedHashMap<Station, Integer>();
		this.relationString = "";
	}
	
	public Station(String name, String relationString)
	{
		this.name = name;
		this.nextStationsMap = new LinkedHashMap<Station, Integer>();
		this.relationString = relationString;
	}
	
	public LinkedHashMap<Station, Integer> getNextStationsMap()
	{
		return this.nextStationsMap;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getNextPrice(Station next)
	{
		return this.nextStationsMap.get(next);
	}
	
	public boolean isHasNexts()
	{
		return !nextStationsMap.isEmpty();
	}
	
	public void addNextStation(ArrayList<Station> stations)
	{
		String[] nexts = this.relationString.split(","); //B(30)
		for(int i=0; i<nexts.length; i++)
		{	
			for(Station st : stations)
			{
				if(st.getName().equals(nexts[i].split("\\(")[0]))
				{
					nextStationsMap.put(st, Integer.parseInt(nexts[i].split("\\(")[1].split("\\)")[0]));
				}
			}
			
		}
	}
	
	public void printNexts()
	{
		String result = "";
		for(Station key : nextStationsMap.keySet())
		{
			result += String.format("%s(%d), ", key.getName(), nextStationsMap.get(key));
		}
		result = result.substring(0, result.length()-2);
		System.out.print(result);
	}
	
}
