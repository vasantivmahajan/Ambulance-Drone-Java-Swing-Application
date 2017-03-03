/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Drone;

import java.util.ArrayList;

/**
 *
 * @author Vasanti
 */
public class DroneDirectory {
    private ArrayList<DroneStation> droneStationList;
    
    
    public DroneDirectory()
    {
       droneStationList=new ArrayList();
        
    }

    public ArrayList<DroneStation> getDroneStationList() {
        return droneStationList;
    }

    public void setDroneStationList(ArrayList<DroneStation> droneStationList) {
        this.droneStationList = droneStationList;
    }

    public DroneStation addDroneStation()
    {
        DroneStation d=new DroneStation();
        droneStationList.add(d);
        return d;
    }
    
   
    
}
