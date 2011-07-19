package elimu_maktabaDatabaseConnection;

public class RecordsAllBooks {
	String barcodNum,title,version,author,publisher,pages,genre;
	public RecordsAllBooks(String barcodNum,String title,String version,String author, String publisher,String pages,String genre){
		this.barcodNum = barcodNum;
		this.title = title;
		this.version = version;
		this.author  = author;
		this.publisher = publisher;
		this.pages = pages;
		this.genre = genre;
		
	}

	public String getBarcodNum() {
		return barcodNum;
	}

	public void setBarcodNum(String barcodNum) {
		this.barcodNum = barcodNum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
}
