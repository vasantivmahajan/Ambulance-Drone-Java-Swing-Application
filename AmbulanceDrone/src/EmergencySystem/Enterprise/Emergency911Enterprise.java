/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmergencySystem.Enterprise;

import Drone.DroneDirectory;


/**
 *
 * @author Vasanti
 */
public class Emergency911Enterprise extends Enterprise{

 private String emergency911DepartmentName;
 private String emergency911DepartmentAddress;
   // private OrganisationDirectory orgDir;
// private DroneOrganisation droneOrg;
    
    
   
   
    public Emergency911Enterprise(String name) {
        super(name, EnterpriseType.EMEREGENCY911ENTERPRISE);{
    }
       
        
        
    //    orgDir=new OrganisationDirectory();
    }
    
    
  
 
    public String getEmergency911DepartmentAddress() {
        return emergency911DepartmentAddress;
    }

    public void setEmergency911DepartmentAddress(String emergency911DepartmentAddress) {
        this.emergency911DepartmentAddress = emergency911DepartmentAddress;
    }
   

    public String getEmergency911DepartmentName() {
        return emergency911DepartmentName;
    }

    public void setEmergency911DepartmentName(String emergency911DepartmentName) {
        this.emergency911DepartmentName = emergency911DepartmentName;
    }

//    public DroneOrganisation getDroneOrg() {
//        return droneOrg;
//    }
//
//    public void setDroneOrg(DroneOrganisation droneOrg) {
//        this.droneOrg = droneOrg;
//    }

//    public OrganisationDirectory getOrgDir() {
//        return orgDir;
//    }
//
//    public void setOrgDir(OrganisationDirectory orgDir) {
//        this.orgDir = orgDir;
//    }

   
}
