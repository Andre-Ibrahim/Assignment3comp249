import java.util.StringTokenizer;

public class Article {
	
	private String author;
	private String year;
	private String journal;
	private String title;
	private String volume;
	private String number;
	private String keywords;
	private String doi;
	private String ISSN;
	private String month;
	private String id;
	private boolean valid = true;
	private String pages;
	
	public Article(String author, String year, String journal, String title, String volume, String number,
			String keywords, String doi, String iSSN, String month, String id, boolean valid, String pages) 
	{
		super();
		this.author = author;
		this.year = year;
		this.journal = journal;
		this.title = title;
		this.volume = volume;
		this.number = number;
		this.keywords = keywords;
		this.doi = doi;
		ISSN = iSSN;
		this.month = month;
		this.id = id;
		this.valid = valid;
		this.pages = pages;
		
	}
	

	public boolean isValid() {
		return valid;
	}


	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public String IeeeString() {
		return author.replace(" and", ",") + "." +"\"" + title + "\", " + journal + " vol. " + volume + ", no. " + number
				+ ", p. " + pages + ", " + month + " " + year + ".\n\n";
	}
	
	public String AcmString(int count) {
		StringTokenizer auth = new StringTokenizer(this.author);
		return "[" + count + "]" + "\t" + auth.nextToken() + " " + auth.nextToken() + "et al. " + year 
				+ ". " + title + ". " + journal + ". " + volume + ", " + "(" + year + "), " + pages
				+ ". DOI:https://doi.org/" + doi + "\n\n";
	}
	
	public String NjString() {
		return author.replace("and", "&") + ". " + title + ". " + journal + ". " + volume + ", " + pages + "(" + year + ").\n\n";
	}


	@Override
	public String toString() {
		return "Article [author=" + author + ", year=" + year + ", journal=" + journal + ", title=" + title
				+ ", volume=" + volume + ", number=" + number + ", keywords=" + keywords + ", doi=" + doi + ", ISSN="
				+ ISSN + ", month=" + month + ", id=" + id + ", valid=" + valid + ", pages=" + pages + "]";
	}
	


}
