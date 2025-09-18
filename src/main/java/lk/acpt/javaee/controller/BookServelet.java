package lk.acpt.javaee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.acpt.javaee.dto.BookDto;
import lk.acpt.javaee.service.BookService;
import lk.acpt.javaee.service.impl.BookServiceImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/book")
public class BookServelet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // service layer call
        BookService service = new BookServiceImpl();//problem// ai super class eka ganne (compile time ekedi balimak pamanak wana nisa implemitation ekak dana eka aparadayak)
        List<BookDto> books = service.loadData(); // assuming you have this method in your service

        // convert list to JSON
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(books);

        // write response
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();//ena object eka json ekakata convert karai
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Map<String, Object> responseMap = new HashMap<>();

        try {
            BookDto bookDto = mapper.readValue(req.getInputStream(), BookDto.class);//thina json eka bookDto object ekakata convert karai

            BookService service = new BookServiceImpl();
            int add = service.addBook(bookDto);

            if (add > 0) {
                resp.setStatus(HttpServletResponse.SC_CREATED); // 201
                responseMap.put("status", "success");
                responseMap.put("message", "Book added successfully");
                responseMap.put("book", bookDto); // return the created book
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
                responseMap.put("status", "error");
                responseMap.put("message", "Failed to add book");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            responseMap.put("status", "error");
            responseMap.put("message", "Server error: " + e.getMessage());
        }

        // Convert responseMap to JSON and write it out
        String jsonResponse = mapper.writeValueAsString(responseMap);
        resp.getWriter().write(jsonResponse);
        resp.getWriter().flush();
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Map<String, Object> responseMap = new HashMap<>();

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            BookService service = new BookServiceImpl();
            int delete = service.deleteBook(id);

            if (delete > 0) {
                resp.setStatus(HttpServletResponse.SC_OK); // 200
                responseMap.put("status", "success");
                responseMap.put("message", "Book delete successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
                responseMap.put("status", "error");
                responseMap.put("message", "Failed to delete book");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            responseMap.put("status", "error");
            responseMap.put("message", "Server error: " + e.getMessage());
        }

        // Convert responseMap to JSON and write it out
        String jsonResponse = mapper.writeValueAsString(responseMap);
        resp.getWriter().write(jsonResponse);
        resp.getWriter().flush();


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Map<String, Object> responseMap = new HashMap<>();

        try {
            BookDto bookDto = mapper.readValue(req.getInputStream(), BookDto.class);

            BookService service = new BookServiceImpl();
            int update = service.updateBook(bookDto);

            if (update > 0) {
                resp.setStatus(HttpServletResponse.SC_CREATED); // 201
                responseMap.put("status", "success");
                responseMap.put("message", "Book updated successfully");
                responseMap.put("book", bookDto); // return the created book
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
                responseMap.put("status", "error");
                responseMap.put("message", "Failed to update book");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            responseMap.put("status", "error");
            responseMap.put("message", "Server error: " + e.getMessage());
        }

        // Convert responseMap to JSON and write it out
        String jsonResponse = mapper.writeValueAsString(responseMap);
        resp.getWriter().write(jsonResponse);
        resp.getWriter().flush();
    }

}


