import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SavingTime extends Calculator{

	int minRouteStops;
	ArrayList<ArrayList<Station>> leastStopsRoutes;
	
	public SavingTime(String start, String destination, int mode, Grapher grapher) {
		super(start, destination, mode, grapher);
		this.minRouteStops = 0;
		this.leastStopsRoutes = new ArrayList<ArrayList<Station>>();
	}

	@Override
	public void calculate() {
		ArrayList<Station> route = new ArrayList<Station>();
		Set<Station> visited = new HashSet<Station>(); //a non-duplicated element set
		dfs(super.grapher.getStation(super.start), 0, route, visited);
		
		boolean isFirst = true;
		ArrayList<Station> tmpRoute = null;
		for(ArrayList<Station> r : super.routes)
		{
			int stops = r.size();
			this.minRouteStops = isFirst ? stops : this.minRouteStops;
			
			if(stops < this.minRouteStops)
			{
				this.minRouteStops = stops;
				tmpRoute = r;
			}else if(stops == this.minRouteStops)
			{
				//計算相同金額 不同路線
				if(tmpRoute != null)
				{
					//filter the first
					this.leastStopsRoutes.add(tmpRoute);
				}
				tmpRoute = r;
			}
			isFirst = false;
		}
		this.leastStopsRoutes.add(tmpRoute);
		
		reviseRoute();
	}

	@Override
	public void printResult() {
		System.out.printf("Among these routes\nThe most saving time ways are: \n");
		for(ArrayList<Station> route : this.leastStopsRoutes)
		{
			this.printRoute(route);
			System.out.print("\s".repeat(7));
			System.out.printf("Cost $%d, Stops %d\n\n", this.getRoutePrice(route), route.size());
		}
	}

	@Override
	public void reviseRoute() {
		for(ArrayList<Station> route : this.leastStopsRoutes)
		{
			if(route.size() != this.minRouteStops)
			{
				this.leastStopsRoutes.remove(route);
			}
		}	
	}
}
