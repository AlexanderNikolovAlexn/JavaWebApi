package com.samodeika.xls;

import com.samodeika.entity.Person;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.List;

public interface XLSProcessor {

    XSSFWorkbook getXls(List<Person> persons);
    List<Person> processFile(InputStream in);

}
