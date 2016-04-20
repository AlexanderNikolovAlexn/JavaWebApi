package com.samodeika.xml;

import com.samodeika.entity.Person;

import java.io.InputStream;
import java.util.List;

public interface XMLProcessor {

    String getXml(List<Person> persons);
    List<Person> processFile(InputStream in);

}
