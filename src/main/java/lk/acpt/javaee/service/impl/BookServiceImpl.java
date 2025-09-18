package lk.acpt.javaee.service.impl;

//import javafx.collections.FXCollections;
import lk.acpt.javaee.db.DBConnection;
import lk.acpt.javaee.dto.BookDto;
import lk.acpt.javaee.service.BookService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookServiceImpl implements BookService {

    public int addBook(BookDto bookDto) {

        try {
            Connection connection = DBConnection.getDbConnection().getConnection();//return type eka Connection ekak

            //query creation
            PreparedStatement preparedStatement = connection.prepareStatement("insert into books_detals(name , author , year) values(?,?,?)");

            preparedStatement.setObject(1, bookDto.getName());
            preparedStatement.setObject(2, bookDto.getAuthor());
            preparedStatement.setObject(3, bookDto.getYear());

            //execute/run query
            int i = preparedStatement.executeUpdate();//return type eka int nisa int ekak controller eket return karanawa

            System.out.println("res "+i);

            return i;

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public int deleteBook(int id) {

        try {
            Connection connection = DBConnection.getDbConnection().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("delete from books_detals where id = ?");

            preparedStatement.setObject(1, id );

            int i = preparedStatement.executeUpdate();

            return i;

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public BookDto searchBook(int id) {

        try {

            Connection connection = DBConnection.getDbConnection().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("select * from books_detals where id = ?");

            preparedStatement.setObject(1, id);

            ResultSet search = preparedStatement.executeQuery();

            if (search.next()) {
                BookDto bookDto = new BookDto(search.getString("name"), search.getString("author"), search.getInt("year"));
                return bookDto;//controller eke thina serch variable eke return type eka BookDto ekek nisa meka return karannath ona bookDto ekak.
            }else {
                return null;
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateBook(BookDto bookDto) {

        try {

            Connection connection = DBConnection.getDbConnection().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("update books_detals set name = ? , author = ? , year = ? where id = ?");

            preparedStatement.setString(1, bookDto.getName());
            preparedStatement.setString(2, bookDto.getAuthor());
            preparedStatement.setString(3, String.valueOf(bookDto.getYear()));
            preparedStatement.setString(4, String.valueOf(bookDto.getId()));

            int i = preparedStatement.executeUpdate();

            if (i > 0) {
                System.out.println("BookDto update successfully");
                return i;
            }else {
                System.out.println("BookDto not update");
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public List<BookDto> loadData() {

        List<BookDto> bookDtos = new ArrayList<>();

        try {

            Connection connection = DBConnection.getDbConnection().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("select * from books_detals");

            ResultSet getAll = preparedStatement.executeQuery();

            while (getAll.next()) {
                bookDtos.add(new BookDto(
                        getAll.getInt(1),
                        getAll.getString(2),
                        getAll.getString(3),
                        getAll.getInt(4)
                ));
            }
            return bookDtos;

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


