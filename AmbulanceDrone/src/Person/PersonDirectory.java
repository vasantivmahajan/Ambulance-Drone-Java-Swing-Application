/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Person;

import java.util.ArrayList;

/**
 *
 * @author Vasanti
 */
public class PersonDirectory {
    private ArrayList<Person> personList;
    
    public PersonDirectory()
    {
        personList=new ArrayList<Person>();
    }

    public ArrayList<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(ArrayList<Person> personList) {
        this.personList = personList;
    }
    
    public Person addPerson()
    {
        Person p=new Person();
        personList.add(p);
        return p;
    }
    
}
