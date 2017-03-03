/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmergencySystem;

import Employee.EmergencySystemAdmin;
import Employee.EnterpriseAdmin;
import Employee.HospitalEnterpriseAdmin;
import Employee.Drone;
import Drone.DroneStation;
import EmergencySystem.EmergencyAddressLocation.EmergencyAddressLocation;
import EmergencySystem.Enterprise.Emergency911Enterprise;
import EmergencySystem.Enterprise.Enterprise;
import EmergencySystem.Enterprise.Enterprise.EnterpriseType;
import EmergencySystem.Network.Network;
import Employee.Ambulance;
import Employee.Doctor;
import Hospital.Hospital;
import Hospital.Organisation.AmbulanceOrganisation;
import Hospital.Organisation.DoctorOrganization;
import Hospital.Organisation.DroneOrganisation;
import Hospital.Organisation.Organisation;
import Hospital.Organisation.Organisation.Type;
import Hospital.Role.AmbulanceRole;
import Hospital.Role.DoctorRole;
import Hospital.Role.DroneRole;
import Hospital.Role.EmergencySystemAdminRole;
import Hospital.Role.Emergency911EnterpriseAdminRole;
import Hospital.Role.HospitalEnterpriseAdminRole;
import Hospital.Role.PoliceAdminRole;
import Hospital.UserAccount.UserAccount;
import LicensePlate.LicensePlate;
import Person.Person;

/**
 *
 * @author Vasanti
 */
public class ConfigureASystem {
    public static EmergencySystem configure(){
        
        EmergencySystem system = EmergencySystem.getInstance();
        
        EmergencySystemAdmin emerAdmin=new EmergencySystemAdmin();
      
        system.getEmployeeDirectory().createEmployee(emerAdmin);
        UserAccount esadmin_ua = system.getUserAccountDirectory().createUserAccount("esadmin", "esadmin", emerAdmin, new EmergencySystemAdminRole());
         //end of emergency system admin creation
        
        
        //Creating boston network
        Network n1=system.addNetwork();
        n1.setNetworkName("Boston");
        
        //Creating person directory
        Person p1=system.getPersonDirectory().addPerson();
        p1.setName("Vasanti Mahajan");
        p1.setAge(23);
        p1.setAddress("44 Clearway street");
        p1.setCarOwned("Honda City");
        p1.setDriversLicenseNumber(871453548);
        p1.setLicensePlateNumber("FP1744");
        p1.setPhoneNumber("6319441865");
        p1.setEmergencyContactNumber("6319441700");
        p1.setPhoto("Images/person1.jpg");
        
        
        Person p2=system.getPersonDirectory().addPerson();
        p2.setName("Shradha Mahajan");
        p2.setAge(27);
        p2.setAddress("75 st alphonsous street");
        p2.setCarOwned("Mercedes Benz");
        p2.setDriversLicenseNumber(665497258);
        p2.setLicensePlateNumber("CI0000");
        p2.setPhoneNumber("6319771865");
        p2.setEmergencyContactNumber("6319441700");
        p2.setPhoto("Images/person2.jpg");
        
        Person p3=system.getPersonDirectory().addPerson();
        p3.setName("Vinayak Mahajan");
        p3.setAge(58);
        p3.setAddress("32 Church St, Cambridge");
        p3.setCarOwned("Audi");
        p3.setDriversLicenseNumber(354876495);
        p3.setLicensePlateNumber("RT72LY");
        p3.setPhoneNumber("6319778965");
        p3.setEmergencyContactNumber("6319441700");
        p3.setPhoto("Images/person3.jpg");
        
        Person p4=system.getPersonDirectory().addPerson();
        p4.setName("Nilima Mahajan");
        p4.setAge(48);
        p4.setAddress("99 Albion St, Somerville");
        p4.setCarOwned("Jaguar");
        p4.setDriversLicenseNumber(879876495);
        p4.setLicensePlateNumber("40559F");
        p4.setPhoneNumber("6319879965");
        p4.setEmergencyContactNumber("6319441700");
        p4.setPhoto("Images/person4.jpg");
        
        Person p5=system.getPersonDirectory().addPerson();
        p5.setName("Aniruddh Jani");
        p5.setAge(28);
        p5.setAddress("20 Reservoir Rd, Quincy");
        p5.setCarOwned("BMW");
        p5.setDriversLicenseNumber(754896495);
        p5.setLicensePlateNumber("BR2515");
        p5.setPhoneNumber("6719879965");
        p5.setEmergencyContactNumber("6319441700");
        p5.setPhoto("Images/person5.jpg");
        
        Person p6=system.getPersonDirectory().addPerson();
        p6.setName("Dhaval Jani");
        p6.setAge(32);
        p6.setAddress("1037 Southern Artery, Quincy");
        p6.setCarOwned("Toyota");
        p6.setDriversLicenseNumber(879896495);
        p6.setLicensePlateNumber("PL2020");
        p6.setPhoneNumber("6718799965");
        p6.setEmergencyContactNumber("6319441700");
        p6.setPhoto("Images/person6.png");
        
        
        //Creating licenseplate directory
        
        LicensePlate l1=system.getLicensePlateDir().addLicensePlate();
        l1.setLicensePlateNumber("Images//license_plate1.jpg");
        
         LicensePlate l2=system.getLicensePlateDir().addLicensePlate();
        l2.setLicensePlateNumber("Images//license_plate2.jpg");
        
         LicensePlate l3=system.getLicensePlateDir().addLicensePlate();
        l3.setLicensePlateNumber("Images//license_plate3.jpg");
        
         LicensePlate l4=system.getLicensePlateDir().addLicensePlate();
        l4.setLicensePlateNumber("Images//license_plate4.jpg");
        
         LicensePlate l5=system.getLicensePlateDir().addLicensePlate();
        l5.setLicensePlateNumber("Images//license_plate5.jpg");
        
         LicensePlate l6=system.getLicensePlateDir().addLicensePlate();
        l6.setLicensePlateNumber("Images//license_plate6.jpg");
        
        //Creation of enterprises in boston

        Enterprise.EnterpriseType type1_n2 = EnterpriseType.EMEREGENCY911ENTERPRISE;
        Enterprise enterprisen2_2 = n1.getEntDirObj().createAndAddEnterprise("BostonPSAP",type1_n2,"360 Huntington Ave, Boston, MA 02115" );
        
        Enterprise.EnterpriseType type2_n2 = EnterpriseType.POLICEENTERPRISE;
        Enterprise enterprisen2_3 = n1.getEntDirObj().createAndAddEnterprise("BostonPolice",type2_n2,"" );
        
       //Creation of boston enterprise admin
        EnterpriseAdmin bostonpsapAdmin=new EnterpriseAdmin();
        enterprisen2_2.getEmployeeDirectory().createEmployee(bostonpsapAdmin);
        enterprisen2_2.getUserAccountDirectory().createUserAccount("bostonpsap", "bostonpsap", bostonpsapAdmin, new Emergency911EnterpriseAdminRole());
        
        
        EnterpriseAdmin bostonPoliceAdmin=new EnterpriseAdmin();
        enterprisen2_3.getEmployeeDirectory().createEmployee(bostonPoliceAdmin);
        enterprisen2_3.getUserAccountDirectory().createUserAccount("bospoliceadmin", "bospoliceadmin", bostonPoliceAdmin, new PoliceAdminRole());
        bostonPoliceAdmin.setAvailable(true);
        
           
        
        //creating hospitals in the network
        

        Hospital h1_2=n1.addHospital("Brigham and Womens Hospital");
        h1_2.setHospitalName("Brigham and Womens Hospital");
        h1_2.setHospitalLocation("75 Francis St, Boston, MA 02115");
        h1_2.setSpeciality("Orthopedic");
        h1_2.setNumberOfBeds(50);
        h1_2.setNumberOfEmptyBeds(5);
        
        
        
        Hospital h1_3=n1.addHospital("Beth Israel Deaconess Medical Center");
        h1_3.setHospitalName("Beth Israel Deaconess Medical Center");
        h1_3.setHospitalLocation("330 Brookline Ave, Boston, MA 02215");
        h1_3.setSpeciality("Neurology");
        h1_3.setNumberOfBeds(50);
        h1_3.setNumberOfEmptyBeds(0);
        
        Hospital h1_4=n1.addHospital("Shriners Hospitals for Children");
        h1_4.setHospitalName("Shriners Hospitals for Children");
        h1_4.setHospitalLocation("51 Blossom St, Boston, MA 02115");
        h1_4.setSpeciality("Cardiothoracic");
        h1_4.setNumberOfBeds(50);
        h1_4.setNumberOfEmptyBeds(5);
        
        Hospital h1_5=n1.addHospital("Fenway health center");
        h1_5.setHospitalName("Fenway health center");
        h1_5.setHospitalLocation("1340 Boylston St, Boston, MA 02215");
        h1_5.setSpeciality("Allergist");
        h1_5.setNumberOfBeds(50);
        h1_5.setNumberOfEmptyBeds(10);
        
         //creating hospital enterprise admin
        HospitalEnterpriseAdmin h1n1=new HospitalEnterpriseAdmin();
        h1n1.setName("BrighamAdmin");
        h1_2.getEmployeeDirectory().createEmployee(h1n1);
        h1_2.getUserAccountDirectory().createUserAccount("brighamadmin", "brighamadmin", h1n1, new HospitalEnterpriseAdminRole());

        HospitalEnterpriseAdmin h2n1=new HospitalEnterpriseAdmin();
        h2n1.setName("BethAdmin");
        h1_3.getEmployeeDirectory().createEmployee(h2n1);
        h1_3.getUserAccountDirectory().createUserAccount("bethadmin", "bethadmin", h2n1, new HospitalEnterpriseAdminRole());
         
        HospitalEnterpriseAdmin h3n1=new HospitalEnterpriseAdmin();
        h3n1.setName("ShrinersAdmin");
      
        h1_4.getEmployeeDirectory().createEmployee(h3n1);
        h1_4.getUserAccountDirectory().createUserAccount("shrinersadmin", "shrinersadmin", h3n1, new HospitalEnterpriseAdminRole());
         
        HospitalEnterpriseAdmin h4n1=new HospitalEnterpriseAdmin();
        h4n1.setName("FenwayAdmin");
        h1_5.getEmployeeDirectory().createEmployee(h4n1);
        h1_5.getUserAccountDirectory().createUserAccount("fenwayadmin", "fenwayadmin", h4n1, new HospitalEnterpriseAdminRole());
         
        
        //creating doctor employees and ambulance employees
        h1_2.getOrganizationDirectory().createOrganisation(Type.DOCTOR);
        h1_2.getOrganizationDirectory().createOrganisation(Type.AMBULANCE);
        
        Doctor d1_n1=new Doctor();
        d1_n1.setName("BRDoc1");
        d1_n1.setDoctorsAvailablityStatus(true);
        d1_n1.setDoctorsSpeciality("Car accident-Body Injury");
        
        Doctor d2_n1=new Doctor();
        d2_n1.setName("BRDoc2");
        d2_n1.setDoctorsAvailablityStatus(true);
        d2_n1.setDoctorsSpeciality("Car accident-Head Injury");
        
        
        
       for(Organisation org: h1_2.getOrganizationDirectory().getOrganisationList())
       {
           if(org instanceof DoctorOrganization)
           {
               ((DoctorOrganization)org).getEmployeeDirectory().createEmployee(d1_n1);
               ((DoctorOrganization)org).getEmployeeDirectory().createEmployee(d2_n1);
               
               
               ((DoctorOrganization)org).getUserAccountDirectory().createUserAccount("brdoc1", "brdoc1", d1_n1, new DoctorRole());
               ((DoctorOrganization)org).getUserAccountDirectory().createUserAccount("brdoc2", "brdoc2", d2_n1, new DoctorRole());
               
              
           }
       }
        h1_3.getOrganizationDirectory().createOrganisation(Type.DOCTOR);
        h1_3.getOrganizationDirectory().createOrganisation(Type.AMBULANCE);
        
       Doctor d3_n1=new Doctor();
        d3_n1.setName("BEDoc1");
        d3_n1.setDoctorsAvailablityStatus(true);
        d3_n1.setDoctorsSpeciality("Car accident-Head Injury");
        
        Doctor d4_n1=new Doctor();
        d4_n1.setName("BEDoc2");
        d4_n1.setDoctorsAvailablityStatus(true);
        d4_n1.setDoctorsSpeciality("Heart attack");
        
         for(Organisation org: h1_3.getOrganizationDirectory().getOrganisationList())
       {
           if(org instanceof DoctorOrganization)
           {
               ((DoctorOrganization)org).getEmployeeDirectory().createEmployee(d3_n1);
               ((DoctorOrganization)org).getEmployeeDirectory().createEmployee(d4_n1);
               
               ((DoctorOrganization)org).getUserAccountDirectory().createUserAccount("bedoc1", "bedoc1", d3_n1, new DoctorRole());
               ((DoctorOrganization)org).getUserAccountDirectory().createUserAccount("bedoc2", "bedoc2", d4_n1, new DoctorRole());
           
           }
       }
        
        h1_4.getOrganizationDirectory().createOrganisation(Type.DOCTOR);
        h1_4.getOrganizationDirectory().createOrganisation(Type.AMBULANCE);
        
        Doctor d5_n1=new Doctor();
        d5_n1.setName("SHDoc1");
        d5_n1.setDoctorsAvailablityStatus(true);
        d5_n1.setDoctorsSpeciality("Heart attack");
        
        Doctor d6_n1=new Doctor();
        d6_n1.setName("SHDoc2");
        d6_n1.setDoctorsAvailablityStatus(true);
        d6_n1.setDoctorsSpeciality("Asthama attack");
        
         for(Organisation org: h1_4.getOrganizationDirectory().getOrganisationList())
       {
           if(org instanceof DoctorOrganization)
           {
               ((DoctorOrganization)org).getEmployeeDirectory().createEmployee(d5_n1);
               ((DoctorOrganization)org).getEmployeeDirectory().createEmployee(d6_n1);
               
               ((DoctorOrganization)org).getUserAccountDirectory().createUserAccount("shdoc1", "shdoc1", d5_n1, new DoctorRole());
               ((DoctorOrganization)org).getUserAccountDirectory().createUserAccount("shdoc2", "shdoc2", d6_n1, new DoctorRole());
               
        
           }
           
       }
         
         
        h1_5.getOrganizationDirectory().createOrganisation(Type.DOCTOR);
        h1_5.getOrganizationDirectory().createOrganisation(Type.AMBULANCE);
         
        Doctor d7_n1=new Doctor();
        d7_n1.setName("FEDoc1");
        d7_n1.setDoctorsAvailablityStatus(true);
        d7_n1.setDoctorsSpeciality("Asthama attack");
        
        Doctor d8_n1=new Doctor();
        d8_n1.setName("FEDoc2");
        d8_n1.setDoctorsAvailablityStatus(true);
        d8_n1.setDoctorsSpeciality("Heart attack");
        
         for(Organisation org: h1_5.getOrganizationDirectory().getOrganisationList())
       {
           if(org instanceof DoctorOrganization)
           {
                
               ((DoctorOrganization)org).getEmployeeDirectory().createEmployee(d7_n1);
               ((DoctorOrganization)org).getEmployeeDirectory().createEmployee(d8_n1);
               
                
               ((DoctorOrganization)org).getUserAccountDirectory().createUserAccount("fedoc1", "fedoc1", d7_n1, new DoctorRole());
               ((DoctorOrganization)org).getUserAccountDirectory().createUserAccount("fedoc2", "fedoc2", d8_n1, new DoctorRole());

           }
       }
       
        Ambulance a1_n1=new Ambulance();
        a1_n1.setName("BRAm1");
        a1_n1.setAvailability("Available");
        
        Ambulance a2_n1=new Ambulance();
        a2_n1.setName("BRAm2");
        a2_n1.setAvailability("Available");
        
         Ambulance a9_n1=new Ambulance();
        a9_n1.setName("BRAm3");
        a9_n1.setAvailability("Available");
        
        Ambulance a10_n1=new Ambulance();
        a10_n1.setName("BRAm4");
        a10_n1.setAvailability("Available");
        
        
    
       for(Organisation org: h1_2.getOrganizationDirectory().getOrganisationList())
       {
           if(org instanceof AmbulanceOrganisation)
           {
               ((AmbulanceOrganisation)org).getEmployeeDirectory().createEmployee(a1_n1);
               ((AmbulanceOrganisation)org).getEmployeeDirectory().createEmployee(a2_n1);
               
               ((AmbulanceOrganisation)org).getEmployeeDirectory().createEmployee(a9_n1);
               ((AmbulanceOrganisation)org).getEmployeeDirectory().createEmployee(a10_n1);
               
               ((AmbulanceOrganisation)org).getUserAccountDirectory().createUserAccount("bram1", "bram1", a1_n1, new AmbulanceRole());
               ((AmbulanceOrganisation)org).getUserAccountDirectory().createUserAccount("bram2", "bram2", a2_n1, new AmbulanceRole());
               ((AmbulanceOrganisation)org).getUserAccountDirectory().createUserAccount("bram3", "bram3", a9_n1, new AmbulanceRole());
               ((AmbulanceOrganisation)org).getUserAccountDirectory().createUserAccount("bram4", "bram4", a10_n1, new AmbulanceRole());
               

           }
       }
        Ambulance a3_n1=new Ambulance();
        a3_n1.setName("BEAm1");
        a3_n1.setAvailability("Available");
        
        
        Ambulance a4_n1=new Ambulance();
        a4_n1.setName("BEAm2");
        a4_n1.setAvailability("Available");
        
        Ambulance a11_n1=new Ambulance();
        a11_n1.setName("BEAm3");
        a11_n1.setAvailability("Available");
        
        
        Ambulance a12_n1=new Ambulance();
        a12_n1.setName("BEAm4");
        a12_n1.setAvailability("Available");
        
        for(Organisation org: h1_3.getOrganizationDirectory().getOrganisationList())
       {
           if(org instanceof AmbulanceOrganisation)
           {
               ((AmbulanceOrganisation)org).getEmployeeDirectory().createEmployee(a3_n1);
               ((AmbulanceOrganisation)org).getEmployeeDirectory().createEmployee(a4_n1);
               ((AmbulanceOrganisation)org).getEmployeeDirectory().createEmployee(a11_n1);
               ((AmbulanceOrganisation)org).getEmployeeDirectory().createEmployee(a12_n1);
               
               ((AmbulanceOrganisation)org).getUserAccountDirectory().createUserAccount("beam1", "beam1", a3_n1, new AmbulanceRole());
               ((AmbulanceOrganisation)org).getUserAccountDirectory().createUserAccount("beam2", "beam2", a4_n1, new AmbulanceRole());
                ((AmbulanceOrganisation)org).getUserAccountDirectory().createUserAccount("beam3", "beam3", a11_n1, new AmbulanceRole());
               ((AmbulanceOrganisation)org).getUserAccountDirectory().createUserAccount("beam4", "beam4", a12_n1, new AmbulanceRole());
               
           }
           
       }
        
        Ambulance a5_n1=new Ambulance();
        a5_n1.setName("SHAm1");
        a5_n1.setAvailability("Available");
        
        
        Ambulance a6_n1=new Ambulance();
        a6_n1.setName("SHAm2");
        a6_n1.setAvailability("Available");
        
         for(Organisation org: h1_4.getOrganizationDirectory().getOrganisationList())
       {
           if(org instanceof AmbulanceOrganisation)
           {
               ((AmbulanceOrganisation)org).getEmployeeDirectory().createEmployee(a5_n1);
               ((AmbulanceOrganisation)org).getEmployeeDirectory().createEmployee(a6_n1);
               
               ((AmbulanceOrganisation)org).getUserAccountDirectory().createUserAccount("sham1", "sham1", a5_n1, new AmbulanceRole());
               ((AmbulanceOrganisation)org).getUserAccountDirectory().createUserAccount("sham2", "sham2", a6_n1, new AmbulanceRole());
           }
           
       }
         
            
        Ambulance a7_n1=new Ambulance();
        a7_n1.setName("FEAm1");
        a7_n1.setAvailability("Available");
        
        
        Ambulance a8_n1=new Ambulance();
        a8_n1.setName("FEAm2");
        a8_n1.setAvailability("Available");
        
        
          for(Organisation org: h1_5.getOrganizationDirectory().getOrganisationList())
         {
           if(org instanceof AmbulanceOrganisation)
           {
               ((AmbulanceOrganisation)org).getEmployeeDirectory().createEmployee(a7_n1);
               ((AmbulanceOrganisation)org).getEmployeeDirectory().createEmployee(a8_n1);
               
               ((AmbulanceOrganisation)org).getUserAccountDirectory().createUserAccount("feam1", "feam1", a7_n1, new AmbulanceRole());
               ((AmbulanceOrganisation)org).getUserAccountDirectory().createUserAccount("feam2", "feam2", a8_n1, new AmbulanceRole());

           }
           
        }
  
        //creating drones in the network
        Organisation droneOrg_n1=null;
         for (Type type : Organisation.Type.values())
         {
            if (type.getValue().equals(Type.DRONE.getValue()))
            {
          
                droneOrg_n1=((Emergency911Enterprise)(enterprisen2_2)).getOrganizationDirectory().createOrganisation(type);
            
            }
         }
         
         //creating drone stations in bostonpsap enterprise
        DroneStation bostonDroneStation1=null;
        DroneStation bostonDroneStation2=null;
        DroneStation bostonDroneStation3=null;
        for(Organisation org:((Emergency911Enterprise)enterprisen2_2).getOrganizationDirectory().getOrganisationList())
        {
            if(org instanceof DroneOrganisation)
            {
                bostonDroneStation1=((DroneOrganisation)org).getDroneDirectoryObject().addDroneStation();
                bostonDroneStation1.setDroneStationName("Boston Drone Station 1");
                bostonDroneStation1.setDroneStationAddress("68 William Cardinal O'Connell Way, Boston, MA 02114");
       
                 bostonDroneStation2=((DroneOrganisation)org).getDroneDirectoryObject().addDroneStation();
                 bostonDroneStation2.setDroneStationName("Boston Drone Station 2");
                 bostonDroneStation2.setDroneStationAddress("4 Yawkey Way, Boston, MA 02215");
            
                 bostonDroneStation3=((DroneOrganisation)org).getDroneDirectoryObject().addDroneStation();
                 bostonDroneStation3.setDroneStationName("Boston Drone Station 3");
                 bostonDroneStation3.setDroneStationAddress("300 Fenway, Boston, MA 02115");
            }
        }
        
        //adding drones to drone station
        Drone d1n1=new Drone();
        d1n1.setDroneId("Drone_Boston100");
        d1n1.setStatus("Active");
        bostonDroneStation1.addDrone(d1n1);
       
        droneOrg_n1.getEmployeeDirectory().createEmployee(d1n1);
        droneOrg_n1.getUserAccountDirectory().createUserAccount("dronebos100", "dronebos100", d1n1, new DroneRole() );
      

        Drone d2n1= new Drone();
        d2n1.setDroneId("Drone_Boston200");
        d2n1.setStatus("Assigned");
        bostonDroneStation1.addDrone(d2n1);
        droneOrg_n1.getEmployeeDirectory().createEmployee(d2n1);
        droneOrg_n1.getUserAccountDirectory().createUserAccount("dronebos200", "dronebos200", d2n1, new DroneRole() );
      
        
        Drone d3n1= new Drone();
        d3n1.setDroneId("Drone_Boston300");
        d3n1.setStatus("Active");
        bostonDroneStation1.addDrone(d3n1);
        droneOrg_n1.getEmployeeDirectory().createEmployee(d3n1);
        droneOrg_n1.getUserAccountDirectory().createUserAccount("dronebos300", "dronebos300", d3n1, new DroneRole() );
      
        
        Drone d4n1= new Drone();
        d4n1.setDroneId("Drone_Boston400");
        d4n1.setStatus("Assigned");
        bostonDroneStation1.addDrone(d4n1);
        droneOrg_n1.getEmployeeDirectory().createEmployee(d4n1);
        droneOrg_n1.getUserAccountDirectory().createUserAccount("dronebos400", "dronebos400", d4n1, new DroneRole() );
      
        
        Drone d5n1=new Drone();
        d5n1.setDroneId("Drone_Boston500");
        d5n1.setStatus("Active");
        bostonDroneStation1.addDrone(d5n1);
        droneOrg_n1.getEmployeeDirectory().createEmployee(d5n1);
        droneOrg_n1.getUserAccountDirectory().createUserAccount("dronebos500", "dronebos500", d5n1, new DroneRole() );

       
        Drone d6n1=new Drone();
              
        d6n1.setDroneId("Drone_Boston600");
        d6n1.setStatus("Active");
        bostonDroneStation2.addDrone(d6n1);
        droneOrg_n1.getEmployeeDirectory().createEmployee(d6n1);
        droneOrg_n1.getUserAccountDirectory().createUserAccount("dronebos600", "dronebos600", d6n1, new DroneRole() );
      
        Drone d7n1=new Drone();
        d7n1.setDroneId("Drone_Boston700");
        d7n1.setStatus("Active");
        bostonDroneStation2.addDrone(d7n1);
        droneOrg_n1.getEmployeeDirectory().createEmployee(d7n1);
        droneOrg_n1.getUserAccountDirectory().createUserAccount("dronebos700", "dronebos700", d7n1, new DroneRole() );
      
        
        
        Drone d8n1= new Drone();
        d8n1.setDroneId("Drone_Boston800");
        d8n1.setStatus("Active");
        bostonDroneStation2.addDrone(d8n1);
        droneOrg_n1.getEmployeeDirectory().createEmployee(d8n1);
        droneOrg_n1.getUserAccountDirectory().createUserAccount("dronebos800", "dronebos800", d8n1, new DroneRole() );
      
        
        
        Drone d9n1=new Drone();
        d9n1.setDroneId("Drone_Boston900");
        d9n1.setStatus("Active");
        bostonDroneStation3.addDrone(d9n1);
        droneOrg_n1.getEmployeeDirectory().createEmployee(d9n1);
        droneOrg_n1.getUserAccountDirectory().createUserAccount("dronebos900", "dronebos900", d9n1, new DroneRole() );
      
        
        Drone d10n1= new Drone();
        d10n1.setDroneId("Drone_Boston1000");
        d10n1.setStatus("Active");
        bostonDroneStation3.addDrone(d10n1);
        droneOrg_n1.getEmployeeDirectory().createEmployee(d10n1);
        droneOrg_n1.getUserAccountDirectory().createUserAccount("dronebos1000", "dronebos1000", d10n1, new DroneRole() );
      
        
        Drone d11n1= new Drone();
        d11n1.setDroneId("Drone_Boston1100");
        d11n1.setStatus("Active");
        bostonDroneStation3.addDrone(d11n1);
        droneOrg_n1.getEmployeeDirectory().createEmployee(d11n1);
        droneOrg_n1.getUserAccountDirectory().createUserAccount("dronebos1100", "dronebos1100", d11n1, new DroneRole() );
      
      
        Drone d12n1= new Drone();
        d12n1.setDroneId("Drone_Boston1200");
        d12n1.setStatus("Active");
        bostonDroneStation3.addDrone(d12n1);
        droneOrg_n1.getEmployeeDirectory().createEmployee(d12n1);
        droneOrg_n1.getUserAccountDirectory().createUserAccount("dronebos1200", "dronebos1200", d12n1, new DroneRole() );
     
        //End of boston network creation
        
        
        //Creating cambridge network
        Network n2=system.addNetwork();
        n2.setNetworkName("Cambridge");
        
        //Creating enterprises in cambridge network

        Enterprise.EnterpriseType type3_n2 = EnterpriseType.EMEREGENCY911ENTERPRISE;
        Enterprise enterprise_n2_2 = n2.getEntDirObj().createAndAddEnterprise("CambridgePSAP",type3_n2,"1350 Massachusetts Avenue. Cambridge, MA 02138" );
      
        Enterprise.EnterpriseType type4_n2 = EnterpriseType.POLICEENTERPRISE;
        Enterprise enterprisen2_4 = n2.getEntDirObj().createAndAddEnterprise("CambridgePolice",type4_n2,"" );
        //creating cambridge enterprise admin 
        EnterpriseAdmin cambridgepsapAdmin=new EnterpriseAdmin();
        enterprise_n2_2.getEmployeeDirectory().createEmployee(cambridgepsapAdmin);
        enterprise_n2_2.getUserAccountDirectory().createUserAccount("cambridgepsap", "cambridgepsap", cambridgepsapAdmin, new Emergency911EnterpriseAdminRole());
        
        EnterpriseAdmin cambridgePoliceAdmin=new EnterpriseAdmin();
        enterprisen2_4.getEmployeeDirectory().createEmployee(cambridgePoliceAdmin);
        enterprisen2_4.getUserAccountDirectory().createUserAccount("campoliceadmin", "campoliceadmin", cambridgePoliceAdmin, new PoliceAdminRole());
        cambridgePoliceAdmin.setAvailable(true);

      //hospital creation  
      
        Hospital h2_1=n2.addHospital("CHA Cambridge Hospital campus");
        h2_1.setHospitalName("CHA Cambridge Hospital campus");
        h2_1.setHospitalLocation("1493 Cambridge St, Cambridge, MA 02139");
        h2_1.setSpeciality("Neurology");
        h2_1.setNumberOfBeds(50);
        h2_1.setNumberOfEmptyBeds(10);
        
        Hospital h2_2=n2.addHospital("Spaulding Hospital Cambridge");
        h2_2.setHospitalName("Spaulding Hospital Cambridge");
        h2_2.setHospitalLocation("1575 Cambridge St, Cambridge, MA 02138");
        h2_2.setSpeciality("Cardiothoracic");
        h2_2.setNumberOfBeds(50);
        h2_2.setNumberOfEmptyBeds(5);
        
        
        Hospital h2_3=n2.addHospital("Mount Auburn Hospital: Walk-In Center");
        h2_3.setHospitalName("Mount Auburn Hospital: Walk-In Center");
        h2_3.setHospitalLocation("330 Mt Auburn St, Cambridge, MA 02138");
        h2_3.setSpeciality("Plastics");
        h2_3.setNumberOfBeds(50);
        h2_3.setNumberOfEmptyBeds(10);
        
       //creatinh hospital admins
        
        HospitalEnterpriseAdmin h1n2=new HospitalEnterpriseAdmin();
        h1n2.setName("CHACambridgeAdmin");
        h2_1.getEmployeeDirectory().createEmployee(h1n2);
        h2_1.getUserAccountDirectory().createUserAccount("chacamadmin", "chacamadmin", h1n2, new HospitalEnterpriseAdminRole());
         
        HospitalEnterpriseAdmin h2n2=new HospitalEnterpriseAdmin();
        h2n2.setName("SpauldingAdmin");
        h2_2.getEmployeeDirectory().createEmployee(h2n2);
        h2_2.getUserAccountDirectory().createUserAccount("spauldingadmin", "spauldingadmin", h2n2, new HospitalEnterpriseAdminRole());
         
        HospitalEnterpriseAdmin h3n2=new HospitalEnterpriseAdmin();
        h3n2.setName("MountAdmin");
        h2_3.getEmployeeDirectory().createEmployee(h3n2);
        h2_3.getUserAccountDirectory().createUserAccount("mountadmin", "mountadmin", h3n2, new HospitalEnterpriseAdminRole());
         
         
        //creating doctor employees and ambulance employees
       
        h2_1.getOrganizationDirectory().createOrganisation(Type.DOCTOR);
        h2_1.getOrganizationDirectory().createOrganisation(Type.AMBULANCE);
        Doctor d1_n2=new Doctor();
        d1_n2.setName("CHACAMDoc1");
        d1_n2.setDoctorsAvailablityStatus(true);
        d1_n2.setDoctorsSpeciality("Car accident-Head Injury");
        
        Doctor d2_n2=new Doctor();
        d2_n2.setName("CHACAMDoc2");
        d2_n2.setDoctorsAvailablityStatus(true);
        d2_n2.setDoctorsSpeciality("Heart attack");
       
        
       for(Organisation org: h2_1.getOrganizationDirectory().getOrganisationList())
       {
           if(org instanceof DoctorOrganization)
           {
               ((DoctorOrganization)org).getEmployeeDirectory().createEmployee(d1_n2);
               ((DoctorOrganization)org).getEmployeeDirectory().createEmployee(d2_n2);
              
              
               
               ((DoctorOrganization)org).getUserAccountDirectory().createUserAccount("chacamdoc1", "chacamdoc1", d1_n2, new DoctorRole());
               ((DoctorOrganization)org).getUserAccountDirectory().createUserAccount("chacamdoc2", "chacamdoc2", d2_n2, new DoctorRole());
               
              
               
           }
       }
       
       
        h2_2.getOrganizationDirectory().createOrganisation(Type.DOCTOR);
        h2_2.getOrganizationDirectory().createOrganisation(Type.AMBULANCE);
        Doctor d3_n2=new Doctor();
        d3_n2.setName("SPDoc1");
        d3_n2.setDoctorsAvailablityStatus(true);
        d3_n2.setDoctorsSpeciality("Heart attack");
        
        Doctor d4_n2=new Doctor();
        d4_n2.setName("SPDoc2");
        d4_n2.setDoctorsAvailablityStatus(true);
        d4_n2.setDoctorsSpeciality("Fire");
       
       for(Organisation org: h2_2.getOrganizationDirectory().getOrganisationList())
       {
           if(org instanceof DoctorOrganization)
           {
               ((DoctorOrganization)org).getEmployeeDirectory().createEmployee(d3_n2);
               ((DoctorOrganization)org).getEmployeeDirectory().createEmployee(d4_n2);
               
               ((DoctorOrganization)org).getUserAccountDirectory().createUserAccount("spdoc1", "spdoc1", d3_n2, new DoctorRole());
               ((DoctorOrganization)org).getUserAccountDirectory().createUserAccount("spdoc2", "spdoc2", d4_n2, new DoctorRole());
           }
       }
           
        h2_3.getOrganizationDirectory().createOrganisation(Type.DOCTOR);
        h2_3.getOrganizationDirectory().createOrganisation(Type.AMBULANCE);
        
        Doctor d5_n2=new Doctor();
        d5_n2.setName("MODoc1");
        d5_n2.setDoctorsAvailablityStatus(true);
        d5_n2.setDoctorsSpeciality("Fire");
      
        
        Doctor d6_n2=new Doctor();
        d6_n2.setName("MODoc2");
        d6_n2.setDoctorsAvailablityStatus(true);
        d6_n2.setDoctorsSpeciality("Car accident-Head Injury");
        
        for(Organisation org: h2_3.getOrganizationDirectory().getOrganisationList())
       {
           if(org instanceof DoctorOrganization)
           {
               ((DoctorOrganization)org).getEmployeeDirectory().createEmployee(d5_n2);
               ((DoctorOrganization)org).getEmployeeDirectory().createEmployee(d6_n2);
               
               ((DoctorOrganization)org).getUserAccountDirectory().createUserAccount("modoc1", "modoc1", d5_n2, new DoctorRole());
               ((DoctorOrganization)org).getUserAccountDirectory().createUserAccount("modoc2", "modoc2", d6_n2, new DoctorRole());

           }
       }
      
        Ambulance a1_n2=new Ambulance();
        a1_n2.setName("CHACAMAm1");
        a1_n2.setAvailability("Available");
        
        
        Ambulance a2_n2=new Ambulance();
        a2_n2.setName("CHACAMAm2");
        a2_n2.setAvailability("Available");
        
 
       for(Organisation org: h2_1.getOrganizationDirectory().getOrganisationList())
       {
           if(org instanceof AmbulanceOrganisation)
           {
               ((AmbulanceOrganisation)org).getEmployeeDirectory().createEmployee(a1_n2);
               ((AmbulanceOrganisation)org).getEmployeeDirectory().createEmployee(a2_n2);
               
               ((AmbulanceOrganisation)org).getUserAccountDirectory().createUserAccount("chacamam1", "chacamam1", a1_n2, new AmbulanceRole());
               ((AmbulanceOrganisation)org).getUserAccountDirectory().createUserAccount("chacamam2", "chacamam2", a2_n2, new AmbulanceRole());

           }
       }
       
        Ambulance a3_n2=new Ambulance();
        a3_n2.setName("SPAm1");
        a3_n2.setAvailability("Available");
        
        
        Ambulance a4_n2=new Ambulance();
        a4_n2.setName("SPAm2");
        a4_n2.setAvailability("Available");
        
        
       for(Organisation org: h2_2.getOrganizationDirectory().getOrganisationList())
       {
           if(org instanceof AmbulanceOrganisation)
           {
               ((AmbulanceOrganisation)org).getEmployeeDirectory().createEmployee(a3_n2);
               ((AmbulanceOrganisation)org).getEmployeeDirectory().createEmployee(a4_n2);
               
               ((AmbulanceOrganisation)org).getUserAccountDirectory().createUserAccount("spam1", "spam1", a1_n1, new AmbulanceRole());
               ((AmbulanceOrganisation)org).getUserAccountDirectory().createUserAccount("spam2", "spam2", a2_n1, new AmbulanceRole());

           }
       }
        
         Ambulance a5_n2=new Ambulance();
        a5_n2.setName("MOAm1");
        a5_n2.setAvailability("Available");
        
        
        Ambulance a6_n2=new Ambulance();
        a6_n2.setName("MOAm2");
        a6_n2.setAvailability("Available");
        
        
       for(Organisation org: h2_3.getOrganizationDirectory().getOrganisationList())
       {
           if(org instanceof AmbulanceOrganisation)
           {
               ((AmbulanceOrganisation)org).getEmployeeDirectory().createEmployee(a5_n2);
               ((AmbulanceOrganisation)org).getEmployeeDirectory().createEmployee(a6_n2);
               
               ((AmbulanceOrganisation)org).getUserAccountDirectory().createUserAccount("moam1", "moam1", a5_n2, new AmbulanceRole());
               ((AmbulanceOrganisation)org).getUserAccountDirectory().createUserAccount("moam2", "moam2", a6_n2, new AmbulanceRole());

           }
       }
        
        
        
        
        //drone creation
         Organisation droneOrg_n2=null;
         for (Type type : Organisation.Type.values())
         {
            if (type.getValue().equals(Type.DRONE.getValue()))
            {
            droneOrg_n2=((Emergency911Enterprise)(enterprise_n2_2)).getOrganizationDirectory().createOrganisation(type);
            }
        }
         
       //creating cambridge drone stations  
       DroneStation cambridgeDroneStation1=null;
        
        for(Organisation org:((Emergency911Enterprise)enterprise_n2_2).getOrganizationDirectory().getOrganisationList())
        {
            if(org instanceof DroneOrganisation)
            {
                cambridgeDroneStation1=((DroneOrganisation)org).getDroneDirectoryObject().addDroneStation();
                cambridgeDroneStation1.setDroneStationName("Cambridge Drone Station 1");
                cambridgeDroneStation1.setDroneStationAddress("1350 Massachusetts Avenue. Cambridge, MA 02138 ");
                 
            }
        }
        
      
      
        //adding drones to cambridge drone station
        Drone d1n2=new Drone();
        
        d1n2.setDroneId("Drone_Cambrideg100");
        d1n2.setStatus("Active");
        cambridgeDroneStation1.addDrone(d1n2);
        droneOrg_n2.getEmployeeDirectory().createEmployee(d1n2);
        droneOrg_n2.getUserAccountDirectory().createUserAccount("dronecam100", "dronecam100", d1n2, new DroneRole() );
     
        
        Drone d2n2= new Drone();
        d2n2.setDroneId("Drone_Cambridge200");
        d2n2.setStatus("Active");
        cambridgeDroneStation1.addDrone(d2n2);
        droneOrg_n2.getEmployeeDirectory().createEmployee(d2n2);
        droneOrg_n2.getUserAccountDirectory().createUserAccount("dronecam200", "dronecam200", d2n2, new DroneRole() );
     
        
        Drone d3n2= new Drone();
        d3n2.setDroneId("Drone_Cambridge300");
        d3n2.setStatus("Active");
         cambridgeDroneStation1.addDrone(d3n2);
        droneOrg_n2.getEmployeeDirectory().createEmployee(d3n2);
        droneOrg_n2.getUserAccountDirectory().createUserAccount("dronecam300", "dronecam300", d3n2, new DroneRole() );
     
        
        Drone d4n2=new Drone();
        d4n2.setDroneId("Drone_Cambridge400");
        d4n2.setStatus("Active");
        cambridgeDroneStation1.addDrone(d4n2);
        droneOrg_n2.getEmployeeDirectory().createEmployee(d4n2);
        droneOrg_n2.getUserAccountDirectory().createUserAccount("dronecam400", "dronecam400", d4n2, new DroneRole() );
     
        
        Drone d5n2= new Drone();
        d5n2.setDroneId("Drone_Cambridge500");
        d5n2.setStatus("Active");
        cambridgeDroneStation1.addDrone(d5n2);
        droneOrg_n2.getEmployeeDirectory().createEmployee(d5n2);
        droneOrg_n2.getUserAccountDirectory().createUserAccount("dronecam500", "dronecam500", d5n2, new DroneRole() );
     
        
       //end of cambridge network creation
        
        
        //Creating malden network
        Network n3=system.addNetwork();
        n3.setNetworkName("Malden");
        
        //creating enterprises in malden network

        Enterprise.EnterpriseType type3_n4 = EnterpriseType.EMEREGENCY911ENTERPRISE;
        Enterprise enterprise_n3_2 = n3.getEntDirObj().createAndAddEnterprise("MaldenPSAP",type3_n4,"77 Salem St, Malden, MA 02148" );
     
        Enterprise.EnterpriseType type2_n5 = EnterpriseType.POLICEENTERPRISE;
        Enterprise enterprisen2_5 = n3.getEntDirObj().createAndAddEnterprise("MaldenPolice",type2_n5,"" );
        //creating enterprise admin for malden
        EnterpriseAdmin maldenpsapAdmin=new EnterpriseAdmin();
        enterprise_n3_2.getEmployeeDirectory().createEmployee(maldenpsapAdmin);
        enterprise_n3_2.getUserAccountDirectory().createUserAccount("maldenpsap", "maldenpsap", maldenpsapAdmin, new Emergency911EnterpriseAdminRole());
       
        EnterpriseAdmin maldenPoliceAdmin=new EnterpriseAdmin();
        enterprisen2_5.getEmployeeDirectory().createEmployee(maldenPoliceAdmin);
        enterprisen2_5.getUserAccountDirectory().createUserAccount("malpoliceadmin", "malpoliceadmin", maldenPoliceAdmin, new PoliceAdminRole());
        maldenPoliceAdmin.setAvailable(true);
  
        //hospital creation
        
        Hospital h3_1=n3.addHospital("AFC Doctors Express Urgent Care Malden");
        h3_1.setHospitalName("AFC Doctors Express Urgent Care Malden");
        h3_1.setHospitalLocation("219 Centre St, Malden, MA 02148");
        h3_1.setSpeciality("Cardiothoracic");
        h3_1.setNumberOfBeds(50);
        h3_1.setNumberOfEmptyBeds(5);
      
        
        Hospital h3_3=n3.addHospital("CHA Malden Family Medicine Center");
        h3_3.setHospitalName("CHA Malden Family Medicine Center");
        h3_3.setHospitalLocation("195 Canal St, Malden, MA 02148");
        h3_3.setSpeciality("Orthopedic");
        h3_3.setNumberOfBeds(50);
        h3_3.setNumberOfEmptyBeds(5);
       
        //hospital admin creation
        
        HospitalEnterpriseAdmin h1n3=new HospitalEnterpriseAdmin();
        h1n3.setName("AFCAdmin");
        h3_1.getEmployeeDirectory().createEmployee(h1n3);
        h3_1.getUserAccountDirectory().createUserAccount("afcadmin", "afcadmin", h1n3, new HospitalEnterpriseAdminRole());
         
        HospitalEnterpriseAdmin h2n3=new HospitalEnterpriseAdmin();
        h2n2.setName("CHAMaldenAdmin");
        h3_3.getEmployeeDirectory().createEmployee(h2n2);
        h3_3.getUserAccountDirectory().createUserAccount("chamaladmin", "chamaladmin", h2n2, new HospitalEnterpriseAdminRole());
         
        //creating doctors and ambulances in cambridge network
        h3_1.getOrganizationDirectory().createOrganisation(Type.DOCTOR);
        h3_1.getOrganizationDirectory().createOrganisation(Type.AMBULANCE);
        Doctor d1_n3=new Doctor();
        d1_n3.setName("AFCMADoc1");
        d1_n3.setDoctorsAvailablityStatus(true);
        d1_n3.setDoctorsSpeciality("Heart attack");
        
        Doctor d2_n3=new Doctor();
        d2_n3.setName("AFCMADoc2");
        d2_n3.setDoctorsAvailablityStatus(true);
        d2_n3.setDoctorsSpeciality("Car accident-Body Injury");
       
        
       for(Organisation org: h3_1.getOrganizationDirectory().getOrganisationList())
       {
           if(org instanceof DoctorOrganization)
           {
               ((DoctorOrganization)org).getEmployeeDirectory().createEmployee(d1_n3);
               ((DoctorOrganization)org).getEmployeeDirectory().createEmployee(d2_n3);

               ((DoctorOrganization)org).getUserAccountDirectory().createUserAccount("afcmadoc1", "afcmadoc1", d1_n3, new DoctorRole());
               ((DoctorOrganization)org).getUserAccountDirectory().createUserAccount("afcmadoc2", "afcmadoc2", d2_n3, new DoctorRole());
               
              
               
           }
       }
        h3_3.getOrganizationDirectory().createOrganisation(Type.DOCTOR);
        h3_3.getOrganizationDirectory().createOrganisation(Type.AMBULANCE);
       Doctor d3_n3=new Doctor();
        d3_n3.setName("CHAMADoc1");
        d3_n3.setDoctorsAvailablityStatus(true);
        d3_n3.setDoctorsSpeciality("Heart attack");
        
        Doctor d4_n3=new Doctor();
        d4_n3.setName("CHAMADoc2");
        d4_n3.setDoctorsAvailablityStatus(true);
        d4_n3.setDoctorsSpeciality("Car accident-Body Injury");
       
        
       for(Organisation org: h3_3.getOrganizationDirectory().getOrganisationList())
       {
           if(org instanceof DoctorOrganization)
           {
               ((DoctorOrganization)org).getEmployeeDirectory().createEmployee(d1_n3);
               ((DoctorOrganization)org).getEmployeeDirectory().createEmployee(d2_n3);
  
               ((DoctorOrganization)org).getUserAccountDirectory().createUserAccount("chamadoc1", "chamadoc1", d1_n3, new DoctorRole());
               ((DoctorOrganization)org).getUserAccountDirectory().createUserAccount("chamadoc2", "chamadoc2", d2_n3, new DoctorRole());
               
              
               
           }
       }
       
        Ambulance a1_n3=new Ambulance();
        a1_n3.setName("AFCAm1");
        a1_n3.setAvailability("Available");
        
        
        Ambulance a2_n3=new Ambulance();
        a2_n3.setName("AFCAm2");
        a2_n3.setAvailability("Available");
        
       for(Organisation org: h3_1.getOrganizationDirectory().getOrganisationList())
       {
           if(org instanceof AmbulanceOrganisation)
           {
               ((AmbulanceOrganisation)org).getEmployeeDirectory().createEmployee(a1_n3);
               ((AmbulanceOrganisation)org).getEmployeeDirectory().createEmployee(a1_n3);
               
               ((AmbulanceOrganisation)org).getUserAccountDirectory().createUserAccount("afcam1", "afcam1", a1_n3, new AmbulanceRole());
               ((AmbulanceOrganisation)org).getUserAccountDirectory().createUserAccount("afcam2", "afcam2", a1_n3, new AmbulanceRole());

           }
       }
        
       
        Ambulance a3_n3=new Ambulance();
        a3_n2.setName("CHAMALAm1");
        a3_n2.setAvailability("Available");
        
        
        Ambulance a4_n3=new Ambulance();
        a4_n3.setName("CHAMALAm2");
        a4_n3.setAvailability("Available");
        
        
       for(Organisation org: h3_3.getOrganizationDirectory().getOrganisationList())
       {
           if(org instanceof AmbulanceOrganisation)
           {
               ((AmbulanceOrganisation)org).getEmployeeDirectory().createEmployee(a5_n2);
               ((AmbulanceOrganisation)org).getEmployeeDirectory().createEmployee(a6_n2);
               
               ((AmbulanceOrganisation)org).getUserAccountDirectory().createUserAccount("chamalam1", "chamalam1", a5_n2, new AmbulanceRole());
               ((AmbulanceOrganisation)org).getUserAccountDirectory().createUserAccount("chamalam2", "chamalam2", a6_n2, new AmbulanceRole());

           }
       }
        
    
       //drone creation
        Organisation droneOrg_n3=null;
         for (Type type : Organisation.Type.values())
         {
            if (type.getValue().equals(Type.DRONE.getValue()))
            {
            droneOrg_n3=((Emergency911Enterprise)(enterprise_n3_2)).getOrganizationDirectory().createOrganisation(type);
            }
        }
         
     
         //drone station creation in malden
         DroneStation maldenDroneStation1=null;
        
        for(Organisation org:((Emergency911Enterprise)enterprise_n3_2).getOrganizationDirectory().getOrganisationList())
        {
            if(org instanceof DroneOrganisation)
            {
                maldenDroneStation1=((DroneOrganisation)org).getDroneDirectoryObject().addDroneStation();
                maldenDroneStation1.setDroneStationName("Malden Drone Station 1");
                maldenDroneStation1.setDroneStationAddress("77 Salem St, Malden, MA 02148");
            }
        }
         
         
        //adding drones to malden drone station
        Drone d1n3=new Drone();     
        d1n3.setDroneId("Drone_Malden100");
        d1n3.setStatus("Active");
        maldenDroneStation1.addDrone(d1n3);
        droneOrg_n3.getEmployeeDirectory().createEmployee(d1n3);
        droneOrg_n3.getUserAccountDirectory().createUserAccount("dronemal100", "dronemal100", d1n3, new DroneRole() );
     
        
        Drone d2n3= new Drone();
        d2n3.setDroneId("Drone_Malden200");
        d2n3.setStatus("Active");
        maldenDroneStation1.addDrone(d2n3);
        droneOrg_n3.getEmployeeDirectory().createEmployee(d2n3);
        droneOrg_n3.getUserAccountDirectory().createUserAccount("dronemal200", "dronemal200", d2n3, new DroneRole() );
     
        
        Drone d3n3= new Drone();
        d3n3.setDroneId("Drone_Malden300");
        d3n3.setStatus("Active");
        maldenDroneStation1.addDrone(d1n3);
        droneOrg_n3.getEmployeeDirectory().createEmployee(d3n3);
        droneOrg_n3.getUserAccountDirectory().createUserAccount("dronemal300", "dronemal300", d3n3, new DroneRole() );
     
        
        
        Drone d4n3= new Drone();
        d4n3.setDroneId("Drone_Malden400");
        d4n3.setStatus("Active");
        maldenDroneStation1.addDrone(d4n3);
        droneOrg_n3.getEmployeeDirectory().createEmployee(d4n3);
        droneOrg_n3.getUserAccountDirectory().createUserAccount("dronemal400", "dronemal400", d4n3, new DroneRole() );
     
        
        Drone d5n3= new Drone();
        d5n3.setDroneId("Drone_Malden500");
        d5n3.setStatus("Active");
        maldenDroneStation1.addDrone(d5n3);
        droneOrg_n3.getEmployeeDirectory().createEmployee(d5n3);
        droneOrg_n3.getUserAccountDirectory().createUserAccount("dronemal500", "dronemal500", d5n3, new DroneRole() );
     
      
        //end of malden network creation
  
        
        EmergencyAddressLocation e1=system.getDirectory().addEmeregncyLocation();
        e1.setAddress("44 Clearway Street, Boston, MA, 02115");
       
        
        EmergencyAddressLocation e2=system.getDirectory().addEmeregncyLocation();
        e2.setAddress("75 St Alphonsus St, Boston, MA 02120");
      
        
        EmergencyAddressLocation e3=system.getDirectory().addEmeregncyLocation();
        e3.setAddress("1 Arborway, Boston, MA 02130");
        
        
        EmergencyAddressLocation e4=system.getDirectory().addEmeregncyLocation();
        e4.setAddress("Franklin Park Rd, Dorchester, Boston, Massachusetts 02121");
       
        
        EmergencyAddressLocation e5=system.getDirectory().addEmeregncyLocation();
        e5.setAddress("25 Shattuck St, Boston, MA 02115");
       
        
        EmergencyAddressLocation e6=system.getDirectory().addEmeregncyLocation();
        e6.setAddress("55 Fruit St, Boston, MA 02114");
       
       
        
        return system;
 
    }

  
}
