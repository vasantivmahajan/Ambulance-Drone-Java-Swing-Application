/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Hospital.Role;

import EmergencySystem.EmergencySystem;
import EmergencySystem.Enterprise.Enterprise;
import EmergencySystem.Network.Network;
import Hospital.UserAccount.UserAccount;
import javax.swing.JPanel;

/**
 *
 * @author raunak
 */
public abstract class Role {
    
    public enum RoleType{
        
        DOCTOR("Doctor"),
        AMBULANCE("Ambulance"),
        EMERGENCYSYSTEMADMIN("Emergency system admin"),
        EMERGENCY911DEPARTMENTADMIN("Emergency 911 department admin"),
        DRONE("Drone"),
        HOSPITALENTERPRISEADMIN("Hospital Enterprise Admin");
        
        
        private String value;
        private RoleType(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
    
    public abstract JPanel createWorkArea(JPanel userProcessContainer, 
            UserAccount account, 
            EmergencySystem system,Network network,Enterprise enterprise);

    @Override
    public String toString() {
        return this.getClass().getName();
    }
    
    
}