/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Employee;

import java.util.ArrayList;

/**
 *
 * @author Vasanti
 */
public class EmployeeDirectory {
    
    private ArrayList<Employee> employeeList;

    public EmployeeDirectory() {
        employeeList = new ArrayList<>();
    }

    public ArrayList<Employee> getEmployeeList() {
        return employeeList;
    }
    
    
    public void createEmployee(Employee e){
        
        employeeList.add(e);
       
    }
}