/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Drone;

import Employee.Drone;
import java.util.ArrayList;

/**
 *
 * @author Vasanti
 */
public class DroneStation {
    private String droneStationName;
    private String droneStationAddress;
    private float distanceFromEmergencyLoc;
    
    private ArrayList<Drone> droneList;

    public DroneStation() {
        droneList=new ArrayList();
    }

    public ArrayList<Drone> getDroneList() {
        return droneList;
    }

    public void setDroneList(ArrayList<Drone> droneList) {
        this.droneList = droneList;
    }
    
    
    
     
    public void addDrone(Drone d)
    {
       
        droneList.add(d);
        
    }

    public String getDroneStationName() {
        return droneStationName;
    }

    public void setDroneStationName(String droneStationName) {
        this.droneStationName = droneStationName;
    }

    @Override
    public String toString() {
        return  droneStationName;
    }

    public String getDroneStationAddress() {
        return droneStationAddress;
    }

    public void setDroneStationAddress(String droneStationAddress) {
        this.droneStationAddress = droneStationAddress;
    }

    public float getDistanceFromEmergencyLoc() {
        return distanceFromEmergencyLoc;
    }

    public void setDistanceFromEmergencyLoc(float distanceFromEmergencyLoc) {
        this.distanceFromEmergencyLoc = distanceFromEmergencyLoc;
    }
    
    
    
}
