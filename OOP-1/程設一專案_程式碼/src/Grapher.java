import java.util.ArrayList;

public class Grapher {
	private ArrayList<Station> stations;
	
	public Grapher()
	{
		this.stations = new ArrayList<Station>();
	}
	
	public ArrayList<Station> getStations()
	{
		return this.stations;
	}
	
	public Station getStation(String name)
	{
		Station target = null;
		for(Station station : stations)
		{
			if(station.getName().equals(name))
			{
				target = station;
				break;
			}
		}
		return target;
	}
	
	public void readGraph(String inputPattern)
	{
		//analyze string 
		/*	eg. A : B(30), C(20), D(10)\n
		 * 	    B : A(30), F(10)\n
		 */
		String[] graph = inputPattern.split("\n");
		
		for(int i=0; i<graph.length; i++)
		{
			String[] relation = graph[i].replaceAll("\\s", "").split(":");
			Station start = new Station(relation[0], relation[1]); //pass the next nodes relation
			this.stations.add(start);
		}
		
		//next nodes should pass the copy reference
		for(Station station : stations)
		{
			station.addNextStation(stations);
		}
	}
	
	public void printGraph()
	{
		for(Station start : stations)
		{
			System.out.printf("%s --> ", start.getName());
			start.printNexts();
			System.out.println("");
		}
	}
}
