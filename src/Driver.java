import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileOutputStream;


public class Driver {
	
	static ArrayList<ArrayList<Article>> articlesInFiles;
	static ArrayList<Article> arr;
	public static ArrayList<Scanner> read = null;
	public static ArrayList<PrintWriter> WriteIeee = null;
	public static ArrayList<PrintWriter> WriteAcm = null;
	public static ArrayList<PrintWriter> WriteNj = null;
	
	public static ArrayList<File> allbibFiles = null;

	
	public static void main(String[] args) {
		read = new ArrayList<Scanner>();
		WriteIeee = new ArrayList<PrintWriter>();
		WriteAcm = new ArrayList<PrintWriter>();
		WriteNj = new ArrayList<PrintWriter>();
		
		
		
		File folder = new File("./");
		File[] allFiles = folder.listFiles();
		articlesInFiles = new ArrayList<ArrayList<Article>>();
		
		allbibFiles = new ArrayList<File>();
		for(int i = 0; i < allFiles.length; i++) {
			if(allFiles[i].getName().contains(".bib"))
				allbibFiles.add(allFiles[i]);
		}
		
		System.out.println(allbibFiles.get(0));
		
		char number = 'x';// to keep compiler happy
		String error = "";
		
		for(int i = 0; i < allbibFiles.size(); i++) 
		{
			//arr = new ArrayList<Article>();
				Scanner temp = null;
				try {
					temp = new Scanner(new FileInputStream(allbibFiles.get(i)));
					read.add(temp);					
												//readArticle(allFiles[i]);
												//articlesInFiles.add(arr);
												//arr = null;
				}

				catch(IOException e)
				{
					System.out.println(allFiles[i].getName() + " could not be opened all files will be closed before the program terminates");
					closeScannerInArraylist();
					closePrintWriter();
					System.exit(0);
				}
		}
		for(int i = 0; i < allbibFiles.size(); i++)
			try {
				number = allbibFiles.get(i).getName().charAt(allbibFiles.get(i).getName().indexOf(".") - 1);			
				error = "IEEE";
				WriteIeee.add(new PrintWriter(new FileOutputStream("IEEE" + number + ".json",true)));
				error = "ACM";
				WriteAcm.add(new PrintWriter(new FileOutputStream("ACM" + number + ".json",true)));
				error = "NJ";
				WriteNj.add(new PrintWriter(new FileOutputStream("NJ" + number + ".json",true)));
				
			}
			catch(IOException g)
			{
				System.out.println("There was a problem openning " + error + number + ".json");
				System.out.println("All json files will be deleted before the program gets terminated");
				File[] FilesWithJson = folder.listFiles();
				for(int j = 0; j > FilesWithJson.length; j++) {
					if(FilesWithJson[j].getName().contains(".json"))
						FilesWithJson[j].delete();
				}
				closeScannerInArraylist();
				closePrintWriter();
				System.exit(0);
				
			}

		processFilesForValidation();
		
		
		
	}
	
	public static void closeScannerInArraylist() {
		for(int j = 0; j < read.size(); j++)
			read.get(j).close();
		
	}
	public static void closePrintWriter() {
		for(int j = 0; j < WriteIeee.size(); j++)
			WriteIeee.get(j).close();
		for(int j = 0; j < WriteAcm.size(); j++)
			WriteAcm.get(j).close();
		for(int j = 0; j < WriteNj.size(); j++)
			WriteNj.get(j).close();
		
		
	}
	
	public static void processFilesForValidation(){
		
		System.out.println(read.size());
		for(int i = 0;i < read.size();i++) {//creating ArrayList containing all articles in all files
			arr = new ArrayList<Article>();
			readArticle(i);
			
			articlesInFiles.add(arr);
			
			arr = null;
		}
		closeScannerInArraylist();
		
		write(WriteIeee, "IEEE");
		write(WriteAcm, "ACM");
		write(WriteNj, "NJ");
		
		
		
		closePrintWriter();
																//	System.out.println(allFiles.length); // testing
	}
	public static void write( ArrayList<PrintWriter> write, String type) {
		boolean valid = true;
		for(int i = 0; i < write.size();i++) {
			for(int j = 0; j < articlesInFiles.get(i).size(); j++) {
				
				valid = articlesInFiles.get(i).get(j).isValid();
					if(type.equals("IEEE") && valid)
						write.get(i).write(articlesInFiles.get(i).get(j).IeeeString());
					if(type.equals("ACM") && valid)
						write.get(i).write(articlesInFiles.get(i).get(j).AcmString(j));
					if(type.equals("NJ") && valid)
						write.get(i).write(articlesInFiles.get(i).get(j).NjString());
			}
		}
	}
	public static void readArticle(int i) { //replace boolean by void and return statement to thrown exception
		
		 
		try {
			//read = new Scanner(new FileInputStream(file));
			
			while(read.get(i).hasNextLine()) {
				
				
			
			String article = read.get(i).nextLine();
			
			if(!article.equals("@ARTICLE{"))
				continue;
			
			String id = read.get(i).nextLine();
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
					variable = read.get(i).nextLine();
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
			//read.get(i).nextLine();
			}
			
			
		}
		catch(FileInvalidException f) {
			arr.get(i).setValid(false);
			System.out.println(f.getMessage() );
		}		
	}
}
