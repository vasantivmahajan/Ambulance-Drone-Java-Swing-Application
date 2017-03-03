/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Hospital.Organisation;

import Hospital.Role.AmbulanceRole;
import Hospital.Role.Role;
import java.util.ArrayList;

/**
 *
 * @Vasanti
 */
public class AmbulanceOrganisation extends Organisation{

    public AmbulanceOrganisation() {
        super(Organisation.Type.AMBULANCE.getValue());
    }


    public ArrayList<Role> getSupportedRole() {
       
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new AmbulanceRole());
        return roles;
    
    }

 
   
    
    
}
