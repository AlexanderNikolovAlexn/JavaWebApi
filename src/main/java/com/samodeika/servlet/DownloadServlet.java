package com.samodeika.servlet;

import com.samodeika.dao.PersonDao;
import com.samodeika.dao.PersonDaoImpl;
import com.samodeika.json.JsonProccessorImpl;
import com.samodeika.utils.FileUtils;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(name = "DownloadServlet", urlPatterns = {"/DownloadServlet"})
public class DownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Request method: " + req.getMethod());
        getServletContext().getRequestDispatcher("/WEB-INF/pages/downloadPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DownloadServlet service!");
        String fileType = req.getParameter("fileType");
        System.out.println("Download file type is: " + fileType );
        PersonDao dao  = new PersonDaoImpl();
        JSONObject json = JsonProccessorImpl.getJson(dao.getPersons());
        resp.setContentType("application/txt");
        resp.setContentLength(json.toString().length());

        File file = FileUtils.writeToFile("json.txt", json.toString());
        FileInputStream inStream = new FileInputStream(file);

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
        resp.setHeader(headerKey, headerValue);

        // obtains response's output stream
        OutputStream outStream = resp.getOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inStream.close();
        outStream.close();
    }
}
