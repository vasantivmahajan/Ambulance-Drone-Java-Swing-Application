/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LicensePlate;

import java.util.ArrayList;

/**
 *
 * @author Vasanti
 */
public class LicensePlateDirectory {
    private ArrayList<LicensePlate> licensePlateList;
    
    public LicensePlateDirectory()
    {
    licensePlateList=new ArrayList<LicensePlate>();
    
    }

    public ArrayList<LicensePlate> getLicensePlateList() {
        return licensePlateList;
    }

    public void setLicensePlateList(ArrayList<LicensePlate> licensePlateList) {
        this.licensePlateList = licensePlateList;
    }
    
    public LicensePlate addLicensePlate()
    {
        LicensePlate licensePlate=new LicensePlate();
        licensePlateList.add(licensePlate);
        return licensePlate;
    }
}
