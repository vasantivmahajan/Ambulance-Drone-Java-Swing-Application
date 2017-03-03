/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmergencySystem.EmergencyAddressLocation;

import java.util.ArrayList;

/**
 *
 * @author Vasanti
 */
public class EmergencyAddressLocationDirectory {
      private ArrayList<EmergencyAddressLocation> emergencyAddressLocationList;
      
      public EmergencyAddressLocationDirectory()
      {
          emergencyAddressLocationList=new ArrayList<>();
      }

    public ArrayList<EmergencyAddressLocation> getEmergencyAddressLocationList() {
        return emergencyAddressLocationList;
    }

    public void setEmergencyAddressLocationList(ArrayList<EmergencyAddressLocation> emergencyAddressLocationList) {
        this.emergencyAddressLocationList = emergencyAddressLocationList;
    }
      
    public EmergencyAddressLocation addEmeregncyLocation()
    {
        EmergencyAddressLocation location=new EmergencyAddressLocation();
        emergencyAddressLocationList.add(location);
        return location;
    }
}

