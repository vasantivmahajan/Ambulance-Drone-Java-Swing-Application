/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmergencySystem.Emergency;

import java.util.ArrayList;

/**
 *
 * @author Vasanti
 */
public class FireEmergency extends Emergency{
    private ArrayList<Description> descriptionList;
     public FireEmergency()
    {
        super(Emergency.EmergencyType.FIREEMERGENCY.getValue());
         descriptionList=new ArrayList<>();
        Description d1=new Description();
        d1.setName("Fire");
        descriptionList.add(d1);

    }
    
      public ArrayList<Description> getDescriptionList() {
        return descriptionList;
    }

    public void setDescriptionList(ArrayList<Description> descriptionList) {
        this.descriptionList = descriptionList;
    }
    
    
    
    
}
