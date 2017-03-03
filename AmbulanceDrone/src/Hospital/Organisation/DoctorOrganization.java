/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Hospital.Organisation;

import Hospital.Role.DoctorRole;
import Hospital.Role.Role;
import java.util.ArrayList;

/**
 *
 *
 */
public class DoctorOrganization extends Organisation{

    public DoctorOrganization() {
        super(Organisation.Type.DOCTOR.getValue());
    }

     public ArrayList<Role> getSupportedRole() {
       
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new DoctorRole());
        return roles;
    
    }

     
}