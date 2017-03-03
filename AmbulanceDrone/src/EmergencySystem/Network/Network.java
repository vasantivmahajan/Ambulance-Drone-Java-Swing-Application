/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmergencySystem.Network;

import EmergencySystem.Enterprise.Enterprise;
import EmergencySystem.Enterprise.Enterprise.EnterpriseType;
import EmergencySystem.Enterprise.EnterpriseDirectory;
import Hospital.Hospital;
import java.util.ArrayList;

/**
 *
 * @author Vasanti
 */

public class Network { 
  
private String networkName;
private EnterpriseDirectory entDirObj;
private ArrayList<Hospital>hospitalList;



public Network()
{

entDirObj=new EnterpriseDirectory();
hospitalList=new ArrayList<Hospital>();

}

   


    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    @Override
    public String toString() {
        return  networkName;
    }

 

    public EnterpriseDirectory getEntDirObj() {
        return entDirObj;
    }

    public void setEntDirObj(EnterpriseDirectory entDirObj) {
        this.entDirObj = entDirObj;
    }

    public ArrayList<Hospital> getHospitalList() {
        return hospitalList;
    }

    public void setHospitalList(ArrayList<Hospital> hospitalEnterpriseList) {
        this.hospitalList = hospitalEnterpriseList;
    }

        
    public Hospital addHospital(String name)
    {   Hospital h=null; 
         for (Enterprise.EnterpriseType type : Enterprise.EnterpriseType.values()) {
           if(type.getValue().equals("Hospital Enterprise"))
           {
                h=new Hospital(name,type);
                hospitalList.add(h);
                
           }
        }
        return h;
       
    }
    
}
