/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hospital.Organisation;

import Drone.DroneDirectory;
import Hospital.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author Vasanti
 */
public class DroneOrganisation extends Organisation{
    private DroneDirectory droneDirectoryObject;
      public DroneOrganisation() {
        super(Organisation.Type.DRONE.getValue());
         droneDirectoryObject=new DroneDirectory();
    }

        
     public DroneDirectory getDroneDirectoryObject() {
        return droneDirectoryObject;
    }

    public void setDroneDirectoryObject(DroneDirectory droneDirectoryObject) {
        this.droneDirectoryObject = droneDirectoryObject;
    }


}
