import java.util.ArrayList;
import java.util.Set;

public abstract class Calculator {
	protected String start;
	protected String destination;
	protected int mode;
	protected Grapher grapher;
	protected ArrayList<ArrayList<Station>> routes; //add a set of Station. has many routes. choose the one which user need
	
	public Calculator(String start, String destination, int mode, Grapher grapher)
	{
		this.start = start;
		this.destination = destination;
		this.mode = mode;
		this.grapher = grapher;
		this.routes = new ArrayList<ArrayList<Station>>();
	}
	
	public abstract void calculate();
	public abstract void printResult();
    public abstract void reviseRoute(); 
	public void dfs(Station current, int dep, ArrayList<Station> route, Set<Station> visited) {
		if(current.getName().equals(destination) || !current.isHasNexts())
		{
			route.add(dep, current); //add last node
			visited.add(current);
			if(current.getName().equals(destination))
			{
				ArrayList<Station> copyRoute = new ArrayList<Station>(route); //copy a new route, not the copy reference
				routes.add(copyRoute);
			}
			return;
		}
		
		//add
		route.add(dep, current);
		
		//is visited
		if(!visited.contains(current))
		{
			visited.add(current);
		}
		
		
		for(Station next : current.getNextStationsMap().keySet())
		{
			if(!visited.contains(next))
			{
				//search
				dfs(next, dep+1, route, visited);
				
				//reset 
				route.remove(dep+1); //remove by index
				visited.remove(next); //remove by reference
			}
		}
	}
	
	public int getRoutePrice(ArrayList<Station> route)
	{
		int sum = 0;
		for(int i=0; i<route.size(); i++)
		{
			if(i < route.size()-1)
			{
				sum += route.get(i).getNextPrice(route.get(i+1));
			}
		}
		return sum;
	}
	
	public void printRoute(ArrayList<Station> route)
	{
		System.out.print("\s".repeat(5)+". ");
		for(int i=0; i<route.size(); i++)
		{
			if(i == route.size()-1)
				System.out.printf("%s", route.get(i).getName());
			else if(i <= route.size()-2) //倒數第二個之前
			{
				
				System.out.printf("%s --($%d)--> ", route.get(i).getName(), route.get(i).getNextPrice(route.get(i+1)));	
			}
				
		}
		System.out.printf(" | sum price: %d, all stops: %d\n", this.getRoutePrice(route), route.size());
	}
	
	public void printAllRoutes() {
		for(ArrayList<Station> route : routes)
		{
			this.printRoute(route);
		}
	}
}
