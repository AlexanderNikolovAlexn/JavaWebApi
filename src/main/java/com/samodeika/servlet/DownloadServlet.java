package com.samodeika.servlet;

import com.samodeika.constants.Constants;
import com.samodeika.dao.PersonDao;
import com.samodeika.dao.PersonDaoImpl;
import com.samodeika.entity.Person;
import com.samodeika.json.JsonProcessorImpl;
import com.samodeika.utils.FileUtils;
import com.samodeika.xls.XLSProcessorImpl;
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
        switch (fileType) {
            case Constants.C_JSON:
                internalResolver = downloadJson(persons);
                file = FileUtils.writeToFile(internalResolver.getFileName(), internalResolver.getContent());
                FileInputStream inStream = new FileInputStream(file);

                // obtains response's output stream
                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                while ((bytesRead = inStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }

                inStream.close();
                break;
            case Constants.C_XLS:
                internalResolver = downloadXls(persons);
                XLSProcessorImpl xlsProcessor = new XLSProcessorImpl();
                XSSFWorkbook workbook = xlsProcessor.getXls(persons);
                //file = FileUtils.writeToFile(internalResolver.getFileName(), internalResolver.getContent());
                workbook.write(outStream);
                break;
            case Constants.C_XML:
                internalResolver = downloadXml(persons);
                break;
        }

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
        resp.setHeader(headerKey, headerValue);
        resp.setContentType(internalResolver.getContentType());
        resp.setContentLength((int) internalResolver.getContentLenght());

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
        XLSProcessorImpl xlsProcessor = new XLSProcessorImpl();
        XSSFWorkbook workbook = xlsProcessor.getXls(persons);

        result.setContentType("application/xls");
        result.setContentLenght(workbook.toString().length());
        result.setFileName("persons.xls");
        result.setContent(workbook.toString());
        return result;
    };

    private InternalResolver downloadXml(List<Person> persons){
        InternalResolver result = new InternalResolver();

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
