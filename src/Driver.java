import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;


public class Driver {
	
	static ArrayList<ArrayList<Article>> articlesInFiles;
	static ArrayList<Article> arr;
	public static Scanner read = null;
	public static File[] allFiles = null;

	
	public static void main(String[] args) {
		
		File folder = new File("./");
		allFiles = folder.listFiles();
		articlesInFiles = new ArrayList<ArrayList<Article>>();
		
		processFilesForValidation();
		
	}
	
	public static void processFilesForValidation(){
		
																//	System.out.println(allFiles.length); // testing
		for(int i = 0; i < allFiles.length; i++) 
		{
			arr = new ArrayList<Article>();
			if(allFiles[i].getName().indexOf(".bib") >= 0)
			{
				try {
					readArticle(allFiles[i]);
					articlesInFiles.add(arr);
					arr = null;
				}
				catch(FileInvalidException e)
				{
					System.out.println(allFiles[i].getName() + " is invalid and therefore a bibliography will not be created for it");
				}
				
				
				
			}
			
		}
		
	}
	public static void readArticle(File file) throws FileInvalidException { //replace boolean by void and return statement to thrown exception
		
		 
		try {
			read = new Scanner(new FileInputStream(file));
			
			while(read.hasNextLine()) {
				
			
			String article = read.nextLine();
			
			if(!article.equals("@ARTICLE{"))
				continue;
			
			String id = read.nextLine();
			String variable = null;
			String content = null;
			boolean valid = true;
			String author= null;
			String year= null;
			String journal= null;
			String title = null;
			String volume = null;
			String number = null;
			String keywords = null;
			String doi = null;
			String ISSN = null;
			String month = null;
			String pages = null;
			id = id.substring(0,id.length() - 2);
				while(true) {
					variable = read.nextLine();
					if(variable.equals("}"))
						break;
					content = new String(variable);
					variable = variable.substring(0,variable.indexOf('='));
					content = content.substring(content.indexOf('{')+1,content.indexOf('}'));
						if(content.equals(""))
							throw new FileInvalidException();//return false;
					switch(variable) {
						case "author":
							author = content;
							break;
						case "year":
							year = content;
							break;
						case "journal":
							journal = content;
							break;
						case "title":
							title = content;
							break;
						case "volume":
							volume = content;
							break;
						case "number":
							number = content;
							break;
						case "keywords":
							keywords = content;
							break;
						case "doi":
							doi = content;
							break;
						case "ISSN":
							ISSN = content;
							break;
						case "month":
							month = content;
							break;
						case "pages":
							pages = content;
							break;
					
				}
			}
			
			
			arr.add(new Article( author,  year,  journal,  title,  volume,  number,
					 keywords,  doi,  ISSN,  month,  id,  valid,  pages));
			read.nextLine();
			}
			
			
		}
		catch(IOException f) {
			System.out.println(f.getMessage());
		}finally {
			
			if(read != null)
				read.close();
			
			
		}
		
		//return true;
		
	}
}
