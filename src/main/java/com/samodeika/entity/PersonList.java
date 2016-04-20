package com.samodeika.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class PersonList {

    private List<Person> person;

    public PersonList() {
    }

    public PersonList(List<Person> person) {
        this.person = person;
    }

    public List<Person> getPerson() {
        return person;
    }

    @XmlElement(name = "person")
    public void setPerson(List<Person> person) {
        this.person = person;
    }
}
