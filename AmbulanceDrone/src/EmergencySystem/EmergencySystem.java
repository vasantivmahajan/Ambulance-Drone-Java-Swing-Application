/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmergencySystem;

import EmergencySystem.Emergency.EmergencyDirectory;
import EmergencySystem.EmergencyAddressLocation.EmergencyAddressLocationDirectory;
import EmergencySystem.Enterprise.Enterprise;
import EmergencySystem.Network.Network;
import Hospital.Hospital;
import Hospital.Organisation.Organisation;
import LicensePlate.LicensePlateDirectory;
import Person.PersonDirectory;
import java.util.ArrayList;

/**
 *
 * @author Vasanti
 */
public class EmergencySystem extends Organisation{
   private ArrayList<Network> networkList;
   private EmergencyDirectory emergencyDirectory;
   private EmergencyAddressLocationDirectory directory;
   private PersonDirectory personDirectory;
   private LicensePlateDirectory licensePlateDir;

    public static EmergencySystem getEmergencySystem() {
        return emergencySystem;
        
    }

    public static void setEmergencySystem(EmergencySystem aEmergencySystem) {
        emergencySystem = aEmergencySystem;
    }
   
     private static EmergencySystem emergencySystem;
     public static EmergencySystem getInstance() 
     {
        if (emergencySystem == null) 
        {
            emergencySystem = new EmergencySystem();
        }
        return emergencySystem;
    }
     
     


  
    
    private EmergencySystem()
    {
        super(null);
        
        networkList=new ArrayList<>();
        directory=new EmergencyAddressLocationDirectory();
        personDirectory=new PersonDirectory();
        licensePlateDir=new LicensePlateDirectory();
        emergencyDirectory=new EmergencyDirectory();
    }
    
    

  
    public Network addNetwork()
    {
        Network n=new Network();
        networkList.add(n);
        return n;
    }

    public ArrayList<Network> getNetworkList() {
        return networkList;
    }

    public void setNetworkList(ArrayList<Network> networkList) {
        this.networkList = networkList;
    }

    public EmergencyAddressLocationDirectory getDirectory() {
        return directory;
    }

    public void setDirectory(EmergencyAddressLocationDirectory directory) {
        this.directory = directory;
    }

    public PersonDirectory getPersonDirectory() {
        return personDirectory;
    }

    public void setPersonDirectory(PersonDirectory personDirectory) {
        this.personDirectory = personDirectory;
    }

    public LicensePlateDirectory getLicensePlateDir() {
        return licensePlateDir;
    }

    public void setLicensePlateDir(LicensePlateDirectory licensePlateDir) {
        this.licensePlateDir = licensePlateDir;
    }
    
    public boolean checkIfUserNameIsUnique(String username,String password)
    {
        boolean userNameIsUnique=true;
        userNameIsUnique= emergencySystem.getUserAccountDirectory().checkIfUsernameIsUnique(username);
            if(userNameIsUnique==true)
            {
                for(Network n:emergencySystem.getNetworkList())
                {
                    for(Enterprise ent:n.getEntDirObj().getEnterpriseList())
                    {
                       userNameIsUnique= ent.getUserAccountDirectory().checkIfUsernameIsUnique(username);
                       if(userNameIsUnique==false)
                           break;
                       else
                       {
                           for(Organisation org:ent.getOrganizationDirectory().getOrganisationList())
                           {
                               userNameIsUnique=org.getUserAccountDirectory().checkIfUsernameIsUnique(username);
                               if(userNameIsUnique==false)
                                   break;
                           }
                       
                       }
                       if(userNameIsUnique==false)
                           break;
                       
                       
                    }
                    if(userNameIsUnique==false)
                           break;
                }
            }
            
            if(userNameIsUnique==true)
            {
                for(Network n:emergencySystem.getNetworkList())
                {
                    for(Hospital h:n.getHospitalList())
                    {
                       userNameIsUnique=h.getUserAccountDirectory().checkIfUsernameIsUnique(username);
                       if(userNameIsUnique==false)
                        break;
                       else
                       {
                           for(Organisation org:h.getOrganizationDirectory().getOrganisationList())
                           {
                               userNameIsUnique=org.getUserAccountDirectory().checkIfUsernameIsUnique(username);
                               if(userNameIsUnique==false)
                                   break;
                           }
                       
                       }
                       if(userNameIsUnique==false)
                           break;
                    }
                    if(userNameIsUnique==false)
                        break;
                }
            
            }
        return userNameIsUnique;
      }

    public EmergencyDirectory getEmergencyDirectory() {
        return emergencyDirectory;
    }

    public void setEmergencyDirectory(EmergencyDirectory emergencyDirectory) {
        this.emergencyDirectory = emergencyDirectory;
    }
}