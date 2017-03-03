/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Employee;



/**
 *
 * @author Vasanti
 */
public class Drone extends Employee {
    private String droneId;
    private String status;

    private double speedOfDrone=100;
   
   
    
    public Drone()
    {

    }

    public String getDroneId() {
        return droneId;
    }

    public void setDroneId(String droneId) {
        this.droneId = droneId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    @Override
    
    public String toString() {
        return droneId;
    }

    public double getSpeedOfDrone() {
        return speedOfDrone;
    }

    public void setSpeedOfDrone(double speedOfDrone) {
        this.speedOfDrone = speedOfDrone;
    }
  
}
