import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
public class Tester {
	public static void main(String[] args) throws IOException {
		/*
		 	example:
			 	台北車站: 西門(20), 忠孝新生(20), 中正紀念堂(30)
				西門: 台北車站(20), 中正紀念堂(10)
				忠孝新生: 台北車站(20), 東門(10)
				東門: 忠孝新生(10), 中正紀念堂(10)
				中正紀念堂: 西門(10), 台北車站(30), 東門(10)
		 */ 
		
		//initialize
		Scanner sc = new Scanner(System.in);
		int stationNum = 0;
		String pattern = "";
		Grapher grapher = new Grapher();
		String startStation = "", destinationStation = "";
		int mode;
		Calculator calculator;
		FileRecorder recorder = new FileRecorder();
		
		System.out.println("-".repeat(40));
		String welcome = "Welcome to Metro Calculator ! ! !"; //32格
		System.out.printf("%36s\n", welcome);
		System.out.println("-".repeat(40)+"\n");
		
		String fileName = recorder.requestFileLoadIn(sc);
		
		if(!fileName.equals(""))
		{
			stationNum = recorder.getStationNum(fileName);
			pattern = recorder.readFile(fileName);
		}else
		{
			System.out.print("How many stations do you want to type in: ");
			stationNum = sc.nextInt();
			System.out.println("Please input the metro graph (eg. A: B(20), C(10)) : ");
			sc.nextLine(); //skip current line;
			//All nodes should be inputed
			for(int i=0; i<stationNum; i++)
			{
				pattern += sc.nextLine()+"\n";
			}
		}
		
		grapher.readGraph(pattern);
		
		System.out.println("\n"+"-".repeat(40));
		System.out.println("\nThe below is your metro graph: ");
		grapher.printGraph();
		System.out.println("");
		
		System.out.print("Which station do you want to start: ");
		startStation = sc.next();
		
		System.out.print("Which station is your destination: ");
		destinationStation = sc.next();
		
		System.out.print("Which mode do you want to choose? (1)most saving money (2)most saving time(least stations): ");
		mode = sc.nextInt();
		System.out.println("\n"+"-".repeat(40)+"\n");
		
		calculator = Factory.createCaculator(startStation, destinationStation, mode, grapher);
		calculator.calculate();
		System.out.println("All routes: ");
		calculator.printAllRoutes();
		calculator.printResult();
		System.out.println("-".repeat(40)+"\n");
		
		recorder.requestSaveGraph(sc, pattern);
		System.out.println("Thanks for using metro calculator ! ");
	}

}
