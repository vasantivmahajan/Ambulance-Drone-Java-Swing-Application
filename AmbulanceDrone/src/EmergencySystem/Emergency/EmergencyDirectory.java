/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmergencySystem.Emergency;

import java.util.ArrayList;

/**
 
 * 
 * @author Vasanti
 */
public class EmergencyDirectory  {
    private ArrayList<Emergency> emergencyList;
    
    public EmergencyDirectory()
    {
        emergencyList=new ArrayList<>();
    }

    public ArrayList<Emergency> getEmergencyList() {
        return emergencyList;
    }

    public void setEmergencyList(ArrayList<Emergency> emergencyList) {
        this.emergencyList = emergencyList;
    }
    
    
    public Emergency createEmergency()
    {
        Emergency e=new Emergency();
        emergencyList.add(e);
        return e;
    }
    
}
