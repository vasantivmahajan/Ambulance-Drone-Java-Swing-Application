/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package EmergencySystem.Enterprise;

import java.util.ArrayList;

/**
 *
 * @author Vasanti
 */
public class EnterpriseDirectory {
    
    private ArrayList<Enterprise> enterpriseList;

    public EnterpriseDirectory() {
        enterpriseList = new ArrayList<>();
    }

    public ArrayList<Enterprise> getEnterpriseList() {
        return enterpriseList;
    }
    
    public Enterprise createAndAddEnterprise(String name, Enterprise.EnterpriseType type,String address){
        
        Enterprise enterprise = null;
//        if (type == Enterprise.EnterpriseType.HEALTHENTERPRISE){
//            enterprise = new HealthEnterprise(name);
//            enterpriseList.add(enterprise);
//        }
//        
        if(type == Enterprise.EnterpriseType.EMEREGENCY911ENTERPRISE)
        {
            enterprise = new Emergency911Enterprise(name);
            enterpriseList.add(enterprise);
        }
        
        else if(type == Enterprise.EnterpriseType.POLICEENTERPRISE)
        {
            enterprise = new PoliceEnterprise(name);
            enterpriseList.add(enterprise);
        }
        
        
        return enterprise;
    }
    
}
