/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Emergency911Department;



import Employee.Drone;
import Drone.DroneStation;
import EmergencySystem.Emergency.Emergency;
import EmergencySystem.EmergencySystem;
import EmergencySystem.Enterprise.Emergency911Enterprise;
import EmergencySystem.Enterprise.Enterprise;
import EmergencySystem.Network.Network;
import Hospital.Organisation.DroneOrganisation;
import Hospital.Organisation.Organisation;
import Hospital.Role.DroneRole;
import Hospital.UserAccount.UserAccount;
import Hospital.WorkQueue.Emergency911DepartmentWorkRequest;
import Hospital.WorkQueue.WorkRequest;
import com.google.gson.Gson;
import com.sl.DistancePojo;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Vasanti
 */
public class Emergency911DepartmentWorkArea extends javax.swing.JPanel {

    /**
     * Creates new form Emergency911DepartmentWorkArea
     */
    private JPanel userProcessContainer;
    private EmergencySystem emergencysystem;
    private UserAccount account; 
    private Network network;
    private Enterprise enterprise;
    private Emergency e;
    private boolean flag=false;
    private boolean enableFlag=false;
    private String location;
    private boolean value=false;
    private boolean droneIdAlreadyPresent;
    private boolean droneUserNameAlreadyPresent;
    private boolean droneStationAlreadyPresent;
        
    private boolean droneHasUserName;
   
    public Emergency911DepartmentWorkArea(JPanel userProcessContainer,UserAccount account, EmergencySystem emergencysystem, Network network, Enterprise enterprise) {
        initComponents();
        this.userProcessContainer=userProcessContainer;
        this.emergencysystem=emergencysystem;
        this.account=account;
        this.network=network;
        this.enterprise=enterprise;
       
        
        displacementTF.setEditable(false);
        tfTime.setEditable(false);
        manipulateFields(flag);
        restrictFields(value);
        populateTable();
        populateDroneStationTab();
        populateDroneStationCombo();
        populateEmergencyCombo();
        
        
      
    }
    
    public void restrictFields(boolean value)
    {
        emergencylocCombo.setEnabled(value);
        calBtn.setEnabled(value);
        showDroneBtn.setEnabled(value);
        showActiveDroneBtn.setEnabled(value);
        assignEmergencyToDroneBtn.setEnabled(value);
    }
    
    
    public void populateDroneStationCombo()
    {
       droneStnCombo.removeAllItems();
        
       
        for (Organisation org:((Emergency911Enterprise)(enterprise)).getOrganizationDirectory().getOrganisationList()){
           
            if(org instanceof DroneOrganisation)
            {
                for(DroneStation ds:((DroneOrganisation)org).getDroneDirectoryObject().getDroneStationList())
                {
                    droneStnCombo.addItem(ds);
                }
            }
            
        } 
    }
    
    public void populateDroneDetailsTable(DroneStation ds)
    {
       DefaultTableModel model = (DefaultTableModel) dronedetailsTable.getModel();
        
        model.setRowCount(0);
        
        for (Drone d:ds.getDroneList()){
            Object[] row = new Object[2];
            row[0] = d;
            row[1] = d.getStatus();
     
            model.addRow(row);
        }  
    }
  
    
    public void manipulateFields(boolean flag)
    {
       
            emergencyLocationLabel.setVisible(flag);
            emergencyLocationTF.setVisible(flag);
            natureOfEmergencyLabel.setVisible(flag);
            natureOfEmergencyTF.setVisible(flag);
            timeRecorderLabel.setVisible(flag);
            timeTF.setVisible(flag);
            desciptionLabel.setVisible(flag);
            descriptionTF.setVisible(flag);
            phoneNumberLabel.setVisible(flag);
            phoneNumberTF.setVisible(flag);
           
           
    }
    
     public void enableFields(boolean enableflag)
    {
        emergencyLocationTF.setEnabled(enableflag);
        natureOfEmergencyTF.setEnabled(enableflag);
        descriptionTF.setEnabled(enableflag);
        phoneNumberTF.setEnabled(enableflag);
    }
    
    
    public void populateTable()
    {
        DefaultTableModel model = (DefaultTableModel) emergencyTable.getModel();
        
        model.setRowCount(0);
        
        for (WorkRequest workRequest:account.getWorkQueue().getWorkRequestList()){
            Object[] row = new Object[4];
            row[0]=  workRequest;
           
            row[1] = ((Emergency911DepartmentWorkRequest) workRequest).getEmergency();
            row[2] = ((Emergency911DepartmentWorkRequest) workRequest).getEmergency().getNatureOfEmergency();
            row[3]= ((Emergency911DepartmentWorkRequest) workRequest).getEmergency().getPriority();
     
            model.addRow(row);
        }
    }
    
   
    
    public void populateEmergencyCombo()
    {
        emergencylocCombo.removeAllItems();
        
        for (WorkRequest workRequest:account.getWorkQueue().getWorkRequestList()){
            emergencylocCombo.addItem(((Emergency911DepartmentWorkRequest) workRequest).getEmergency());
        } 
    }
    public void populateDroneTable(DroneStation dronestation)
    {
        DefaultTableModel model = (DefaultTableModel) droneTable.getModel();
        
        model.setRowCount(0);
        
        for (Drone d:dronestation.getDroneList()){
            Object[] row = new Object[2];
            row[0] = d;
            row[1] = d.getStatus();
     
            model.addRow(row);
        }
      
    }
    
    public void populateDroneStationTab()
    {
   
       
        DefaultTableModel model = (DefaultTableModel) dsTable.getModel();
        model.setRowCount(0);
        for (Organisation org:((Emergency911Enterprise)enterprise).getOrganizationDirectory().getOrganisationList()){
             if(org instanceof DroneOrganisation)
             {
                 for(DroneStation ds:((DroneOrganisation)org).getDroneDirectoryObject().getDroneStationList())
                 {
                     Object[] row = new Object[2];
                     row[0] = ds;
                     row[1] = ds.getDroneStationAddress();
                     model.addRow(row);
                 }
             }
     
        }
        
    }
    
    public void populateDroneStationTable()
    {
       
        DefaultTableModel model = (DefaultTableModel) droneStationTable.getModel();
        
        model.setRowCount(0);
        
        
        Emergency emerg=  (Emergency) emergencylocCombo.getSelectedItem();
         
        if (emerg != null){
            try
            {
            location=emerg.getLocationOfEmergency().replaceAll("\\s","");
            URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?origins="+location+"&destinations=24+Beacon+St,+Boston,+MA+01233|4+Yawkey+Way,+Boston,+MA+02215|300+Fenway,+Boston,+MA+02115&key=AIzaSyAUftFKfNIO2RI64ZJM0joAG6Xtnolpc_8");
           
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            String line, outputString = "";
            BufferedReader reader = new BufferedReader(
            new InputStreamReader(conn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                outputString += line;
            }
              // System.out.println("outputString >>>> "+outputString);

            String[] distanceStringArray = new String[4];
            try{
                JSONObject obj = new JSONObject(outputString);
                JSONArray rows = obj.getJSONArray("rows");
                //String dist = obj.getJSONObject("rows").getJSONObject("elements").getString("distance");

                for(int i = 0; i<rows.length();i++){
                    JSONArray e=rows.getJSONObject(i).getJSONArray("elements");

                    for(int j = 0 ; j<e.length();j++){
                        String distance=e.getJSONObject(j).getJSONObject("distance").getString("text");
                        distanceStringArray[j]=distance;

                    }
                    
                    }
              
               

                }
                catch(Exception e)
                {
                System.out.println("The exception occured >> "+e.getMessage());
                }

             DistancePojo capRes = new Gson().fromJson(outputString, DistancePojo.class);
              //System.out.println("capRes >> "+capRes);
             
            
            String dist1="null";
            String dist2="null";
            String dist3="null";
            
            int p1 = distanceStringArray[0].indexOf(' ');
             if (p1 >= 0)
                {
                    dist1 = distanceStringArray[0].substring(0, p1);
                }

        
             int p2 = distanceStringArray[1].indexOf(' ');
             if (p2 >= 0)
                {
                    dist2 = distanceStringArray[1].substring(0, p2);
                }

        
            int p3 = distanceStringArray[2].indexOf(' ');
            if (p3 >= 0)
                {
                    dist3 = distanceStringArray[2].substring(0, p3);
                }
            
             float d1=Float.parseFloat(dist1);
             float d2=Float.parseFloat(dist2);
             float d3=Float.parseFloat(dist3);
             
             for(Organisation org:((Emergency911Enterprise)enterprise).getOrganizationDirectory().getOrganisationList())
             {
                 if(org instanceof DroneOrganisation)
                 {
                     ((DroneOrganisation)org).getDroneDirectoryObject().getDroneStationList().get(0).setDistanceFromEmergencyLoc(d1);
                     ((DroneOrganisation)org).getDroneDirectoryObject().getDroneStationList().get(1).setDistanceFromEmergencyLoc(d2);
                     ((DroneOrganisation)org).getDroneDirectoryObject().getDroneStationList().get(2).setDistanceFromEmergencyLoc(d3);
                     float smallest;
                    UserAccount userAcc=null;
                    if(d1<d2 && d1<d3)
                        {
                            smallest = d1;
                            closestDroneStation.setText("The closest drone station is "+ ((DroneOrganisation)org).getDroneDirectoryObject().getDroneStationList().get(0).getDroneStationName()+" which is at "+smallest+" km distance from the emergency location");    
        
                        }
                    else if(d2<d3)
                        {
                            smallest = d2;
                            closestDroneStation.setText("The closest drone station is "+ ((DroneOrganisation)org).getDroneDirectoryObject().getDroneStationList().get(1).getDroneStationName()+" which is at "+smallest+" km distance from the emergency location");
  
                        }
                     else
                         {
                            smallest = d3;
                            closestDroneStation.setText("The closest drone station is "+ ((DroneOrganisation)org).getDroneDirectoryObject().getDroneStationList().get(2).getDroneStationName()+" which is at "+smallest+" km distance from the emergency location");
           
                         }
 
                 }
             }
             
             
        }

        catch(Exception e)
        {
            System.err.println("The exception is "+e);
        }
        }
        
        for(Organisation org:enterprise.getOrganizationDirectory().getOrganisationList())
        {
            if(org instanceof DroneOrganisation)
            {
                for (DroneStation ds:((DroneOrganisation)org).getDroneDirectoryObject().getDroneStationList())
                {
                Object[] row = new Object[2];
                row[0] = ds;
                row[1] = ds.getDistanceFromEmergencyLoc();
     
                model.addRow(row);
                }
            }
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        header = new javax.swing.JLabel();
        subHeader = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        emergencyListPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        emergencyTable = new javax.swing.JTable();
        processTheEmrgBtn = new javax.swing.JButton();
        emergencyLocationLabel = new javax.swing.JLabel();
        natureOfEmergencyLabel = new javax.swing.JLabel();
        timeRecorderLabel = new javax.swing.JLabel();
        desciptionLabel = new javax.swing.JLabel();
        phoneNumberLabel = new javax.swing.JLabel();
        emergencyLocationTF = new javax.swing.JTextField();
        natureOfEmergencyTF = new javax.swing.JTextField();
        timeTF = new javax.swing.JTextField();
        descriptionTF = new javax.swing.JTextField();
        phoneNumberTF = new javax.swing.JTextField();
        alertTheDRonePanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        droneTable = new javax.swing.JTable();
        assignEmergencyToDroneBtn = new javax.swing.JButton();
        emgLocLabel = new javax.swing.JLabel();
        emergencylocCombo = new javax.swing.JComboBox();
        closestDroneStation = new javax.swing.JLabel();
        showDroneBtn = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        droneStationTable = new javax.swing.JTable();
        showActiveDroneBtn = new javax.swing.JButton();
        displacementLabel = new javax.swing.JLabel();
        displacementTF = new javax.swing.JTextField();
        inKmsLabel = new javax.swing.JLabel();
        timeLable = new javax.swing.JLabel();
        tfTime = new javax.swing.JTextField();
        inSecLabel = new javax.swing.JLabel();
        calBtn = new javax.swing.JButton();
        manageDroneStations = new javax.swing.JPanel();
        createDrone = new javax.swing.JTabbedPane();
        createDroneStationPanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        dsTable = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        addDroneStnBtn = new javax.swing.JButton();
        droneStnNameTF = new javax.swing.JTextField();
        droneStnAddressTF = new javax.swing.JTextField();
        droneStnNameErr = new javax.swing.JLabel();
        droneAddressErr = new javax.swing.JLabel();
        createDronePanel = new javax.swing.JPanel();
        droneStnLabel = new javax.swing.JLabel();
        droneStnCombo = new javax.swing.JComboBox();
        jScrollPane5 = new javax.swing.JScrollPane();
        dronedetailsTable = new javax.swing.JTable();
        droneIdLabel = new javax.swing.JLabel();
        droneIdTF = new javax.swing.JTextField();
        addDroneBtn = new javax.swing.JButton();
        userIdLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        droneUserIdTF = new javax.swing.JTextField();
        createUserAccountBtn = new javax.swing.JButton();
        dronePassword = new javax.swing.JPasswordField();
        droneIdErr = new javax.swing.JLabel();
        userIdErr = new javax.swing.JLabel();
        passwordErr = new javax.swing.JLabel();

        header.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        header.setText("Emergency 911 department work area");

        subHeader.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        subHeader.setText("Public Safety Dispatch Center");

        emergencyListPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        emergencyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sender", "Location of emergency", "Nature of emergency", "Priority"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(emergencyTable);
        if (emergencyTable.getColumnModel().getColumnCount() > 0) {
            emergencyTable.getColumnModel().getColumn(1).setMinWidth(250);
            emergencyTable.getColumnModel().getColumn(1).setPreferredWidth(250);
            emergencyTable.getColumnModel().getColumn(1).setMaxWidth(250);
        }

        processTheEmrgBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        processTheEmrgBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/Emergency911Department/images/1449792327_process.png"))); // NOI18N
        processTheEmrgBtn.setText("Process the emergency");
        processTheEmrgBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                processTheEmrgBtnActionPerformed(evt);
            }
        });

        emergencyLocationLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        emergencyLocationLabel.setText("Emergency location:");

        natureOfEmergencyLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        natureOfEmergencyLabel.setText("Nature of emergency:");

        timeRecorderLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        timeRecorderLabel.setText("Time at which emergency was recorded:");

        desciptionLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        desciptionLabel.setText("Description:");

        phoneNumberLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        phoneNumberLabel.setText("Phone number of caller:");

        emergencyLocationTF.setEnabled(false);

        natureOfEmergencyTF.setEnabled(false);

        timeTF.setEnabled(false);

        descriptionTF.setEnabled(false);

        phoneNumberTF.setEnabled(false);

        javax.swing.GroupLayout emergencyListPanelLayout = new javax.swing.GroupLayout(emergencyListPanel);
        emergencyListPanel.setLayout(emergencyListPanelLayout);
        emergencyListPanelLayout.setHorizontalGroup(
            emergencyListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(emergencyListPanelLayout.createSequentialGroup()
                .addGroup(emergencyListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(emergencyListPanelLayout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addGroup(emergencyListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(natureOfEmergencyLabel)
                            .addComponent(emergencyLocationLabel)
                            .addComponent(timeRecorderLabel)
                            .addComponent(desciptionLabel)
                            .addComponent(phoneNumberLabel))
                        .addGroup(emergencyListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(emergencyListPanelLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(emergencyListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(emergencyLocationTF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                                    .addComponent(natureOfEmergencyTF, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(phoneNumberTF, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(emergencyListPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(emergencyListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(timeTF)
                                    .addComponent(descriptionTF)))))
                    .addGroup(emergencyListPanelLayout.createSequentialGroup()
                        .addGap(260, 260, 260)
                        .addComponent(processTheEmrgBtn)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, emergencyListPanelLayout.createSequentialGroup()
                .addGap(0, 54, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
        emergencyListPanelLayout.setVerticalGroup(
            emergencyListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(emergencyListPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(processTheEmrgBtn)
                .addGap(18, 18, 18)
                .addGroup(emergencyListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emergencyLocationLabel)
                    .addComponent(emergencyLocationTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(emergencyListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(natureOfEmergencyLabel)
                    .addComponent(natureOfEmergencyTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(emergencyListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeRecorderLabel)
                    .addComponent(timeTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(emergencyListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(desciptionLabel)
                    .addComponent(descriptionTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(emergencyListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phoneNumberLabel)
                    .addComponent(phoneNumberTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(129, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Emergency list", emergencyListPanel);

        alertTheDRonePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        droneTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Drone Id", "Drone Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(droneTable);

        assignEmergencyToDroneBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        assignEmergencyToDroneBtn.setText("Assign emergency to drone");
        assignEmergencyToDroneBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignEmergencyToDroneBtnActionPerformed(evt);
            }
        });

        emgLocLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        emgLocLabel.setText("Emergency location:");

        emergencylocCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        emergencylocCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emergencylocComboActionPerformed(evt);
            }
        });

        closestDroneStation.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        showDroneBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        showDroneBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/Emergency911Department/images/1449793043_Paper_Plane.png"))); // NOI18N
        showDroneBtn.setText("Show drone");
        showDroneBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showDroneBtnActionPerformed(evt);
            }
        });

        droneStationTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Drone station name", "Distance from location (in kms)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(droneStationTable);

        showActiveDroneBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        showActiveDroneBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/Emergency911Department/images/1449792985_Paper-Plane.png"))); // NOI18N
        showActiveDroneBtn.setText("Show active drones");
        showActiveDroneBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showActiveDroneBtnActionPerformed(evt);
            }
        });

        displacementLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        displacementLabel.setText("Displacement to reach the accidental location:");

        inKmsLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        inKmsLabel.setForeground(new java.awt.Color(102, 102, 102));
        inKmsLabel.setText("in kms");

        timeLable.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        timeLable.setText("Time required to reach the accidental location:");

        inSecLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        inSecLabel.setForeground(new java.awt.Color(102, 102, 102));
        inSecLabel.setText("in seconds");

        calBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        calBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/Emergency911Department/images/1449792431_cloud-calculator.png"))); // NOI18N
        calBtn.setText("Calculate");
        calBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout alertTheDRonePanelLayout = new javax.swing.GroupLayout(alertTheDRonePanel);
        alertTheDRonePanel.setLayout(alertTheDRonePanelLayout);
        alertTheDRonePanelLayout.setHorizontalGroup(
            alertTheDRonePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(alertTheDRonePanelLayout.createSequentialGroup()
                .addGroup(alertTheDRonePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(alertTheDRonePanelLayout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(emgLocLabel)
                        .addGap(42, 42, 42)
                        .addComponent(emergencylocCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(alertTheDRonePanelLayout.createSequentialGroup()
                        .addGap(206, 206, 206)
                        .addComponent(showDroneBtn)
                        .addGap(39, 39, 39)
                        .addComponent(showActiveDroneBtn))
                    .addGroup(alertTheDRonePanelLayout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addGroup(alertTheDRonePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(alertTheDRonePanelLayout.createSequentialGroup()
                                .addComponent(timeLable)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tfTime, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inSecLabel))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, alertTheDRonePanelLayout.createSequentialGroup()
                                .addComponent(displacementLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(displacementTF, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inKmsLabel)
                                .addGap(23, 23, 23))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(91, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, alertTheDRonePanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(alertTheDRonePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, alertTheDRonePanelLayout.createSequentialGroup()
                        .addComponent(closestDroneStation, javax.swing.GroupLayout.PREFERRED_SIZE, 631, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, alertTheDRonePanelLayout.createSequentialGroup()
                        .addComponent(assignEmergencyToDroneBtn)
                        .addGap(236, 236, 236))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, alertTheDRonePanelLayout.createSequentialGroup()
                        .addComponent(calBtn)
                        .addGap(261, 261, 261))))
        );
        alertTheDRonePanelLayout.setVerticalGroup(
            alertTheDRonePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(alertTheDRonePanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(alertTheDRonePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emergencylocCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emgLocLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closestDroneStation, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(calBtn)
                .addGap(13, 13, 13)
                .addGroup(alertTheDRonePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(displacementLabel)
                    .addComponent(displacementTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inKmsLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(alertTheDRonePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inSecLabel)
                    .addComponent(timeLable))
                .addGap(28, 28, 28)
                .addGroup(alertTheDRonePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(showActiveDroneBtn)
                    .addComponent(showDroneBtn))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(assignEmergencyToDroneBtn)
                .addGap(51, 51, 51))
        );

        jTabbedPane1.addTab("Alert the drone", alertTheDRonePanel);

        manageDroneStations.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        createDroneStationPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Create drone stations");

        dsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Drone Station name", "Drone Station address"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(dsTable);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Drone station name:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Drone station address:");

        addDroneStnBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        addDroneStnBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/Emergency911Department/images/1449768059_More.png"))); // NOI18N
        addDroneStnBtn.setText("Add drone station");
        addDroneStnBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDroneStnBtnActionPerformed(evt);
            }
        });

        droneStnNameTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                droneStnNameTFFocusGained(evt);
            }
        });

        droneStnAddressTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                droneStnAddressTFFocusGained(evt);
            }
        });

        droneStnNameErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        droneStnNameErr.setForeground(new java.awt.Color(102, 102, 102));

        droneAddressErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        droneAddressErr.setForeground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout createDroneStationPanelLayout = new javax.swing.GroupLayout(createDroneStationPanel);
        createDroneStationPanel.setLayout(createDroneStationPanelLayout);
        createDroneStationPanelLayout.setHorizontalGroup(
            createDroneStationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createDroneStationPanelLayout.createSequentialGroup()
                .addContainerGap(111, Short.MAX_VALUE)
                .addGroup(createDroneStationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createDroneStationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createDroneStationPanelLayout.createSequentialGroup()
                            .addGap(93, 93, 93)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(209, 209, 209))
                        .addGroup(createDroneStationPanelLayout.createSequentialGroup()
                            .addGroup(createDroneStationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(droneStnNameErr, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(createDroneStationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(createDroneStationPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(droneStnNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(createDroneStationPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(28, 28, 28)
                                        .addGroup(createDroneStationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(droneAddressErr, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(droneStnAddressTF, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGap(126, 126, 126)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createDroneStationPanelLayout.createSequentialGroup()
                        .addComponent(addDroneStnBtn)
                        .addGap(206, 206, 206))))
            .addGroup(createDroneStationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(createDroneStationPanelLayout.createSequentialGroup()
                    .addGap(77, 77, 77)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        createDroneStationPanelLayout.setVerticalGroup(
            createDroneStationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createDroneStationPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 152, Short.MAX_VALUE)
                .addGroup(createDroneStationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(droneStnNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(droneStnNameErr, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(createDroneStationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(droneStnAddressTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(droneAddressErr, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addDroneStnBtn)
                .addGap(118, 118, 118))
            .addGroup(createDroneStationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(createDroneStationPanelLayout.createSequentialGroup()
                    .addGap(50, 50, 50)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(287, Short.MAX_VALUE)))
        );

        createDrone.addTab("Create Drone Stations", createDroneStationPanel);

        createDronePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        droneStnLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        droneStnLabel.setText("Drone station:");

        droneStnCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        droneStnCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                droneStnComboActionPerformed(evt);
            }
        });

        dronedetailsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Drone Id", "Drone Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(dronedetailsTable);

        droneIdLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        droneIdLabel.setText("Drone id:");

        droneIdTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                droneIdTFFocusGained(evt);
            }
        });

        addDroneBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        addDroneBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/Emergency911Department/images/1449768059_More.png"))); // NOI18N
        addDroneBtn.setText("Add drone");
        addDroneBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDroneBtnActionPerformed(evt);
            }
        });

        userIdLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        userIdLabel.setText("UserId:");

        passwordLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        passwordLabel.setText("Password:");

        droneUserIdTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                droneUserIdTFFocusGained(evt);
            }
        });

        createUserAccountBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        createUserAccountBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/Emergency911Department/images/1447997078_user.png"))); // NOI18N
        createUserAccountBtn.setText("Create user account");
        createUserAccountBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createUserAccountBtnActionPerformed(evt);
            }
        });

        dronePassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dronePasswordFocusGained(evt);
            }
        });

        droneIdErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        droneIdErr.setForeground(new java.awt.Color(102, 102, 102));

        userIdErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        userIdErr.setForeground(new java.awt.Color(102, 102, 102));

        passwordErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        passwordErr.setForeground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout createDronePanelLayout = new javax.swing.GroupLayout(createDronePanel);
        createDronePanel.setLayout(createDronePanelLayout);
        createDronePanelLayout.setHorizontalGroup(
            createDronePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createDronePanelLayout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addComponent(droneStnLabel)
                .addGap(42, 42, 42)
                .addComponent(droneStnCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(146, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createDronePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(createDronePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createDronePanelLayout.createSequentialGroup()
                        .addGroup(createDronePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(passwordLabel)
                            .addGroup(createDronePanelLayout.createSequentialGroup()
                                .addComponent(droneIdLabel)
                                .addGap(50, 50, 50)
                                .addComponent(droneIdTF, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(createDronePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(createUserAccountBtn)
                                .addGroup(createDronePanelLayout.createSequentialGroup()
                                    .addComponent(userIdLabel)
                                    .addGap(58, 58, 58)
                                    .addGroup(createDronePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(addDroneBtn)
                                        .addComponent(droneUserIdTF, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(dronePassword, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(droneIdErr, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(userIdErr, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(passwordErr, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(202, 202, 202))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createDronePanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76))))
        );
        createDronePanelLayout.setVerticalGroup(
            createDronePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createDronePanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(createDronePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(droneStnCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(droneStnLabel))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(createDronePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(droneIdLabel)
                    .addComponent(droneIdTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(droneIdErr, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addDroneBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(createDronePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(droneUserIdTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userIdLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userIdErr, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(createDronePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel)
                    .addComponent(dronePassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(passwordErr, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(createUserAccountBtn)
                .addGap(99, 99, 99))
        );

        createDrone.addTab("Create drones", createDronePanel);

        javax.swing.GroupLayout manageDroneStationsLayout = new javax.swing.GroupLayout(manageDroneStations);
        manageDroneStations.setLayout(manageDroneStationsLayout);
        manageDroneStationsLayout.setHorizontalGroup(
            manageDroneStationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageDroneStationsLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(createDrone, javax.swing.GroupLayout.PREFERRED_SIZE, 612, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        manageDroneStationsLayout.setVerticalGroup(
            manageDroneStationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageDroneStationsLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(createDrone, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Manage drone stations", manageDroneStations);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 65, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(216, 216, 216)
                        .addComponent(header))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(289, 289, 289)
                        .addComponent(subHeader)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(header)
                .addGap(18, 18, 18)
                .addComponent(subHeader)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void processTheEmrgBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_processTheEmrgBtnActionPerformed
        // TODO add your handling code here:
        int rowNumber=emergencyTable.getSelectedRow();
       
        if(rowNumber>=0)
        {
            e= (Emergency) emergencyTable.getValueAt(rowNumber,1);
            flag=true;
            manipulateFields(flag);
            emergencyLocationTF.setText(e.getLocationOfEmergency());
            natureOfEmergencyTF.setText(e.getNatureOfEmergency());
            
            timeTF.setText(e.getReportedTime().toString());
            descriptionTF.setText(e.getDescription());
            phoneNumberTF.setText(e.getCallersPhoneNumber());
            
           restrictFields(true);
        }
        
        else
            
        {
             JOptionPane.showMessageDialog(null, "Please select a row from the table", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        
    }//GEN-LAST:event_processTheEmrgBtnActionPerformed

    private void assignEmergencyToDroneBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignEmergencyToDroneBtnActionPerformed
        // TODO add your handling code here:
         Emergency emrg=(Emergency) emergencylocCombo.getSelectedItem();
        if(emrg.getEmergencyStatus().equalsIgnoreCase("Assigned to Drone")&&(emrg!=null))
        {
        JOptionPane.showMessageDialog(this,"A drone is already allocated to this emergency");
        }
        
        else{
        int row=droneStationTable.getSelectedRow();
        int droneRow=droneTable.getSelectedRow();
        if(row>=0&&droneRow>=0)
        {
        DroneStation ds=(DroneStation) droneStationTable.getValueAt(row,0);
         UserAccount userAcc=null;
      
        int selected=emergencyTable.getSelectedRow();
        WorkRequest request=(WorkRequest) emergencyTable.getValueAt(selected, 0);
        if (request != null){
            
            request.setSender(account);
            
               int rowSelected=droneTable.getSelectedRow();
               
               if(rowSelected>=0)
               {
                  Drone d= (Drone) droneTable.getValueAt(rowSelected,0);
                
                  for(Organisation org:enterprise.getOrganizationDirectory().getOrganisationList()) 
                  {
                      if(org instanceof DroneOrganisation)
                      {
                           for(UserAccount ua:org.getUserAccountDirectory().getUserAccountList())
                         {
                    
                            if(ua.getEmployee()==d)
                                {
                     
                         
                                    request.setReceiver(ua);
                                    request.setSender(account);
                                    emrg.setEmergencyStatus("Assigned to Drone");
                                   
                                    ua.getWorkQueue().getWorkRequestList().add(request);
                                    d.setStatus("Assigned");
                                    JOptionPane.showMessageDialog(this, "Emergency added to the drone");
                                    Date date=new Date();
                                    emrg.setDroneAlerted(date);
                                    emrg.setTotalTimeToReachDrone((emrg.getDroneAlerted().getTime()-emrg.getReportedTime().getTime())/1000%60);
                                    System.err.println("Total time from emrg reported--drone alerted"+emrg.getTotalTimeToReachDrone());
                                    DefaultTableModel model = (DefaultTableModel) droneTable.getModel();
                                    model.setRowCount(0);

                                     for (Drone drone:ds.getDroneList()){
                                     if(drone.getStatus().equalsIgnoreCase("active"))
                                         {
                                            Object[] row1 = new Object[2];
                                            row1[0] = drone;
                                            row1[1] = drone.getStatus();
     
                                             model.addRow(row1);
                                         }
                                    
                                }
                         }
                      }
                  }
   
           }
                  
      }
               
               else
               {
                   JOptionPane.showMessageDialog(this,"Choose the drone");
               }   
            
        }
        
        }
        
        else
        {
           JOptionPane.showMessageDialog(this, "You must choose drone station and drone to proceed");
        }
        
        }
        
    }//GEN-LAST:event_assignEmergencyToDroneBtnActionPerformed

    private void emergencylocComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emergencylocComboActionPerformed
        // TODO add your handling code here:
        populateDroneStationTable();
        displacementTF.setText("");
        tfTime.setText("");
    }//GEN-LAST:event_emergencylocComboActionPerformed

    private void showDroneBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showDroneBtnActionPerformed
        // TODO add your handling code here:
       int rowNumber=droneStationTable.getSelectedRow();
       
       
       if(rowNumber>=0)
       {
        DroneStation ds=  (DroneStation) droneStationTable.getValueAt(rowNumber,0);  
        populateDroneTable(ds);
        droneTable.setEnabled(false);
       
       }
       
       else
       {
           JOptionPane.showMessageDialog(this,"Select a row from the table");
       }
       
    }//GEN-LAST:event_showDroneBtnActionPerformed

    private void showActiveDroneBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showActiveDroneBtnActionPerformed
        // TODO add your handling code here:
        int rowNumber=droneStationTable.getSelectedRow();
       
       
       if(rowNumber>=0)
       {
        DroneStation ds=  (DroneStation) droneStationTable.getValueAt(rowNumber,0);  
        DefaultTableModel model = (DefaultTableModel) droneTable.getModel();
        
        model.setRowCount(0);
         droneTable.setEnabled(true);
        for (Drone d:ds.getDroneList()){
            if(d.getStatus().equalsIgnoreCase("active"))
            {
            Object[] row = new Object[2];
            row[0] = d;
            row[1] = d.getStatus();
     
            model.addRow(row);
            }
        }
       
       }
       
       else
       {
           JOptionPane.showMessageDialog(this,"Select a row from the table");
       }
    }//GEN-LAST:event_showActiveDroneBtnActionPerformed

    private void calBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calBtnActionPerformed
        // TODO add your handling code here:
        int row=droneStationTable.getSelectedRow();
        if(row>=0)
        {
                
        double lat1 = 0,lat2 = 0,lng1 = 0,lng2 = 0;
       int rowNumber=droneStationTable.getSelectedRow();

       if(rowNumber>=0)
       {
        DroneStation ds=  (DroneStation) droneStationTable.getValueAt(rowNumber,0); 
        String droneStationAddress=ds.getDroneStationAddress().replaceAll("\\s","");
        
        //find the lat long of drone station
        
        try {
        
        URL url1 = new URL ("https://maps.googleapis.com/maps/api/geocode/json?address="+droneStationAddress+"&key=AIzaSyBYk0Mj7AKJD1lDBJlkRFAnjs16rz_JRJA");
        HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
        conn.setRequestMethod("GET");
        String line, outputString = "";
        BufferedReader reader = new BufferedReader(
        new InputStreamReader(conn.getInputStream()));
        while ((line = reader.readLine()) != null)
        {
          outputString += line;
        }
      //  System.out.println("outputString >>>> "+outputString);
        try
        {
              JSONObject obj = new JSONObject(outputString); 
              JSONArray rows = obj.getJSONArray("results");
              //String dist = obj.getJSONObject("rows").getJSONObject("elements").getString("distance");  
              
              for(int i = 0; i<rows.length();i++)
              {
              JSONObject e=    rows.getJSONObject(i).getJSONObject("geometry").getJSONObject("location");
//                  System.err.println("The latitude of the droneStation is "+e.getDouble("lat"));
//                  System.err.println("The longitude of the droneStation is "+e.getDouble("lng"));
                lat1=e.getDouble("lat");
                lng1=e.getDouble("lng");
                // System.out.println("the lat long of the dronestation is"+lat1+" "+lng1);
              }
            }
             
             catch(Exception e)
             {
                 System.out.println("Exception"+e);
                 
             }

        }
        
        
       catch(Exception e)
         {
             System.out.println("An exception occured"+e);     
         }
        
        
        //find the lat long of accidental location
        
        
         try {
        
        URL url2 = new URL ("https://maps.googleapis.com/maps/api/geocode/json?address="+location+"&key=AIzaSyBYk0Mj7AKJD1lDBJlkRFAnjs16rz_JRJA");
        HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
        conn2.setRequestMethod("GET");
        String line2, outputString2 = "";
        BufferedReader reader = new BufferedReader(
        new InputStreamReader(conn2.getInputStream()));
        while ((line2 = reader.readLine()) != null)
        {
          outputString2 += line2;
        }
      //  System.out.println("outputString >>>> "+outputString2);
        try
        {
              JSONObject obj2 = new JSONObject(outputString2); 
              JSONArray rows2 = obj2.getJSONArray("results");
              //String dist = obj.getJSONObject("rows").getJSONObject("elements").getString("distance");  
              
              for(int i = 0; i<rows2.length();i++)
              {
              JSONObject e2=rows2.getJSONObject(i).getJSONObject("geometry").getJSONObject("location");
//                  System.err.println("The latitude of the droneStation is "+e2.getDouble("lat"));
//                  System.err.println("The longitude of the droneStation is "+e2.getDouble("lng"));

                   lat2=e2.getDouble("lat");
                   lng2=e2.getDouble("lng");
                  // System.out.println("the lat long of the emeregncy loc is"+lat2+" "+lng2);
              }
            }
             
             catch(Exception e)
             {
                 System.out.println("Exception"+e);
                 
             }

        }
        
        
       catch(Exception e)
         {
             System.out.println("An exception occured"+e);     
         }
        
         

              double result=HaversineInKM(lat1,lng1,lat2,lng2);
    //          System.out.println("the distance is "+result);
              result=(double)Math.round(result * 10000d) / 10000d;
              displacementTF.setText(Double.toString(result));
              
              double time=calculateTime(result);
              tfTime.setText(Double.toString(time));
       
       }
        }
        
        else
        
        {
            JOptionPane.showMessageDialog(this, "Choose a drone station from the table");
        }
       
    }//GEN-LAST:event_calBtnActionPerformed

    private void addDroneStnBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addDroneStnBtnActionPerformed
        // TODO add your handling code here:
        droneStationAlreadyPresent=false;
        if(droneStnNameTF.getText().trim().matches("[a-zA-Z0-9 ]+")&& (droneStnAddressTF.getText().trim().matches("[a-zA-Z0-9 ]+")))
        {
        for (Organisation org:((Emergency911Enterprise)(enterprise)).getOrganizationDirectory().getOrganisationList())
            {
           
            if(org instanceof DroneOrganisation)
            {
               for(DroneStation ds:((DroneOrganisation)org).getDroneDirectoryObject().getDroneStationList())
               {
                   if(ds.getDroneStationName().equalsIgnoreCase(droneStnNameTF.getText()))
                   {
                       droneStationAlreadyPresent=true;
                       break;
                   }
                   
               }
            }
            break;
            }
        
           if(droneStationAlreadyPresent==true)
           {
               droneStnNameErr.setText("This drone station is already present");
           }
           
           else
           {
                for(Organisation org:((Emergency911Enterprise)enterprise).getOrganizationDirectory().getOrganisationList())
        {
            if(org instanceof DroneOrganisation)
            {
                DroneStation ds=((DroneOrganisation)org).getDroneDirectoryObject().addDroneStation();
                ds.setDroneStationName(droneStnNameTF.getText());
                ds.setDroneStationAddress(droneStnAddressTF.getText());
                JOptionPane.showMessageDialog(this,"Drone station successfully added");
                populateDroneStationTab();
                droneStnNameTF.setText("");
                droneStnAddressTF.setText("");
                populateDroneStationCombo();   
            }
        }
           }
        }
        
        else
        {
            if(!droneStnNameTF.getText().trim().matches("[a-zA-Z0-9]+"))
            {
                droneStnNameErr.setText("Enter a valid drone station name");
            }
            
            if(!droneStnAddressTF.getText().trim().matches("[a-zA-Z0-9 ]+"))
            {
                droneAddressErr.setText("Enter a valid drone station address");
            }
        }
        
       
        
        
    }//GEN-LAST:event_addDroneStnBtnActionPerformed

    private void droneStnComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_droneStnComboActionPerformed
        // TODO add your handling code here:
         DroneStation ds=(DroneStation) droneStnCombo.getSelectedItem();
         if(ds!=null)
        populateDroneDetailsTable(ds);
    }//GEN-LAST:event_droneStnComboActionPerformed

    private void addDroneBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addDroneBtnActionPerformed
        // TODO add your handling code here:
        droneIdAlreadyPresent=false;
         if(droneIdTF.getText().trim().matches("[a-zA-Z0-9_ ]+"))
         {
            for (Organisation org:((Emergency911Enterprise)(enterprise)).getOrganizationDirectory().getOrganisationList()){
           
            if(org instanceof DroneOrganisation)
            {
                for(DroneStation ds:((DroneOrganisation)org).getDroneDirectoryObject().getDroneStationList())
                {
                   for(Drone d:ds.getDroneList())
                   {
                       if(d.getDroneId().equalsIgnoreCase(droneIdTF.getText()))
                       {
                           droneIdAlreadyPresent=true;
                           break;
                       }
                   }
                   if(droneIdAlreadyPresent==true)
                       break;
                }
                
                if(droneIdAlreadyPresent==true)
                    droneIdErr.setText("This drone id already exist");
                else
                {
                     DroneStation ds=(DroneStation) droneStnCombo.getSelectedItem();
                     Drone d=new Drone();
                     d.setDroneId(droneIdTF.getText().trim());
                     d.setStatus("Active");
                     ds.addDrone(d);
                     JOptionPane.showMessageDialog(this,"The drone has been added to the drone station successfully");
                     droneIdTF.setText("");
                     populateDroneDetailsTable(ds);
                }
            }
            
        } 
       
         }
         
         else
         {
             droneIdErr.setText("Enter a valid drone id");
         }
        
    }//GEN-LAST:event_addDroneBtnActionPerformed

    private void createUserAccountBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createUserAccountBtnActionPerformed
        // TODO add your handling code here:
        droneHasUserName=false;
        String username=droneUserIdTF.getText().trim();
         char[] passwordCharArray = dronePassword.getPassword();
        String password = String.valueOf(passwordCharArray).trim(); 
        Drone d=null;
        if((username.matches("[a-zA-Z0-9]+"))&&(password.matches("[a-zA-Z0-9]+")))
        {
            int rowSelected=dronedetailsTable.getSelectedRow();
            if(rowSelected>=0)
            {
            d=(Drone) dronedetailsTable.getValueAt(rowSelected, 0);
            for (Organisation org:((Emergency911Enterprise)(enterprise)).getOrganizationDirectory().getOrganisationList())
            {
           
            if(org instanceof DroneOrganisation)
            {
               
                for(UserAccount ua:org.getUserAccountDirectory().getUserAccountList())
                {
                    if(ua.getEmployee()==d)
                        {
                            droneHasUserName=true;
                            break;
                        }
                        
                }
                
            }
             break;
            }
            
             if(droneHasUserName==false)
             {
                  droneUserNameAlreadyPresent=emergencysystem.checkIfUserNameIsUnique(username, password);
            if(droneUserNameAlreadyPresent==false)
            {
                userIdErr.setText("Username already exists");
            }
            
            else
            {
                int rowSelected1=dronedetailsTable.getSelectedRow();
         DroneOrganisation droneOrg=(DroneOrganisation) enterprise.getOrganizationDirectory().getOrganisationList().get(0);
         if(rowSelected>=0)
         {
             Drone drone=(Drone) dronedetailsTable.getValueAt(rowSelected1, 0);
             droneOrg.getEmployeeDirectory().createEmployee(drone);
             droneOrg.getUserAccountDirectory().createUserAccount(droneUserIdTF.getText(),String.valueOf(dronePassword.getPassword()), drone, new DroneRole() );
             JOptionPane.showMessageDialog(this,"Drone's user account details created successfully");
             droneUserIdTF.setText("");
             dronePassword.setText("");
         }
        
         else{
             JOptionPane.showMessageDialog(this,"Choose the drone from the table");
         }
            }
             }
             
             else
             {
                 JOptionPane.showMessageDialog(this, "The username for this drone has already been set");
             }
            
           
            }
            
            else
            {
                JOptionPane.showMessageDialog(this, "Choose a drone from the drone table");
            }
            
            
        }
        
        else
        {
            if(!username.matches("[a-zA-Z0-9]+"))
            {
                userIdErr.setText("Enter a valid username");
            }
            
            if(!password.matches("[a-zA-Z0-9]+"))
            {
                passwordErr.setText("Enter a valid password");
            }
        }
       
        
        
        
         
        
    }//GEN-LAST:event_createUserAccountBtnActionPerformed

    private void droneIdTFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_droneIdTFFocusGained
        // TODO add your handling code here:
        droneIdErr.setText("");
    }//GEN-LAST:event_droneIdTFFocusGained

    private void droneUserIdTFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_droneUserIdTFFocusGained
        // TODO add your handling code here:
        userIdErr.setText("");
    }//GEN-LAST:event_droneUserIdTFFocusGained

    private void dronePasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dronePasswordFocusGained
        // TODO add your handling code here:
        passwordErr.setText("");
    }//GEN-LAST:event_dronePasswordFocusGained

    private void droneStnNameTFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_droneStnNameTFFocusGained
        // TODO add your handling code here:
        droneStnNameErr.setText("");
    }//GEN-LAST:event_droneStnNameTFFocusGained

    private void droneStnAddressTFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_droneStnAddressTFFocusGained
        // TODO add your handling code here:
        droneAddressErr.setText("");
    }//GEN-LAST:event_droneStnAddressTFFocusGained

     static final double _eQuatorialEarthRadius = 6378.1370D;
    static final double _d2r = (Math.PI / 180D);

    public static int HaversineInM(double lat1, double long1, double lat2, double long2) {
        return (int) (1000D * HaversineInKM(lat1, long1, lat2, long2));
    }

    public static double HaversineInKM(double lat1, double long1, double lat2, double long2) {
        double dlong = (long2 - long1) * _d2r;
        double dlat = (lat2 - lat1) * _d2r;
        double a = Math.pow(Math.sin(dlat / 2D), 2D) + Math.cos(lat1 * _d2r) * Math.cos(lat2 * _d2r)
                * Math.pow(Math.sin(dlong / 2D), 2D);
        double c = 2D * Math.atan2(Math.sqrt(a), Math.sqrt(1D - a));
        double d = _eQuatorialEarthRadius * c;

        return d;
    }
    
   public double calculateTime(double dist)
   {
       double speed=100;
       
       double time=dist/speed;
    //   System.err.println("the dist is "+dist+" the time is in hrs is"+time);
       time=time*3600;
     //  System.err.println("the time in seconds is "+time);
       time=(double)Math.round(time * 10000d) / 10000d;
    //   System.err.println("the rounded time is "+time);
       return time;
       
   }
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addDroneBtn;
    private javax.swing.JButton addDroneStnBtn;
    private javax.swing.JPanel alertTheDRonePanel;
    private javax.swing.JButton assignEmergencyToDroneBtn;
    private javax.swing.JButton calBtn;
    private javax.swing.JLabel closestDroneStation;
    private javax.swing.JTabbedPane createDrone;
    private javax.swing.JPanel createDronePanel;
    private javax.swing.JPanel createDroneStationPanel;
    private javax.swing.JButton createUserAccountBtn;
    private javax.swing.JLabel desciptionLabel;
    private javax.swing.JTextField descriptionTF;
    private javax.swing.JLabel displacementLabel;
    private javax.swing.JTextField displacementTF;
    private javax.swing.JLabel droneAddressErr;
    private javax.swing.JLabel droneIdErr;
    private javax.swing.JLabel droneIdLabel;
    private javax.swing.JTextField droneIdTF;
    private javax.swing.JPasswordField dronePassword;
    private javax.swing.JTable droneStationTable;
    private javax.swing.JTextField droneStnAddressTF;
    private javax.swing.JComboBox droneStnCombo;
    private javax.swing.JLabel droneStnLabel;
    private javax.swing.JLabel droneStnNameErr;
    private javax.swing.JTextField droneStnNameTF;
    private javax.swing.JTable droneTable;
    private javax.swing.JTextField droneUserIdTF;
    private javax.swing.JTable dronedetailsTable;
    private javax.swing.JTable dsTable;
    private javax.swing.JPanel emergencyListPanel;
    private javax.swing.JLabel emergencyLocationLabel;
    private javax.swing.JTextField emergencyLocationTF;
    private javax.swing.JTable emergencyTable;
    private javax.swing.JComboBox emergencylocCombo;
    private javax.swing.JLabel emgLocLabel;
    private javax.swing.JLabel header;
    private javax.swing.JLabel inKmsLabel;
    private javax.swing.JLabel inSecLabel;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel manageDroneStations;
    private javax.swing.JLabel natureOfEmergencyLabel;
    private javax.swing.JTextField natureOfEmergencyTF;
    private javax.swing.JLabel passwordErr;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JLabel phoneNumberLabel;
    private javax.swing.JTextField phoneNumberTF;
    private javax.swing.JButton processTheEmrgBtn;
    private javax.swing.JButton showActiveDroneBtn;
    private javax.swing.JButton showDroneBtn;
    private javax.swing.JLabel subHeader;
    private javax.swing.JTextField tfTime;
    private javax.swing.JLabel timeLable;
    private javax.swing.JLabel timeRecorderLabel;
    private javax.swing.JTextField timeTF;
    private javax.swing.JLabel userIdErr;
    private javax.swing.JLabel userIdLabel;
    // End of variables declaration//GEN-END:variables
}
