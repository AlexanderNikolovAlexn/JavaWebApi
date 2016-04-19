package com.samodeika.xls;

import com.samodeika.entity.Person;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XLSProcessorImpl implements XLSProcessor {

    @Override
    public List<Person> processFile(InputStream in) {
        List<Person> persons = new ArrayList<>();
        try {
            XSSFWorkbook workbook= new XSSFWorkbook(in);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            while (rows.hasNext()) {
                Row row = rows.next();
                // skip header
                System.out.println("Row: " + row.toString());
                if (hasHeader(row)) {
                    continue;
                }
                Person p = new Person();
                for (int i = 0; i < row.getLastCellNum(); i++) {
                    if (i == 0) {
                        p.setId((long) row.getCell(i).getNumericCellValue());
                    } else if (i == 1) {
                        p.setName(row.getCell(i).getStringCellValue());
                    } else if (i == 2) {
                        p.setAge((int) row.getCell(i).getNumericCellValue());
                    }
                }
                persons.add(p);
            }
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        return persons;
    }

    private static boolean hasHeader(Row row) {
        return row.toString().contains("Person ID") || row.toString().contains("Name") || row.toString().contains("Age");
    }

    @Override
    public XSSFWorkbook getXls(List<Person> persons) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("persons");
        int rownum = 0;
        addHeader(sheet, rownum++);
        for (Person person : persons) {
            XSSFRow row = sheet.createRow(rownum++);
            int column = 0;
            XSSFCell cell = row.createCell(column++);
            cell.setCellValue(person.getId());
            cell = row.createCell(column++);
            cell.setCellValue(person.getName());
            cell = row.createCell(column++);
            cell.setCellValue(person.getAge());
        }

        return workbook;
    }

    private void addHeader(XSSFSheet sheet, int rownum) {
        XSSFRow row = sheet.createRow(rownum);
        int column = 0;
        XSSFCell cell = row.createCell(column++);
        cell.setCellValue("Person ID");
        cell = row.createCell(column++);
        cell.setCellValue("Name");
        cell = row.createCell(column++);
        cell.setCellValue("Age");
    }
}
