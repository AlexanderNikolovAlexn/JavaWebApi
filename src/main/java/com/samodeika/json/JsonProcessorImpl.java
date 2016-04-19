package com.samodeika.json;

import com.samodeika.entity.Person;
import com.samodeika.utils.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonProcessorImpl {

    public static List<Person> proccessFile(InputStream in) {
        System.out.println("Read json file");
        String fileContent = FileUtils.getFileContent(in);
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
