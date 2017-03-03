/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmergencySystem.EmergencyDepartment;

import java.util.ArrayList;

/**
 *
 * @author Vasanti
 */
public class Emergency911DepartmentDirectory {
    private ArrayList<EmergencyDepartment> emergencyDepartmentList;
    
    public Emergency911DepartmentDirectory()
    {
        emergencyDepartmentList=new ArrayList();
    }

    public ArrayList<EmergencyDepartment> getEmergencyDepartmentList() {
        return emergencyDepartmentList;
    }

    public void setEmergencyDepartmentList(ArrayList<EmergencyDepartment> emergencyDepartmentList) {
        this.emergencyDepartmentList = emergencyDepartmentList;
    }
    
    
}
