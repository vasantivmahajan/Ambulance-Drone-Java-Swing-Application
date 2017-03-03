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
public class AccidentEmergency extends Emergency
{
     private ArrayList<Description> descriptionList;
    public AccidentEmergency()
    {
        super(Emergency.EmergencyType.ACCIDENTEMERGENCY.getValue());
        descriptionList=new ArrayList<>();
        Description d1=new Description();
        d1.setName("Car accident-Head Injury");
        descriptionList.add(d1);
        
        Description d2=new Description();
        d2.setName("Car accident-Body Injury");
        descriptionList.add(d2);
        
        Description d3=new Description();
        d3.setName("Drowning");
        descriptionList.add(d3);
        
    }

    public ArrayList<Description> getDescriptionList() {
        return descriptionList;
    }

    public void setDescriptionList(ArrayList<Description> descriptionList) {
        this.descriptionList = descriptionList;
    }
    
    
    
    
    
}
