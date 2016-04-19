package com.samodeika.dao;

import com.samodeika.entity.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDaoImpl implements PersonDao {

    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Person> getPersons() {
        try (Connection c = getConnection()) {
            PreparedStatement ps = c.prepareStatement("select * from person");
            ResultSet rs = ps.executeQuery();
            List<Person> persons = new ArrayList<>();
            while(rs.next()) {
                Person p = new Person(rs.getLong("id"), rs.getString("name"), rs.getInt("age"));
                persons.add(p);
            }

            return persons;
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean savePersons(List<Person> persons) {
        try (Connection c = getConnection()) {
            c.setAutoCommit(false);
            for(Person person : persons) {
                PreparedStatement ps = c.prepareStatement("INSERT INTO person(name, age) values(?, ?)");
                ps.setString(1, person.getName());
                ps.setInt(2, person.getAge());
                ps.execute();
            }
            c.commit();
            return true;
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean savePerson(Person person) {
        return false;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/alex", "root", "root");
    }
}
