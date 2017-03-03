/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmergencySystem.EmergencyDepartment;

import EmergencySystem.Emergency.Emergency;
import java.util.ArrayList;

/**
 *
 * @author Vasanti
 */
public class EmergencyDepartment {
    private ArrayList<Emergency> emergencyList;

    public ArrayList<Emergency> getEmergencyList() {
        return emergencyList;
    }

    public void setEmergencyList(ArrayList<Emergency> emergencyList) {
        this.emergencyList = emergencyList;
    }
    
    public EmergencyDepartment()
    {
        emergencyList=new ArrayList();
    }
}
