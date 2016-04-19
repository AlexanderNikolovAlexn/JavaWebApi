package com.samodeika.servlet;

import com.samodeika.dao.PersonDao;
import com.samodeika.dao.PersonDaoImpl;
import com.samodeika.entity.Person;
import com.samodeika.json.JsonProcessorImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@WebServlet(name = "UploadServlet", urlPatterns = {"/UploadServlet", "/"})
@MultipartConfig
public class UploadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Request method: " + req.getMethod());
        getServletContext().getRequestDispatcher("/WEB-INF/pages/uploadPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Request method: " + req.getMethod());
        Collection<Part> parts = req.getParts();

        List<Person> persons = new ArrayList<Person>();
        for(Part part : parts) {
            List<Person> tmp = JsonProcessorImpl.proccessFile(part.getInputStream());
            persons.addAll(tmp);
        }

        PersonDao dao = new PersonDaoImpl();

        if(dao.savePersons(persons)) {
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        }

    }

}
