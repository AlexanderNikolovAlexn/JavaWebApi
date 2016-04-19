package com.samodeika.json;

import com.samodeika.entity.Person;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;

public interface JsonProcessor {

    List<Person> processFile(InputStream in);
    JSONObject getJson(List<Person> persons);

}
