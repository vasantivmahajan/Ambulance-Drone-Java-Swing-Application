/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Employee;

/**
 *
 * @author Vasanti
 */
public class Doctor extends Employee {
    private boolean doctorsAvailablityStatus;
    private String doctorsSpeciality;

    public boolean isDoctorsAvailablityStatus() {
        return doctorsAvailablityStatus;
    }

    public void setDoctorsAvailablityStatus(boolean doctorsAvailablityStatus) {
        this.doctorsAvailablityStatus = doctorsAvailablityStatus;
    }

    public String getDoctorsSpeciality() {
        return doctorsSpeciality;
    }

    public void setDoctorsSpeciality(String doctorsSpeciality) {
        this.doctorsSpeciality = doctorsSpeciality;
    }
    
    
}
