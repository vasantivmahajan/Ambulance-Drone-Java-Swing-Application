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
public class MedicalEmergency extends Emergency {
    private ArrayList<Description> descriptionList;
     public MedicalEmergency()
    {
        super(Emergency.EmergencyType.MEDICALEMERGENCY.getValue());
        descriptionList=new ArrayList<>();
        Description d1=new Description();
        d1.setName("Heart attack");
        descriptionList.add(d1);
        
        Description d2=new Description();
        d2.setName("Asthama attack");
        descriptionList.add(d2);
    }
    
      public ArrayList<Description> getDescriptionList() {
        return descriptionList;
    }

    public void setDescriptionList(ArrayList<Description> descriptionList) {
        this.descriptionList = descriptionList;
    }
    
}
