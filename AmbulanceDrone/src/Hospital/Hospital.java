/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hospital;

import EmergencySystem.Enterprise.Enterprise;
import Hospital.Organisation.OrganisationDirectory;

/**
 *
 * @author Vasanti
 */
public class Hospital extends Enterprise{
    private String hospitalName;
    private String hospitalLocation;
    private String speciality;
    private int numberOfBeds;
    private int numberOfEmptyBeds;
    private float distanceFromEmergencyLocation;
 //   private OrganisationDirectory hospitalOrganisationDirectoryObject;
    private float timeTakenToReachTheAccidentLoc;

    public Hospital(String name, EnterpriseType type) {
        super(name, type);
     //   hospitalOrganisationDirectoryObject=new OrganisationDirectory();
    }

  
    
    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalLocation() {
        return hospitalLocation;
    }

    public void setHospitalLocation(String hospitalLocation) {
        this.hospitalLocation = hospitalLocation;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public int getNumberOfEmptyBeds() {
        return numberOfEmptyBeds;
    }

    public void setNumberOfEmptyBeds(int numberOfEmptyBeds) {
        this.numberOfEmptyBeds = numberOfEmptyBeds;
    }

//    public OrganisationDirectory getHospitalOrganisationDirectoryObject() {
//        return hospitalOrganisationDirectoryObject;
//    }
//
//    public void setHospitalOrganisationDirectoryObject(OrganisationDirectory hospitalOrganisationDirectoryObject) {
//        this.hospitalOrganisationDirectoryObject = hospitalOrganisationDirectoryObject;
//    }

    @Override
    public String toString() {
        return hospitalName;
    }

    public float getDistanceFromEmergencyLocation() {
        return distanceFromEmergencyLocation;
    }

    public void setDistanceFromEmergencyLocation(float distanceFromEmergencyLocation) {
        this.distanceFromEmergencyLocation = distanceFromEmergencyLocation;
    }

    public float getTimeTakenToReachTheAccidentLoc() {
        return timeTakenToReachTheAccidentLoc;
    }

    public void setTimeTakenToReachTheAccidentLoc(float timeTakenToReachTheAccidentLoc) {
        this.timeTakenToReachTheAccidentLoc = timeTakenToReachTheAccidentLoc;
    }

    
    
    
}
