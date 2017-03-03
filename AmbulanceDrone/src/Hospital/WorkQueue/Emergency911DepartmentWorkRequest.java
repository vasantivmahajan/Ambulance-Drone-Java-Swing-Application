/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hospital.WorkQueue;

import EmergencySystem.Emergency.Emergency;
import Employee.Employee;

/**
 *
 * @author Vasanti
 */
public class Emergency911DepartmentWorkRequest extends WorkRequest implements Comparable{
    private Employee employee;
    private Emergency emergency;
    
    public Emergency911DepartmentWorkRequest()
    {
 //   employee=new Employee();
    emergency=new Emergency();
    }
    

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Emergency getEmergency() {
        return emergency;
    }

    public void setEmergency(Emergency emergency) {
        this.emergency = emergency;
    }

    @Override
    public int compareTo(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
//    
//    public int compareTo(Emergency compare) {
//        int comparePriority=((Emergency)compare).getPriority();
//        return comparePriority-this.priority;
//    }
//    
}
