package com.samodeika.json;

import com.samodeika.entity.Person;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonProcessorImpl implements JsonProcessor{

    @Override
    public List<Person> processFile(InputStream in) {
        System.out.println("Read json file");
        String fileContent = null;
        try {
            fileContent = IOUtils.toString(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject json = new JSONObject(fileContent);
        JSONArray array = json.getJSONArray("persons");
        List<Person> persons = new ArrayList<Person>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            Person p = new Person(jsonObject.getLong("id"), jsonObject.getString("name"), jsonObject.getInt("age"));
            persons.add(p);
        }

        return persons;
    }

    @Override
    public JSONObject getJson(List<Person> persons) {
        List<Person> personList = persons;
        JSONObject result = new JSONObject();
        JSONArray personArray = new JSONArray();
        int i = 0;
        for (Person person : personList) {
            JSONObject tmp = new JSONObject();
            tmp.put("id", person.getId());
            tmp.put("name", person.getName());
            tmp.put("age", person.getAge());
            personArray.put(i++, tmp);
        }

        result.put("persons", personArray);
        return result;
    }

}
