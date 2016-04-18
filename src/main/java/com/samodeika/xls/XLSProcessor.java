package com.samodeika.xls;

import com.samodeika.entity.Person;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public interface XLSProcessor {

    XSSFWorkbook process(List<Person> persons);

}
