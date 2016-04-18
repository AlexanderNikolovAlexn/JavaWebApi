package com.samodeika.dao;

import com.samodeika.entity.Person;

import java.util.List;

public interface PersonDao {

    public List<Person> getPersons();
    public boolean savePersons(List<Person> persons);
    public boolean savePerson(Person person);

}
