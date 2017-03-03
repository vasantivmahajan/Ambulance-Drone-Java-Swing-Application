/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hospital.Organisation;

import Employee.EmployeeDirectory;
import Hospital.Role.Role;
import Hospital.UserAccount.UserAccountDirectory;
import Hospital.WorkQueue.WorkQueue;
import java.util.ArrayList;

/**
 *
 * @author Vasanti
 */
public class Organisation {
    private String name;
    private WorkQueue workQueue;
    private EmployeeDirectory employeeDirectory;
    private UserAccountDirectory userAccountDirectory;
    
    
    public enum Type{
        DOCTOR("Doctor Organization"), AMBULANCE("Abulance Organization"),
        HOSPITALENTERPRISEADMIN("Hospital Enterprise Organisation"),DRONE("Drone organisation"),
        POLICE("Police organisation"),
        EMERGENCY911DEPARTMENT("Emergency 911 department organisation"), 
        EMERGENCYSYSTEMADMIN("Emergency system admin organisation");
        private String value;
        private Type(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }
    

    public Organisation(String name) {
        this.name = name;
        workQueue = new WorkQueue();
        employeeDirectory = new EmployeeDirectory();
        userAccountDirectory = new UserAccountDirectory();
       
    }

    
    public UserAccountDirectory getUserAccountDirectory() {
        return userAccountDirectory;
    }

    public EmployeeDirectory getEmployeeDirectory() {
        return employeeDirectory;
    }
    
    public String getName() {
        return name;
    }

    public WorkQueue getWorkQueue() {
        return workQueue;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWorkQueue(WorkQueue workQueue) {
        this.workQueue = workQueue;
    }

    @Override
    public String toString() {
        return name;
    }
    
    
}
