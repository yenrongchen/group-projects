import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

public class FileRecorder {
	private Scanner sc;
	private File file;
	
	//default Constructor

	public String requestFileLoadIn(Scanner sc)
	{
		String fileName = "";
		System.out.print("Do you want to load present file of metro graph? (y/N): ");
		String ans = sc.next();
		
		if(ans.equals("y")) {
			System.out.println("The following are the present file.");
			this.printAllFiles();
			System.out.print("Please choose one file and enter the full name: ");
			fileName = sc.next();	
		}
		
		return fileName;
	}
	
	public void requestSaveGraph(Scanner sc, String pattern) throws IOException
	{
		System.out.print("Do you want to save the graph? (y/N): ");
		String ans = sc.next();
		if(ans.equals("y"))
		{
			System.out.print("Please input the file name: ");
			String name = sc.next();
			Writer writer = null;
			try {
				File file = new File("./recordedGraph/"+name);
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8")); 
				writer.write(pattern);
			}catch(IOException e) {
				throw new IOException(e.getMessage());
			}finally {
				writer.close();
			}
		}
	}
	
	public void printAllFiles()
	{
		File folder = new File("./recordedGraph");
		for(File file : folder.listFiles())
		{
			System.out.printf("%s ", file.getName());
		}
		System.out.println("");
	}
	
	public int getStationNum(String fileName) throws FileNotFoundException
	{
		int count = 0;
		try {
			File file = new File("./recordedGraph/"+fileName);
			sc = new Scanner(file, "UTF-8");
			
			while(sc.hasNextLine())
			{
				count++;
				sc.nextLine();
			}
		}catch(FileNotFoundException e) {
			throw new FileNotFoundException(e.getMessage());
		}finally
		{
			//sc.close();
		}
		return count;
	}
	
	public String readFile(String fileName) throws FileNotFoundException 
	{
		try
		{
			file = new File("./recordedGraph/"+fileName);
			sc = new Scanner(file, "UTF-8");
			
			String result = "";
			while(sc.hasNextLine())
			{
				result += sc.nextLine()+"\n";
			}
			
			return result;
		}catch(FileNotFoundException e) {
			throw new FileNotFoundException(e.getMessage());
		}finally
		{
			sc.close();
		}
	}
}
