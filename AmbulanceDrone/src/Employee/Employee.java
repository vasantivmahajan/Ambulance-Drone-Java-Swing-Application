/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Employee;

/**
 *
 * @author Vasanti
 */
public abstract class Employee {
    
    private String employeeName;
    private int employeeId;
    private static int count = 1;

    public Employee() {
        employeeId = count;
        count++;
    }

    public int getId() {
        return employeeId;
    }

    public void setName(String name) {
        this.employeeName = name;
    }

    
    public String getName() {
        return employeeName;
    }

    @Override
    public String toString() {
        return employeeName;
    }
    
    
}
