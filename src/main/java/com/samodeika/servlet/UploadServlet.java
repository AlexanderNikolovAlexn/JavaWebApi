package com.samodeika.servlet;

import com.samodeika.constants.Constants;
import com.samodeika.dao.PersonDao;
import com.samodeika.dao.PersonDaoImpl;
import com.samodeika.entity.Person;
import com.samodeika.json.JsonProcessor;
import com.samodeika.json.JsonProcessorImpl;
import com.samodeika.xls.XLSProcessor;
import com.samodeika.xls.XLSProcessorImpl;
import com.samodeika.xml.XMLProcessor;
import com.samodeika.xml.XMLProcessorImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
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
        String fileType = req.getParameter("fileType");
        Part part = req.getPart("data");

        List<Person> persons = new ArrayList<Person>();
        List<Person> tmp = new ArrayList<>();
        switch (fileType) {
            case Constants.C_JSON:
                JsonProcessor jsonProcessor = new JsonProcessorImpl();
                tmp = jsonProcessor.processFile(part.getInputStream());
                break;
            case Constants.C_XLS:
                XLSProcessor xlsProcessor = new XLSProcessorImpl();
                tmp = xlsProcessor.processFile(part.getInputStream());
                break;
            case Constants.C_XML:
                XMLProcessor xmlProcessor = new XMLProcessorImpl();
                tmp = xmlProcessor.processFile(part.getInputStream());
                break;
        }

        persons.addAll(tmp);

        PersonDao dao = new PersonDaoImpl();

        if(dao.savePersons(persons)) {
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        }

    }

}
