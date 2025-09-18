package lk.acpt.javaee.service;

import lk.acpt.javaee.dto.BookDto;

import java.util.ArrayList;
import java.util.List;

public interface BookService {

    List<BookDto> loadData();

    int addBook(BookDto bookDto);

    int deleteBook(int id);

    BookDto searchBook(int id);

    int updateBook(BookDto bookDto);

}
