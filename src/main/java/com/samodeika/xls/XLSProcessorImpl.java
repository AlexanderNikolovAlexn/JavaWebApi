package com.samodeika.xls;

import com.samodeika.entity.Person;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public class XLSProcessorImpl implements XLSProcessor {

    @Override
    public XSSFWorkbook getXls(List<Person> persons) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("persons");
        int rownum = 0;
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
}
