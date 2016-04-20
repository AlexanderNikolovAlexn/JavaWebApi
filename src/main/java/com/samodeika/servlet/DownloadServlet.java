package com.samodeika.servlet;

import com.samodeika.constants.Constants;
import com.samodeika.dao.PersonDao;
import com.samodeika.dao.PersonDaoImpl;
import com.samodeika.entity.Person;
import com.samodeika.json.JsonProcessorImpl;
import com.samodeika.utils.FileUtils;
import com.samodeika.xls.XLSProcessor;
import com.samodeika.xls.XLSProcessorImpl;
import com.samodeika.xml.XMLProcessor;
import com.samodeika.xml.XMLProcessorImpl;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import java.util.List;

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
        List<Person> persons = dao.getPersons();
        InternalResolver internalResolver = new InternalResolver();
        File file = new File("default");
        OutputStream outStream = resp.getOutputStream();
        String headerKey;
        String headerValue;
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        FileInputStream inStream;

        switch (fileType) {
            case Constants.C_JSON:
                internalResolver = downloadJson(persons);
                file = FileUtils.writeToFile(internalResolver.getFileName(), internalResolver.getContent());
                inStream = new FileInputStream(file);

                headerKey = "Content-Disposition";
                headerValue = String.format("attachment; filename=\"%s\"", file.getName());

                resp.setHeader(headerKey, headerValue);
                resp.setContentType(internalResolver.getContentType());
                resp.setContentLength((int) internalResolver.getContentLenght());

                // obtains response's output stream
                while ((bytesRead = inStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }

                inStream.close();
                break;
            case Constants.C_XLS:
                internalResolver = downloadXls(persons);
                XLSProcessor xlsProcessor = new XLSProcessorImpl();
                XSSFWorkbook workbook = xlsProcessor.getXls(persons);
                file = FileUtils.writeToFile(internalResolver.getFileName(), internalResolver.getContent());
                headerKey = "Content-Disposition";
                headerValue = String.format("attachment; filename=\"%s\"", file.getName());

                resp.setHeader(headerKey, headerValue);
                resp.setContentType(internalResolver.getContentType());
                workbook.write(outStream);
                break;
            case Constants.C_XML:
                internalResolver = downloadXml(persons);
                file = FileUtils.writeToFile(internalResolver.getFileName(), internalResolver.getContent());
                inStream = new FileInputStream(file);
                headerKey = "Content-Disposition";
                headerValue = String.format("attachment; filename=\"%s\"", file.getName());

                resp.setHeader(headerKey, headerValue);
                resp.setContentType(internalResolver.getContentType());
                resp.setContentLength((int) internalResolver.getContentLenght());

                // obtains response's output stream
                while ((bytesRead = inStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }

                inStream.close();
                break;
        }

        outStream.flush();
        outStream.close();
    }

    private InternalResolver downloadJson(List<Person> persons){
        InternalResolver result = new InternalResolver();
        JsonProcessorImpl jsonProcessor = new JsonProcessorImpl();
        JSONObject json = jsonProcessor.getJson(persons);

        result.setContentType("application/txt");
        result.setContentLenght(json.toString().length());
        result.setFileName("json.txt");
        result.setContent(json.toString());
        return result;
    };

    private InternalResolver downloadXls(List<Person> persons){
        InternalResolver result = new InternalResolver();
        XLSProcessor xlsProcessor = new XLSProcessorImpl();
        XSSFWorkbook workbook = xlsProcessor.getXls(persons);

        result.setContentType("application/xls");
        result.setContentLenght(workbook.toString().length());
        result.setFileName("persons.xls");
        result.setContent(workbook.toString());
        return result;
    };

    private InternalResolver downloadXml(List<Person> persons){
        InternalResolver result = new InternalResolver();
        XMLProcessor xmlProcessor = new XMLProcessorImpl();
        String s = xmlProcessor.getXml(persons);

        result.setContentType("application/xml");
        result.setContentLenght(s.length());
        result.setFileName("persons.xml");
        result.setContent(s);

        return result;
    };

    class InternalResolver {
        String contentType;
        long contentLenght;
        String fileName;
        String content;

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public long getContentLenght() {
            return contentLenght;
        }

        public void setContentLenght(long contentLenght) {
            this.contentLenght = contentLenght;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
