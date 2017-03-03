/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package EmergencySystem.Enterprise;

import Hospital.Organisation.Organisation;
import Hospital.Organisation.OrganisationDirectory;



/**
 *
 * @author Vasanti
 */
public abstract class Enterprise extends Organisation{

    private EnterpriseType enterpriseType;
    private OrganisationDirectory organisationDirectory;
    
    public Enterprise(String name, EnterpriseType type) {
        super(name);
        this.enterpriseType = type;
        organisationDirectory = new OrganisationDirectory();
    }
    
    public enum EnterpriseType{
       HOSPITALENTERPRISE("Hospital Enterprise"),POLICEENTERPRISE("Police Enterprise"),EMEREGENCY911ENTERPRISE("Emergency 911 Enterprise");
        
        private String value;

        private EnterpriseType(String value) {
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

    public EnterpriseType getEnterpriseType() {
        return enterpriseType;
    }

    public OrganisationDirectory getOrganizationDirectory() {
        return organisationDirectory;
    }

}
