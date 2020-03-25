import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileReader;


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
		
		String name = "";// to keep compiler happy
		String error = "";
		
		for(int i = 0; i < allbibFiles.size(); i++) 
		{
				Scanner temp = null;
				try {
					temp = new Scanner(new FileInputStream(allbibFiles.get(i)));
					read.add(temp);					
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
				name = allbibFiles.get(i).getName().substring(allbibFiles.get(i).getName().indexOf("x") + 1 ,allbibFiles.get(i).getName().indexOf("."));			
				
				error = "IEEE";
				WriteIeee.add(new PrintWriter(new FileOutputStream("IEEE" + name + ".json")));
				error = "ACM";
				WriteAcm.add(new PrintWriter(new FileOutputStream("ACM" + name + ".json")));
				error = "NJ";
				WriteNj.add(new PrintWriter(new FileOutputStream("NJ" + name + ".json")));
				
			}
			catch(IOException g)
			{
				System.out.println("There was a problem openning " + error + name + ".json");
				System.out.println("All json files will be deleted before the program gets terminated");
				File[] FilesWithJson = folder.listFiles();
				
				closeScannerInArraylist();
				closePrintWriter();
				
				for(int j = 0; j > FilesWithJson.length; j++) {
					if(FilesWithJson[j].getName().contains(".json"))
						FilesWithJson[j].delete();
				}
				
				System.exit(0);
				
			}

		processFilesForValidation();
		
		File folderjson = new File("./"); 
		File[] FilesWithJson = folderjson.listFiles();
		
		
		Scanner keyboard = new Scanner(System.in);
		String input = "";
		BufferedReader inputStream = null;
		boolean fileFound = false;
		String line = "";
		
		for(int i = 0; i < 2 && !fileFound; i++) {
			if(i == 1)
				System.out.println("The File is invalid you have one more chance to read a File");
			
			
			System.out.print("please Enter a File name to read: ");
			input = keyboard.nextLine();
			
			if(!input.contains(".json"))
				continue;
			
			for(int j=0; j < FilesWithJson.length;j++) {
				if(FilesWithJson[j].getName().equals(input))
					fileFound = true;
			}
			if(fileFound) {
				try {
					inputStream = new BufferedReader(new FileReader(input));
				
				
					try {
						System.out.println("Here are the content of the successfully created Jason File: " + input);
						
						while(line != null) {
							line = inputStream.readLine();
							if(line != null)
								System.out.println(line);
						}

					}
					catch(EOFException z) {
						if(inputStream != null)
						inputStream.close();
						break;
					}
					catch(IOException t) {
						System.out.println("c");
						break;
					}
				}
				catch(IOException e) {
				continue;
				}
				
					
				
			}
			
			
		}
		keyboard.close();	
		System.out.println("Goodbye! Hope you have enjoyed creating the needed files using Bibliography");
		
		
		
		
		
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
		int errorCount = 0;
		for(int i = 0;i < read.size();i++) {//creating ArrayList containing all articles in all files
			arr = new ArrayList<Article>();
			
			try {
				readArticle(i);
			}
			catch(FileInvalidException e) {
				//File folder = new File("./");
				//File[] allFiles = folder.listFiles();
				errorCount++;
				WriteIeee.get(i).close();
				WriteAcm.get(i).close();
				WriteNj.get(i).close();
				System.out.println("Error: Dectected Empty Filed!");
				System.out.println("=============================\n");

				System.out.println( "Problem dected with input file:" + "\t" +  allbibFiles.get(i).getName() + "\n");
				System.out.println(e.getMessage());
				System.out.println();
				
				String num = allbibFiles.get(i).getName().substring( allbibFiles.get(i).getName().indexOf("x") + 1, allbibFiles.get(i).getName().indexOf("."));
				File Ieee = new File("IEEE" + num + ".json");
				File Acm = new File("ACM" + num + ".json");
				File Nj = new File("NJ" + num + ".json");
				
				Ieee.delete();
				Acm.delete();
				Nj.delete();
			}
			
			
			articlesInFiles.add(arr);
			
			arr = null;
		}
		
		System.out.println("A total of " + errorCount + " files where invalid, and could not be processed. All other " + (read.size() - errorCount) + " \"Valid\" files have been created.");
		
		
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
					if(type.equals("IEEE"))
						write.get(i).write(articlesInFiles.get(i).get(j).IeeeString());
					if(type.equals("ACM"))
						write.get(i).write(articlesInFiles.get(i).get(j).AcmString(j + 1));
					if(type.equals("NJ"))
						write.get(i).write(articlesInFiles.get(i).get(j).NjString());
			}
		}
	}
	public static void readArticle(int i) throws FileInvalidException { //replace boolean by void and return statement to thrown exception
		
		 
		
			//read = new Scanner(new FileInputStream(file));
			
			while(read.get(i).hasNextLine()) {
				
				
			
			String article = read.get(i).nextLine();
			
			if(!article.contains("@ARTICLE{"))
				continue;
			
								//added
			String id = "";
			id = read.get(i).nextLine();
			
			while(id.length() == 0)
				id = read.get(i).nextLine();
			
			
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
					if(!variable.contains("="))
						continue;
					
					content = new String(variable);
					variable = variable.substring(0,variable.indexOf('='));
					content = content.substring(content.indexOf('{')+1,content.indexOf('}'));
						if(content.equals("")) {						
							throw new FileInvalidException("File is Invalid: Field " + variable + "is Empty." + "Processing stoped at this point. Other empty fieleds ma be present as well!");
						}
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
			}
			

	}
}
