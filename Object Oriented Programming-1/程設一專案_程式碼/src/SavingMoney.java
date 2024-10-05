import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SavingMoney extends Calculator{

	private int minRoutePrice;
	private ArrayList<ArrayList<Station>> cheapestRoutes;
	
	public SavingMoney(String start, String destination, int mode, Grapher grapher) {
		super(start, destination, mode, grapher);
		this.minRoutePrice = 0;
		this.cheapestRoutes = new ArrayList<ArrayList<Station>>();
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
			int price = this.getRoutePrice(r);
			this.minRoutePrice = isFirst ? price : this.minRoutePrice;
			
			if(price < this.minRoutePrice)
			{
				this.minRoutePrice = price;
				tmpRoute = r;
			}else if(price == this.minRoutePrice)
			{
				//有問題
				//計算相同金額 不同路線
				if(tmpRoute != null)
				{
					//filter the first
					this.cheapestRoutes.add(tmpRoute);
				}
				tmpRoute = r;
			}
			isFirst = false;
		}
		this.cheapestRoutes.add(tmpRoute);
		
		reviseRoute();
	}

	@Override
	public void printResult() {
		System.out.printf("Among these routes\nThe most saving money ways are: \n");
		for(ArrayList<Station> route : this.cheapestRoutes)
		{
			this.printRoute(route);
			System.out.print("\s".repeat(7));
			System.out.printf("Cost $%d, Stops %d\n\n", this.getRoutePrice(route), route.size());
		}
	}

	@Override
	public void reviseRoute() {
		for(ArrayList<Station> route : this.cheapestRoutes)
		{
			if(this.getRoutePrice(route) != this.minRoutePrice)
			{
				this.cheapestRoutes.remove(route);
			}
		}	
	}	
}
