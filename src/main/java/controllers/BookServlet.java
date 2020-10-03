package controllers;

import DAO.BooksDB;
import entities.Book;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class BookServlet extends HttpServlet {

    public void init() {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();
        String[] actionArr = action.split("/");
        if (actionArr.length == 1 && actionArr[0].equals("books")) {
            getAllBooks(resp);
        } else if (actionArr.length == 2 && actionArr[0].equals("books")){
            String id = actionArr[1];
            Book book = checkBookID(id);
            if (book == null){
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                String bookJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(book);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                PrintWriter out = resp.getWriter();
                out.print(bookJson);
                out.flush();
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Book book = mapper.readValue(req.getReader(), Book.class);
            BooksDB.addBookToDB(book);
        } catch (JsonGenerationException|JsonMappingException|SQLException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();
        String[] actionArr = action.split("/");
        if (actionArr.length == 2 && actionArr[0].equals("books")){
            String id = actionArr[1];
            Book book = checkBookID(id);
            if (book != null) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    Book updatedBook = mapper.readValue(req.getReader(), Book.class);
                    updateBook(book, updatedBook);
                    BooksDB.updateBook(book);
                } catch (JsonGenerationException|JsonMappingException|SQLException e) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();
        String[] actionArr = action.split("/");
        if (actionArr.length == 2 && actionArr[0].equals("books")){
            String id = actionArr[1];
            Book book = checkBookID(id);
            if (book != null) {
                try {
                    BooksDB.deleteBook(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void destroy() {
    }

    private void getAllBooks(HttpServletResponse resp) throws ServletException, IOException  {
        List<Book> books = BooksDB.getBooksFromDB();
        ObjectMapper objectMapper = new ObjectMapper();
        String booksJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(books);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(booksJson);
        out.flush();
    }

    private Book checkBookID(String id) {
        List<Book> books = BooksDB.getBooksFromDB();
        for (Book b: books) {
            if (id.equals("" + b.getId())) {
                return b;
            }
        }
        return null;
    }

    private void updateBook (Book book, Book updatedBook) {
        if (updatedBook.getTitle() != null) {
            book.setTitle(updatedBook.getTitle());
        }
        if (updatedBook.getAuthor() != null) {
            book.setAuthor(updatedBook.getAuthor());
        }
        if (updatedBook.getYear() > 0) {
            book.setYear(updatedBook.getYear());
        }
        if (updatedBook.getPagesNumber() > 0) {
            book.setPagesNumber(updatedBook.getPagesNumber());
        } else {
            book.setPagesNumber(0);
        }
        if (updatedBook.getHeading() != null) {
            book.setHeading(updatedBook.getHeading());
        } else {
            book.setHeading(null);
        }
    }
}
