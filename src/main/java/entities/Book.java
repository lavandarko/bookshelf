package entities;

public class Book {
    private  int id;
    private String title;
    private String author;
    private int year;
    private int pagesNumber;
    private String heading;

    public Book() {
    }

    public Book(int id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Book(int id, String title, String author, int year, int pagesNumber, String heading) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.pagesNumber = pagesNumber;
        this.heading = heading;
    }

    public Book(int id, String title, String author, int year, int pagesNumber) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.pagesNumber = pagesNumber;
    }

    public Book(int id, String title, String author, int year, String heading) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.heading = heading;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPagesNumber() {
        return pagesNumber;
    }

    public void setPagesNumber(int pagesNumber) {
        this.pagesNumber = pagesNumber;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }
}
