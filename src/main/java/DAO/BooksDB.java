package DAO;

import entities.Book;

import java.sql.*;
import java.util.ArrayList;

public class BooksDB {

    public static ArrayList<Book> getBooksFromDB() {

        DBConnection dbConnection = new DBConnection();
        String sql = "SELECT * FROM \"Books\"";
        ArrayList<Book> books = new ArrayList<>();

        try {
            Statement statement = dbConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt(6);
                String title = resultSet.getString(1);
                String author = resultSet.getString(2);
                int year = resultSet.getInt(3);
                int pagesNumber = resultSet.getInt(4);
                String heading = resultSet.getString(5);
                Book book = new Book(id, title.trim(), author.trim(), year);
                if (pagesNumber > 0) book.setPagesNumber(pagesNumber);
                if (heading != null) book.setHeading(heading.trim());
                books.add(book);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return books;
    }

    public static void addBookToDB(Book book) throws Exception{
        DBConnection dbConnection = new DBConnection();
        String columns = "\"id\", \"title\", \"author\", \"year\"";
        String  values = "?, ?, ?, ?";
        int c = 5;
        if (book.getPagesNumber() > 0) {
            columns += ", \"pages_number\"";
            values += ", ?";
            c++;
        }
        if (book.getHeading() != null) {
            columns += ", \"heading\"";
            values += ", ?";
        }
        String sql = "INSERT INTO \"Books\" (" + columns + ")" +
                " VALUES (" + values + ")";
        PreparedStatement stmt = dbConnection.getConnection().prepareStatement(sql);
        stmt.setObject(1, book.getId());
        stmt.setObject(2, book.getTitle());
        stmt.setObject(3, book.getAuthor());
        stmt.setObject(4, book.getYear());
        if (book.getPagesNumber() > 0) stmt.setObject(5, book.getPagesNumber());
        if (book.getHeading() != null) stmt.setObject(c, book.getHeading());
        stmt.executeUpdate();
    }

    public static void updateBook(Book book) throws Exception {
        DBConnection dbConnection = new DBConnection();
        String sql1 = "UPDATE \"Books\" SET \"title\" = ?, \"author\" = ?, \"year\" = ?";
        String sql2 = "WHERE \"id\" = '" + book.getId() + "'";
        int c = 4;
        if (book.getPagesNumber() > 0) {
            sql1 += ", \"pages_number\" = ?";
            c++;
        }
        if (book.getHeading() != null) {
            sql1 += ", \"heading\" = ?";
        }
        PreparedStatement stmt = dbConnection.getConnection().prepareStatement(sql1 + sql2);
        stmt.setObject(1, book.getTitle());
        stmt.setObject(2, book.getAuthor());
        stmt.setObject(3, book.getYear());
        if (book.getPagesNumber() > 0) stmt.setObject(4, book.getPagesNumber());
        if (book.getHeading() != null) stmt.setObject(c, book.getHeading());
        stmt.executeUpdate();
    }

    public static void deleteBook(String id) {
        DBConnection dbConnection = new DBConnection();
        String sql = "DELETE FROM \"Books\" WHERE \"id\" = '" +id + "'";
        try {
            PreparedStatement stmt = dbConnection.getConnection().prepareStatement(sql);
            stmt.executeUpdate();
        } catch (Exception ex) {

        }
    }
}
