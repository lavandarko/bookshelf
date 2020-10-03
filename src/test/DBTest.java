import DAO.BooksDB;
import entities.Book;

import java.util.ArrayList;

public class DBTest {
    public static void main(String[] args) throws Exception{
        ArrayList<Book> books = new ArrayList<>();
        books = BooksDB.getBooksFromDB();
        for (Book b: books) {
            System.out.println(
                    b.getId() + " " + b.getTitle() + " " + b.getAuthor() + " " + b.getYear() + " " + b.getPagesNumber() + b.getPagesNumber()
            );
        }
        BooksDB.deleteBook("2");
        //Book book = new Book(23,"test", "test", 123);
        //BooksDB.addBookToDB(book);
    }
}
