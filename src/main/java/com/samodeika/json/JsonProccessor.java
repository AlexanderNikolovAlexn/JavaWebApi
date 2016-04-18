package com.samodeika.json;

import com.samodeika.entity.Person;

import java.io.InputStream;
import java.util.List;

public interface JsonProccessor {

    List<Person> proccessJsonFile(InputStream in);

}
