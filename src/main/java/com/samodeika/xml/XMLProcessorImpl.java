package com.samodeika.xml;

import com.samodeika.entity.Person;
import com.samodeika.entity.PersonList;

import javax.xml.bind.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

public class XMLProcessorImpl implements XMLProcessor {

    @Override
    public String getXml(List<Person> persons) {
        String s = null;
        PersonList p = new PersonList(persons);
        try {
            JAXBContext context = JAXBContext.newInstance(PersonList.class);
            Marshaller marshaller = context.createMarshaller();
            ByteArrayOutputStream buff = new ByteArrayOutputStream();
            marshaller.marshal(p, buff);
            s = new String(buff.toByteArray());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return s;
    }

    @Override
    public List<Person> processFile(InputStream in) {
        PersonList personList = getPersonList(in);
        return personList.getPerson();
    }

    private PersonList getPersonList(InputStream in) {
        PersonList personList = new PersonList();
        try {
            JAXBContext context = JAXBContext.newInstance(PersonList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            personList = (PersonList) unmarshaller.unmarshal(in);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return personList;
    }
}
