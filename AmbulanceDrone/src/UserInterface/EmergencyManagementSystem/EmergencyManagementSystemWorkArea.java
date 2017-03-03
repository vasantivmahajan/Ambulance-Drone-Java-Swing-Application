/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.EmergencyManagementSystem;

import Employee.EnterpriseAdmin;
import Employee.HospitalEnterpriseAdmin;
import EmergencySystem.Emergency.AccidentEmergency;
import EmergencySystem.Emergency.Description;
import EmergencySystem.Emergency.Emergency;
import EmergencySystem.Emergency.Emergency.EmergencyType;
import EmergencySystem.Emergency.EmergencyDirectory;
import EmergencySystem.Emergency.FireEmergency;
import EmergencySystem.Emergency.MedicalEmergency;
import EmergencySystem.EmergencySystem;
import EmergencySystem.Enterprise.Emergency911Enterprise;
import EmergencySystem.Enterprise.Enterprise;
import EmergencySystem.Enterprise.Enterprise.EnterpriseType;
import EmergencySystem.Network.Network;
import Hospital.Hospital;
import Hospital.Role.Emergency911EnterpriseAdminRole;
import Hospital.Role.HospitalEnterpriseAdminRole;
import Hospital.UserAccount.UserAccount;
import Hospital.WorkQueue.Emergency911DepartmentWorkRequest;
import Hospital.WorkQueue.WorkRequest;
import com.google.gson.Gson;
import com.sl.DistancePojo;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 *
 * @author Vasanti
 */
public class EmergencyManagementSystemWorkArea extends javax.swing.JPanel  {

    /**
     * Creates new form EmergencyManagementSystemWorkArea
     */
    private JPanel userProcessContainer;
    private EmergencySystem system;
    private EmergencyDirectory emergencyDirectory;
    private String location;
    private Emergency e;
    private boolean networkAlreadyPresent;
    private boolean enterpriseAlreadyPresent;
    private boolean hospitalAlreadyPresent;
    private boolean userNameIsUnique;
    private boolean entUsernameIsUnique;
   
    private UserAccount userAccount;
    public EmergencyManagementSystemWorkArea(JPanel userProcessContainer, EmergencySystem system, UserAccount userAccount)
    {
        initComponents();
        this.userProcessContainer=userProcessContainer;
        this.system=system;
        this.userAccount=userAccount;
       
        emergencyDirectory=system.getEmergencyDirectory();
        locateBtn.setEnabled(false);
        distTF1.setEditable(false);
        dist2TF.setEditable(false);
        dist3TF.setEditable(false);
        findTheDistBtn.setEnabled(false);
        routeCallBtn.setEnabled(false);
        populateEmergencyLocation();
        ent911AddressTF.setVisible(false);
        ent911AddressLabel.setVisible(false);
        populateNetworkTable();
        populateCombo();
        populateAdminNetworkCombo();
        populateEntTable();
        populateNatureOfEmergencyCombo();
        populateTable();
        populateAdminDetailsTable();
        populateNetCombo();
        populateNetworkJCombo();
        populateTimeReportTable();
    }
    
    public void  populateTimeReportTable()
    {
    DefaultTableModel model = (DefaultTableModel) timeFrameTable.getModel();

        model.setRowCount(0);

        for(WorkRequest work:userAccount.getWorkQueue().getWorkRequestList())
        {
            Object[] row = new Object[5];
            row[0] = ((Emergency911DepartmentWorkRequest)work).getEmergency();
            row[1] = ((Emergency911DepartmentWorkRequest)work).getEmergency().getTotalTimeToReachDrone();
            row[2] = ((Emergency911DepartmentWorkRequest)work).getEmergency().getTotalTimeForDoctorToGetComnnected();
            row[3] = ((Emergency911DepartmentWorkRequest)work).getEmergency().getTotalTimeToDispatchAmbulance();
            row[4] = ((Emergency911DepartmentWorkRequest)work).getEmergency().getTotatTimeTakenByPoliceToAlertEmergencyContact();
            

             model.addRow(row);
        }
        
       
    }
    
    public void populateEmergencyLocation()
    {
       
        Random rand = new Random(); 
        int index=rand.nextInt(5);
         
         locationEmergencyTF.setText(system.getDirectory().getEmergencyAddressLocationList().get(index).getAddress());
    }
    
    public void populateNetCombo()
    {
         netCombo.removeAllItems();
        
        for (Network network : system.getNetworkList()){
            netCombo.addItem(network);
        }
    }
    
    public void populateNetworkJCombo()
    {
    networkJCombo.removeAllItems();
        
        for (Network network : system.getNetworkList()){
            networkJCombo.addItem(network);
        }
    }
    
 
    
    public void populateAdminDetailsTable()
    {
        DefaultTableModel model = (DefaultTableModel) entAdminTable.getModel();

        model.setRowCount(0);
        for (Network network : system.getNetworkList()) 
        {
            for (Enterprise enterprise : network.getEntDirObj().getEnterpriseList()) 
            {
                for (UserAccount userAccount : enterprise.getUserAccountDirectory().getUserAccountList())
                {
                    Object[] row = new Object[3];
                    row[0] = enterprise.getName();
                    row[1] = network.getNetworkName();
                    row[2] = userAccount.getUsername();

                    model.addRow(row);
                }
            }
        }
    }
    
    public void populateHospitalTable(Network network)
    {
     DefaultTableModel model = (DefaultTableModel) hospitalTable.getModel();
     model.setRowCount(0);
     
     for(Hospital h:network.getHospitalList())
     {
         Object[] row = new Object[2];
         row[0] = h;
         row[1]=network;
         model.addRow(row);
         
     }
    }
    
    public void populateAdminNetworkCombo()
    {
        networkAdminCombo.removeAllItems();
        
        for (Network network : system.getNetworkList()){
            networkAdminCombo.addItem(network);
        }
    }
    
    public void populateEnterpriseAdminComboBox(Network network)
    {
        entAdminCombo.removeAllItems();
        
        for (Enterprise enterprise : network.getEntDirObj().getEnterpriseList()){
            entAdminCombo.addItem(enterprise);
        }
    }
    public void populateNetworkTable()
    {
        DefaultTableModel model = (DefaultTableModel) addNetworkTable.getModel();
        
        model.setRowCount(0);
        
        for (Network network : system.getNetworkList()){
            Object[] row = new Object[1];
            row[0] = network;
            model.addRow(row);
        }
    }
    
    public void populateEntTable()
    {
        DefaultTableModel model = (DefaultTableModel) enterpriseTable.getModel();

        model.setRowCount(0);
        for (Network network : system.getNetworkList()) 
        {
            for (Enterprise enterprise : network.getEntDirObj().getEnterpriseList()) 
            {
                Object[] row = new Object[3];
                row[0] = enterprise.getName();
                row[1] = network.getNetworkName();
                row[2] = enterprise.getEnterpriseType().getValue();

                model.addRow(row);
            }
        }
    }
    
    public void  populateCombo()
    {
         networkCombo.removeAllItems();
        for (Network n:system.getNetworkList())
        {  
                networkCombo.addItem(n);
        }
        entTypeCombo.removeAllItems();
        for (Enterprise.EnterpriseType type : Enterprise.EnterpriseType.values()) {
            if(!type.getValue().equalsIgnoreCase("Hospital Enterprise"))
            entTypeCombo.addItem(type);
        }
  
    }
    public void populateNatureOfEmergencyCombo()
    {
         natureOfEmergencyCombo.removeAllItems();
        for (EmergencyType type : Emergency.EmergencyType.values())
        {
                String value=type.getValue();
                natureOfEmergencyCombo.addItem(value);
        }
  
    }
    
    public void populateDescriptionComboBox(String emrg)
    {
        descriptionCombo.removeAllItems();
        if(emrg.equalsIgnoreCase("Accident Emergency"))
        {
            AccidentEmergency accEmerg=new AccidentEmergency();
            for(Description d:accEmerg.getDescriptionList())
            {
                descriptionCombo.addItem(d.getName());
            }
        }
        
        else if(emrg.equalsIgnoreCase("Medical Emergency"))
        {
            MedicalEmergency medEmerg=new MedicalEmergency();
          
            for(Description d:medEmerg.getDescriptionList())
            {
                descriptionCombo.addItem(d.getName());
            }
        }
        
         else if(emrg.equalsIgnoreCase("Fire Emergency"))
        {
            FireEmergency fireEmerg=new FireEmergency();
            System.err.println("I am going in the fire emer");
            for(Description d:fireEmerg.getDescriptionList())
            {
                descriptionCombo.addItem(d.getName());
            }
        }
    }
    public void populateTable()
    {
        DefaultTableModel model = (DefaultTableModel) networkTable.getModel();
        
        model.setRowCount(0);
        
        for (Network network : system.getNetworkList()){
            Object[] row = new Object[2];
            row[0] = network;
            for(Enterprise e:network.getEntDirObj().getEnterpriseList())
            {
                if(e instanceof Emergency911Enterprise)
                {
                     row[1] = ((Emergency911Enterprise)e).getEmergency911DepartmentName();
                }
            }
           
            model.addRow(row);
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pickTheCall = new javax.swing.JPanel();
        emergencyLocation = new javax.swing.JLabel();
        phoneNumberOfCaller = new javax.swing.JLabel();
        natureOfEmergency = new javax.swing.JLabel();
        description = new javax.swing.JLabel();
        locationEmergencyTF = new javax.swing.JTextField();
        callersPhoneNumberTF = new javax.swing.JTextField();
        locationErr = new javax.swing.JLabel();
        callerPhoneNumberErr = new javax.swing.JLabel();
        natureOfemergencyErr = new javax.swing.JLabel();
        descriptionErr = new javax.swing.JLabel();
        reportAnEmergencyBtn = new javax.swing.JButton();
        locateBtn = new javax.swing.JButton();
        priorityLabel = new javax.swing.JLabel();
        prioritySlider = new javax.swing.JSlider();
        natureOfEmergencyCombo = new javax.swing.JComboBox();
        descriptionCombo = new javax.swing.JComboBox();
        search911Dept = new javax.swing.JPanel();
        table = new javax.swing.JScrollPane();
        networkTable = new javax.swing.JTable();
        findTheDistBtn = new javax.swing.JButton();
        bostonDitsLabel = new javax.swing.JLabel();
        camDistLabel = new javax.swing.JLabel();
        maldenDistLabel = new javax.swing.JLabel();
        distTF1 = new javax.swing.JTextField();
        dist2TF = new javax.swing.JTextField();
        dist3TF = new javax.swing.JTextField();
        closestPSAPLabel = new javax.swing.JLabel();
        routeCallBtn = new javax.swing.JButton();
        manageEmergencySystem = new javax.swing.JPanel();
        managedEmeregencyPanel = new javax.swing.JTabbedPane();
        createNetworkPanel = new javax.swing.JPanel();
        networkTab = new javax.swing.JScrollPane();
        addNetworkTable = new javax.swing.JTable();
        networkNameLabel = new javax.swing.JLabel();
        networkNameTF = new javax.swing.JTextField();
        addNetworkBtn = new javax.swing.JButton();
        networkNameErr = new javax.swing.JLabel();
        createHospitalPanel = new javax.swing.JPanel();
        hospitalNameTF = new javax.swing.JTextField();
        hospitalAddressTF = new javax.swing.JTextField();
        specialityTF = new javax.swing.JTextField();
        noOfBedsTF = new javax.swing.JTextField();
        noOfEmptyBedsTF = new javax.swing.JTextField();
        createHospitalHeader = new javax.swing.JLabel();
        addHospitalBtn = new javax.swing.JButton();
        hospitalNameLabel = new javax.swing.JLabel();
        hospitalAddressLabel = new javax.swing.JLabel();
        specialityLabel = new javax.swing.JLabel();
        noOfBedsLabel = new javax.swing.JLabel();
        numberOfEmptyBeds = new javax.swing.JLabel();
        networkLabel = new javax.swing.JLabel();
        netCombo = new javax.swing.JComboBox();
        hospNameErr = new javax.swing.JLabel();
        hospAddressErr = new javax.swing.JLabel();
        specilaityErr = new javax.swing.JLabel();
        noOfBedsErr = new javax.swing.JLabel();
        noOfEmptyBedsErr = new javax.swing.JLabel();
        createEnterprisePanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        enterpriseTable = new javax.swing.JTable();
        netLabel = new javax.swing.JLabel();
        entTypeLabel = new javax.swing.JLabel();
        entNameLabel = new javax.swing.JLabel();
        entNameTf = new javax.swing.JTextField();
        createEntBtn = new javax.swing.JButton();
        networkCombo = new javax.swing.JComboBox();
        entTypeCombo = new javax.swing.JComboBox();
        ent911AddressTF = new javax.swing.JTextField();
        ent911AddressLabel = new javax.swing.JLabel();
        entNameErr = new javax.swing.JLabel();
        entAddressErr = new javax.swing.JLabel();
        createEntAdminPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        entAdminTable = new javax.swing.JTable();
        netwrkL = new javax.swing.JLabel();
        enterpriseLabel = new javax.swing.JLabel();
        entAdNameL = new javax.swing.JLabel();
        entAdminUserL = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        name_AdminTF = new javax.swing.JTextField();
        username_Admin_TF = new javax.swing.JTextField();
        networkAdminCombo = new javax.swing.JComboBox();
        entAdminCombo = new javax.swing.JComboBox();
        createAdminButton = new javax.swing.JButton();
        passwordAdminTF = new javax.swing.JPasswordField();
        nameAdminErr = new javax.swing.JLabel();
        passwordAdminErr = new javax.swing.JLabel();
        usernameAdminErr = new javax.swing.JLabel();
        createHospitalAdminPanel = new javax.swing.JPanel();
        networkJLabel = new javax.swing.JLabel();
        networkJCombo = new javax.swing.JComboBox();
        jScrollPane4 = new javax.swing.JScrollPane();
        hospitalTable = new javax.swing.JTable();
        nameLabel = new javax.swing.JLabel();
        userNameLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        hospitalAdminName = new javax.swing.JTextField();
        hospitalAdminUsernameTF = new javax.swing.JTextField();
        hospitalAdminPassword = new javax.swing.JPasswordField();
        createAdminBtn = new javax.swing.JButton();
        nameErr = new javax.swing.JLabel();
        passErr = new javax.swing.JLabel();
        userNaemErr = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        timeFrameTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(779, 671));

        header.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        header.setText("Emergency Management System Admin Work Area");

        pickTheCall.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        emergencyLocation.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        emergencyLocation.setText("Location of the emergency:");

        phoneNumberOfCaller.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        phoneNumberOfCaller.setText("Phone number of the caller:");

        natureOfEmergency.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        natureOfEmergency.setText("Nature of the emergency: ");

        description.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        description.setText("Description:");

        locationEmergencyTF.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        locationEmergencyTF.setEnabled(false);

        callerPhoneNumberErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        callerPhoneNumberErr.setForeground(new java.awt.Color(102, 102, 102));

        reportAnEmergencyBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        reportAnEmergencyBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/EmergencyManagementSystem/images/1449767337_09.png"))); // NOI18N
        reportAnEmergencyBtn.setText("Report an emergency");
        reportAnEmergencyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportAnEmergencyBtnActionPerformed(evt);
            }
        });

        locateBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        locateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/EmergencyManagementSystem/images/1449767491_map_pin_fill.png"))); // NOI18N
        locateBtn.setText("Locate the emergency location");
        locateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locateBtnActionPerformed(evt);
            }
        });

        priorityLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        priorityLabel.setText("Priority:");

        prioritySlider.setMajorTickSpacing(1);
        prioritySlider.setMaximum(10);
        prioritySlider.setMinorTickSpacing(1);
        prioritySlider.setPaintLabels(true);
        prioritySlider.setPaintTicks(true);
        prioritySlider.setValue(0);

        natureOfEmergencyCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        natureOfEmergencyCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                natureOfEmergencyComboActionPerformed(evt);
            }
        });

        descriptionCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pickTheCallLayout = new javax.swing.GroupLayout(pickTheCall);
        pickTheCall.setLayout(pickTheCallLayout);
        pickTheCallLayout.setHorizontalGroup(
            pickTheCallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pickTheCallLayout.createSequentialGroup()
                .addGroup(pickTheCallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pickTheCallLayout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addGroup(pickTheCallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pickTheCallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(emergencyLocation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(phoneNumberOfCaller, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(natureOfEmergency, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pickTheCallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(priorityLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(description, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)))
                        .addGap(42, 42, 42))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pickTheCallLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(reportAnEmergencyBtn)
                        .addGap(18, 18, 18)))
                .addGroup(pickTheCallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(locationEmergencyTF, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pickTheCallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(locationErr, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pickTheCallLayout.createSequentialGroup()
                            .addComponent(prioritySlider, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(descriptionErr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pickTheCallLayout.createSequentialGroup()
                            .addComponent(descriptionCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(natureOfemergencyErr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(natureOfEmergencyCombo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(callerPhoneNumberErr, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(callersPhoneNumberTF, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(locateBtn))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        pickTheCallLayout.setVerticalGroup(
            pickTheCallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pickTheCallLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(pickTheCallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emergencyLocation)
                    .addComponent(locationEmergencyTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(locationErr, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(pickTheCallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phoneNumberOfCaller)
                    .addComponent(callersPhoneNumberTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(pickTheCallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(natureOfemergencyErr, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pickTheCallLayout.createSequentialGroup()
                        .addComponent(callerPhoneNumberErr, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pickTheCallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(natureOfEmergency)
                            .addComponent(natureOfEmergencyCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(pickTheCallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(description, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(descriptionCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)))
                .addGroup(pickTheCallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pickTheCallLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(descriptionErr, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(219, Short.MAX_VALUE))
                    .addGroup(pickTheCallLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(prioritySlider, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(184, Short.MAX_VALUE))
                    .addGroup(pickTheCallLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(priorityLabel)
                        .addGap(67, 67, 67)
                        .addGroup(pickTheCallLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(reportAnEmergencyBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(locateBtn))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTabbedPane1.addTab("Pick the call", pickTheCall);

        search911Dept.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        networkTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Network Name", "Public safety dispatch center"
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
        table.setViewportView(networkTable);

        findTheDistBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        findTheDistBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/EmergencyManagementSystem/images/1449767706_search.png"))); // NOI18N
        findTheDistBtn.setText("Find the distance");
        findTheDistBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findTheDistBtnActionPerformed(evt);
            }
        });

        bostonDitsLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        bostonDitsLabel.setText("Distance of emergency location from Boston PSAP:");

        camDistLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        camDistLabel.setText("Distance of emergency location from Cambridge PSAP:");

        maldenDistLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        maldenDistLabel.setText("Distance of emergency location from Malden PSAP:");

        closestPSAPLabel.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N

        routeCallBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        routeCallBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/EmergencyManagementSystem/images/1449767932_129_ArrowRight.png"))); // NOI18N
        routeCallBtn.setText("Route the call to the PSAP");
        routeCallBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                routeCallBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout search911DeptLayout = new javax.swing.GroupLayout(search911Dept);
        search911Dept.setLayout(search911DeptLayout);
        search911DeptLayout.setHorizontalGroup(
            search911DeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(search911DeptLayout.createSequentialGroup()
                .addGroup(search911DeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(search911DeptLayout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addGroup(search911DeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(search911DeptLayout.createSequentialGroup()
                                .addComponent(findTheDistBtn)
                                .addGap(34, 34, 34)
                                .addComponent(routeCallBtn)
                                .addGap(28, 28, 28))
                            .addGroup(search911DeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(bostonDitsLabel)
                                .addGroup(search911DeptLayout.createSequentialGroup()
                                    .addGroup(search911DeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(camDistLabel)
                                        .addComponent(maldenDistLabel))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(search911DeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(dist3TF, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(dist2TF, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(table, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(distTF1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(search911DeptLayout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(closestPSAPLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 541, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        search911DeptLayout.setVerticalGroup(
            search911DeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(search911DeptLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(table, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(search911DeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(routeCallBtn)
                    .addComponent(findTheDistBtn))
                .addGap(42, 42, 42)
                .addGroup(search911DeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bostonDitsLabel)
                    .addComponent(distTF1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(search911DeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dist2TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(camDistLabel))
                .addGap(26, 26, 26)
                .addGroup(search911DeptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maldenDistLabel)
                    .addComponent(dist3TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(closestPSAPLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(104, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Search for the 911 departments", search911Dept);

        manageEmergencySystem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        createNetworkPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        addNetworkTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Network name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        networkTab.setViewportView(addNetworkTable);

        networkNameLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        networkNameLabel.setText("Network name:");

        networkNameTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                networkNameTFFocusGained(evt);
            }
        });

        addNetworkBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        addNetworkBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/EmergencyManagementSystem/images/1449768059_More.png"))); // NOI18N
        addNetworkBtn.setText("Add network");
        addNetworkBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNetworkBtnActionPerformed(evt);
            }
        });

        networkNameErr.setBackground(new java.awt.Color(102, 102, 102));
        networkNameErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        networkNameErr.setForeground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout createNetworkPanelLayout = new javax.swing.GroupLayout(createNetworkPanel);
        createNetworkPanel.setLayout(createNetworkPanelLayout);
        createNetworkPanelLayout.setHorizontalGroup(
            createNetworkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createNetworkPanelLayout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addGroup(createNetworkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(networkTab, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(createNetworkPanelLayout.createSequentialGroup()
                        .addComponent(networkNameLabel)
                        .addGap(18, 18, 18)
                        .addGroup(createNetworkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(networkNameErr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(networkNameTF))))
                .addGap(139, 139, 139))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createNetworkPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addNetworkBtn)
                .addGap(237, 237, 237))
        );
        createNetworkPanelLayout.setVerticalGroup(
            createNetworkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createNetworkPanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(networkTab, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(createNetworkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(networkNameLabel)
                    .addComponent(networkNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(networkNameErr, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(addNetworkBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        managedEmeregencyPanel.addTab("Create networks", createNetworkPanel);

        createHospitalPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        hospitalNameTF.setEnabled(false);
        hospitalNameTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                hospitalNameTFFocusGained(evt);
            }
        });

        hospitalAddressTF.setEnabled(false);
        hospitalAddressTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                hospitalAddressTFFocusGained(evt);
            }
        });

        specialityTF.setEnabled(false);
        specialityTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                specialityTFFocusGained(evt);
            }
        });

        noOfBedsTF.setEnabled(false);
        noOfBedsTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                noOfBedsTFFocusGained(evt);
            }
        });
        noOfBedsTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noOfBedsTFActionPerformed(evt);
            }
        });

        noOfEmptyBedsTF.setEnabled(false);
        noOfEmptyBedsTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                noOfEmptyBedsTFFocusGained(evt);
            }
        });

        createHospitalHeader.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        createHospitalHeader.setText("Create Hospitals");

        addHospitalBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        addHospitalBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/EmergencyManagementSystem/images/1449768059_More.png"))); // NOI18N
        addHospitalBtn.setText("Add hospital");
        addHospitalBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addHospitalBtnActionPerformed(evt);
            }
        });

        hospitalNameLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        hospitalNameLabel.setText("Hospital Name:");

        hospitalAddressLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        hospitalAddressLabel.setText("Hospital Address:");

        specialityLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        specialityLabel.setText("Speciality:");

        noOfBedsLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        noOfBedsLabel.setText("Number of beds:");

        numberOfEmptyBeds.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        numberOfEmptyBeds.setText("Number of empty beds:");

        networkLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        networkLabel.setText("Network:");

        netCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        netCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                netComboActionPerformed(evt);
            }
        });

        hospNameErr.setBackground(new java.awt.Color(102, 102, 102));
        hospNameErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        hospNameErr.setForeground(new java.awt.Color(102, 102, 102));

        hospAddressErr.setBackground(new java.awt.Color(102, 102, 102));
        hospAddressErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        hospAddressErr.setForeground(new java.awt.Color(102, 102, 102));

        specilaityErr.setBackground(new java.awt.Color(102, 102, 102));
        specilaityErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        specilaityErr.setForeground(new java.awt.Color(102, 102, 102));

        noOfBedsErr.setBackground(new java.awt.Color(102, 102, 102));
        noOfBedsErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        noOfBedsErr.setForeground(new java.awt.Color(102, 102, 102));

        noOfEmptyBedsErr.setBackground(new java.awt.Color(102, 102, 102));
        noOfEmptyBedsErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        noOfEmptyBedsErr.setForeground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout createHospitalPanelLayout = new javax.swing.GroupLayout(createHospitalPanel);
        createHospitalPanel.setLayout(createHospitalPanelLayout);
        createHospitalPanelLayout.setHorizontalGroup(
            createHospitalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createHospitalPanelLayout.createSequentialGroup()
                .addGap(0, 153, Short.MAX_VALUE)
                .addGroup(createHospitalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(numberOfEmptyBeds)
                    .addComponent(noOfBedsLabel)
                    .addComponent(specialityLabel)
                    .addComponent(hospitalAddressLabel)
                    .addComponent(hospitalNameLabel)
                    .addComponent(networkLabel))
                .addGap(25, 25, 25)
                .addGroup(createHospitalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(specialityTF, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(noOfBedsTF, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(noOfEmptyBedsTF, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(specilaityErr, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(noOfBedsErr, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(noOfEmptyBedsErr, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addGroup(createHospitalPanelLayout.createSequentialGroup()
                        .addComponent(netCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(hospAddressErr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hospitalNameTF)
                    .addComponent(hospitalAddressTF)
                    .addComponent(hospNameErr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(93, 93, 93))
            .addGroup(createHospitalPanelLayout.createSequentialGroup()
                .addGap(228, 228, 228)
                .addComponent(createHospitalHeader)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createHospitalPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addHospitalBtn)
                .addGap(190, 190, 190))
        );
        createHospitalPanelLayout.setVerticalGroup(
            createHospitalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createHospitalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(createHospitalHeader)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(createHospitalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(networkLabel)
                    .addComponent(netCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(createHospitalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hospitalNameLabel)
                    .addComponent(hospitalNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hospNameErr, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(createHospitalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hospitalAddressLabel)
                    .addComponent(hospitalAddressTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hospAddressErr, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(createHospitalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(specialityTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(specialityLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(specilaityErr, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(createHospitalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(noOfBedsTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(noOfBedsLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(noOfBedsErr, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(createHospitalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numberOfEmptyBeds)
                    .addComponent(noOfEmptyBedsTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(noOfEmptyBedsErr, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addHospitalBtn)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        managedEmeregencyPanel.addTab("Create Hospitals", createHospitalPanel);

        createEnterprisePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        enterpriseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Enterprise Name", "Network", "Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(enterpriseTable);

        netLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        netLabel.setText("Network:");

        entTypeLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        entTypeLabel.setText("Enterprise type:");

        entNameLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        entNameLabel.setText("Enterprise Name:");

        entNameTf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                entNameTfFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                entNameTfFocusLost(evt);
            }
        });

        createEntBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        createEntBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/EmergencyManagementSystem/images/1449768059_More.png"))); // NOI18N
        createEntBtn.setText("Create Enterprise");
        createEntBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createEntBtnActionPerformed(evt);
            }
        });

        networkCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        entTypeCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        entTypeCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entTypeComboActionPerformed(evt);
            }
        });

        ent911AddressTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ent911AddressTFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                ent911AddressTFFocusLost(evt);
            }
        });
        ent911AddressTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ent911AddressTFActionPerformed(evt);
            }
        });

        ent911AddressLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ent911AddressLabel.setText("911 Enterprise Address:");

        entNameErr.setBackground(new java.awt.Color(102, 102, 102));
        entNameErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        entNameErr.setForeground(new java.awt.Color(102, 102, 102));

        entAddressErr.setBackground(new java.awt.Color(102, 102, 102));
        entAddressErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        entAddressErr.setForeground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout createEnterprisePanelLayout = new javax.swing.GroupLayout(createEnterprisePanel);
        createEnterprisePanel.setLayout(createEnterprisePanelLayout);
        createEnterprisePanelLayout.setHorizontalGroup(
            createEnterprisePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createEnterprisePanelLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createEnterprisePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(createEnterprisePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createEnterprisePanelLayout.createSequentialGroup()
                        .addGroup(createEnterprisePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(createEnterprisePanelLayout.createSequentialGroup()
                                .addComponent(ent911AddressLabel)
                                .addGap(18, 18, 18)
                                .addComponent(ent911AddressTF, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(createEnterprisePanelLayout.createSequentialGroup()
                                .addGroup(createEnterprisePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(netLabel)
                                    .addComponent(entTypeLabel)
                                    .addComponent(entNameLabel))
                                .addGap(56, 56, 56)
                                .addGroup(createEnterprisePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(entTypeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(networkCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(entNameTf, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(132, 132, 132))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createEnterprisePanelLayout.createSequentialGroup()
                        .addGroup(createEnterprisePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(entNameErr, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(entAddressErr, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(112, 112, 112))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createEnterprisePanelLayout.createSequentialGroup()
                        .addComponent(createEntBtn)
                        .addGap(187, 187, 187))))
        );
        createEnterprisePanelLayout.setVerticalGroup(
            createEnterprisePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createEnterprisePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(createEnterprisePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(netLabel)
                    .addComponent(networkCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(createEnterprisePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(entTypeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(entTypeLabel))
                .addGap(18, 18, 18)
                .addGroup(createEnterprisePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(entNameTf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(entNameLabel))
                .addGap(5, 5, 5)
                .addGroup(createEnterprisePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(createEnterprisePanelLayout.createSequentialGroup()
                        .addGroup(createEnterprisePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(createEnterprisePanelLayout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(ent911AddressLabel))
                            .addComponent(entNameErr, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9))
                    .addGroup(createEnterprisePanelLayout.createSequentialGroup()
                        .addComponent(ent911AddressTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(entAddressErr, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(createEntBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        managedEmeregencyPanel.addTab("Create enterprises", createEnterprisePanel);

        createEntAdminPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        entAdminTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Enterprise name", "Network", "Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(entAdminTable);

        netwrkL.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        netwrkL.setText("Network:");

        enterpriseLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        enterpriseLabel.setText("Enterprise:");

        entAdNameL.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        entAdNameL.setText("Name:");

        entAdminUserL.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        entAdminUserL.setText("UserName:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("Password:");

        name_AdminTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                name_AdminTFFocusGained(evt);
            }
        });
        name_AdminTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                name_AdminTFActionPerformed(evt);
            }
        });

        username_Admin_TF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                username_Admin_TFFocusGained(evt);
            }
        });

        networkAdminCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        networkAdminCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                networkAdminComboActionPerformed(evt);
            }
        });

        entAdminCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        createAdminButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        createAdminButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/EmergencyManagementSystem/images/1449791895_user_male2.png"))); // NOI18N
        createAdminButton.setText("Create admin");
        createAdminButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createAdminButtonActionPerformed(evt);
            }
        });

        passwordAdminTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passwordAdminTFFocusGained(evt);
            }
        });

        nameAdminErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        nameAdminErr.setForeground(new java.awt.Color(102, 102, 102));

        passwordAdminErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        passwordAdminErr.setForeground(new java.awt.Color(102, 102, 102));

        usernameAdminErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        usernameAdminErr.setForeground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout createEntAdminPanelLayout = new javax.swing.GroupLayout(createEntAdminPanel);
        createEntAdminPanel.setLayout(createEntAdminPanelLayout);
        createEntAdminPanelLayout.setHorizontalGroup(
            createEntAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createEntAdminPanelLayout.createSequentialGroup()
                .addGap(0, 70, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
            .addGroup(createEntAdminPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(createEntAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(netwrkL)
                    .addComponent(enterpriseLabel)
                    .addComponent(entAdNameL)
                    .addComponent(entAdminUserL)
                    .addComponent(jLabel14))
                .addGap(80, 80, 80)
                .addGroup(createEntAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(passwordAdminErr, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(createEntAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(entAdminCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(networkAdminCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(name_AdminTF)
                        .addComponent(nameAdminErr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(username_Admin_TF, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(usernameAdminErr, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(passwordAdminTF, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createEntAdminPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(createAdminButton)
                .addGap(207, 207, 207))
        );
        createEntAdminPanelLayout.setVerticalGroup(
            createEntAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createEntAdminPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(createEntAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(netwrkL)
                    .addComponent(networkAdminCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(createEntAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(enterpriseLabel)
                    .addComponent(entAdminCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(createEntAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(name_AdminTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(entAdNameL))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameAdminErr, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(createEntAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(username_Admin_TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(entAdminUserL))
                .addGap(1, 1, 1)
                .addComponent(usernameAdminErr, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(createEntAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordAdminTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(passwordAdminErr, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(createAdminButton)
                .addContainerGap())
        );

        managedEmeregencyPanel.addTab("Create enterprise admin", createEntAdminPanel);

        createHospitalAdminPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        networkJLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        networkJLabel.setText("Network");

        networkJCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        networkJCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                networkJComboActionPerformed(evt);
            }
        });

        hospitalTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hospital Name", "Network"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(hospitalTable);
        if (hospitalTable.getColumnModel().getColumnCount() > 0) {
            hospitalTable.getColumnModel().getColumn(0).setMinWidth(300);
            hospitalTable.getColumnModel().getColumn(0).setPreferredWidth(300);
            hospitalTable.getColumnModel().getColumn(0).setMaxWidth(300);
        }

        nameLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        nameLabel.setText("Name:");

        userNameLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        userNameLabel.setText("Username:");

        passwordLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        passwordLabel.setText("Password:");

        hospitalAdminName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                hospitalAdminNameFocusGained(evt);
            }
        });

        hospitalAdminUsernameTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                hospitalAdminUsernameTFFocusGained(evt);
            }
        });

        hospitalAdminPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                hospitalAdminPasswordFocusGained(evt);
            }
        });
        hospitalAdminPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hospitalAdminPasswordActionPerformed(evt);
            }
        });

        createAdminBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        createAdminBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/EmergencyManagementSystem/images/1449791895_user_male2.png"))); // NOI18N
        createAdminBtn.setText("Create Admin");
        createAdminBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createAdminBtnActionPerformed(evt);
            }
        });

        nameErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        nameErr.setForeground(new java.awt.Color(102, 102, 102));

        passErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        passErr.setForeground(new java.awt.Color(102, 102, 102));

        userNaemErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        userNaemErr.setForeground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout createHospitalAdminPanelLayout = new javax.swing.GroupLayout(createHospitalAdminPanel);
        createHospitalAdminPanel.setLayout(createHospitalAdminPanelLayout);
        createHospitalAdminPanelLayout.setHorizontalGroup(
            createHospitalAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createHospitalAdminPanelLayout.createSequentialGroup()
                .addGroup(createHospitalAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(createHospitalAdminPanelLayout.createSequentialGroup()
                        .addGap(208, 208, 208)
                        .addComponent(networkJLabel)
                        .addGap(18, 18, 18)
                        .addComponent(networkJCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(createHospitalAdminPanelLayout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addGroup(createHospitalAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(userNameLabel)
                            .addComponent(nameLabel)
                            .addComponent(passwordLabel))
                        .addGap(52, 52, 52)
                        .addGroup(createHospitalAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(hospitalAdminPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                            .addComponent(passErr, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                            .addComponent(userNaemErr, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                            .addComponent(hospitalAdminName)
                            .addComponent(nameErr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hospitalAdminUsernameTF))))
                .addContainerGap(140, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createHospitalAdminPanelLayout.createSequentialGroup()
                .addContainerGap(94, Short.MAX_VALUE)
                .addGroup(createHospitalAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createHospitalAdminPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createHospitalAdminPanelLayout.createSequentialGroup()
                        .addComponent(createAdminBtn)
                        .addGap(197, 197, 197))))
        );
        createHospitalAdminPanelLayout.setVerticalGroup(
            createHospitalAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createHospitalAdminPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(createHospitalAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(networkJCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(networkJLabel))
                .addGap(21, 21, 21)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addGroup(createHospitalAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(hospitalAdminName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameErr, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(createHospitalAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hospitalAdminUsernameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userNameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userNaemErr, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(createHospitalAdminPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel)
                    .addComponent(hospitalAdminPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(passErr, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(createAdminBtn)
                .addContainerGap(61, Short.MAX_VALUE))
        );

        managedEmeregencyPanel.addTab("Create Hospital Admin", createHospitalAdminPanel);

        javax.swing.GroupLayout manageEmergencySystemLayout = new javax.swing.GroupLayout(manageEmergencySystem);
        manageEmergencySystem.setLayout(manageEmergencySystemLayout);
        manageEmergencySystemLayout.setHorizontalGroup(
            manageEmergencySystemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageEmergencySystemLayout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addComponent(managedEmeregencyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 597, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );
        manageEmergencySystemLayout.setVerticalGroup(
            manageEmergencySystemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageEmergencySystemLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(managedEmeregencyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(140, 140, 140))
        );

        jTabbedPane1.addTab("Manage the emergency system", manageEmergencySystem);

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        timeFrameTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Emergency location", "T1", "T2", "T3", "T4"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Long.class, java.lang.Long.class, java.lang.Long.class, java.lang.Long.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(timeFrameTable);
        if (timeFrameTable.getColumnModel().getColumnCount() > 0) {
            timeFrameTable.getColumnModel().getColumn(0).setMinWidth(300);
            timeFrameTable.getColumnModel().getColumn(0).setPreferredWidth(300);
            timeFrameTable.getColumnModel().getColumn(0).setMaxWidth(300);
        }

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Emergency time tracking");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(262, 262, 262)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(328, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Generate reports", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 694, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(145, 145, 145))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(header)
                .addGap(32, 32, 32)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(86, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void routeCallBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_routeCallBtnActionPerformed
        // TODO add your handling code here:
        int rowNumber=networkTable.getSelectedRow();
        if(rowNumber>=0)
        {

            Network n= (Network) networkTable.getValueAt(rowNumber,0);
            for(Enterprise ent:n.getEntDirObj().getEnterpriseList())
            {
                if(ent instanceof Emergency911Enterprise)
                {
                    UserAccount userAcc=ent.getUserAccountDirectory().getUserAccountList().get(0);
                     Emergency911DepartmentWorkRequest request=new Emergency911DepartmentWorkRequest();
                     request.setEmployee(ent.getEmployeeDirectory().getEmployeeList().get(0));
                     request.setEmergency(e);
                     e.setEmergencyStatus("Assigned to PSAP");
                     request.setReceiver(userAcc);
                     request.setSender(system.getUserAccountDirectory().getUserAccountList().get(0));
                     userAcc.getWorkQueue().getWorkRequestList().add(request);
                     routeCallBtn.setEnabled(false);
                     
                     request.setEmployee(userAccount.getEmployee());
                     request.setEmergency(e);
                     request.setReceiver(userAccount);
                     request.setSender(userAccount);
                     userAccount.getWorkQueue().getWorkRequestList().add(request);
                   //  populateTimeReportTable();
//                     Date d=new Date();
//                     e.setPsapAlerted(d);
//                     
                }
            }

            JOptionPane.showMessageDialog(this,"The emergency has been routed to the nearest PSAP");
        }

        else

        {
            JOptionPane.showMessageDialog(null, "Please select a row from the table", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_routeCallBtnActionPerformed

    private void findTheDistBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findTheDistBtnActionPerformed

        // TODO add your handling code here:

        try{
            URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?origins="+location+"&destinations=360+Huntington+Ave,+Boston,+MA+02115|1350+Massachusetts+Avenue+Cambridge,+MA+02138|77+Salem+St,+Malden,+MA+02148&key=AIzaSyAUftFKfNIO2RI64ZJM0joAG6Xtnolpc_8");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            String line, outputString = "";
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                outputString += line;
            }
                //System.out.println("outputString >>>> "+outputString);

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
             
                distTF1.setText(distanceStringArray[0]);
                dist2TF.setText(distanceStringArray[1]);
                dist3TF.setText(distanceStringArray[2]);

            }catch(Exception e){
                System.out.println("The exception occured >> "+e.getMessage());
            }

            DistancePojo capRes = new Gson().fromJson(outputString, DistancePojo.class);
            //  System.out.println("capRes >> "+capRes);
        
      
            
        }

        catch(Exception e)
        {
            System.err.println("The exception is "+e);
        }

        String distance1 =distTF1.getText();
        String dist1="null";
        String dist2="null";
        String dist3="null";
        int p1 = distance1.indexOf(' ');
        if (p1 >= 0)
        {
            dist1 = distance1.substring(0, p1);
        }

        String distance2 =dist2TF.getText();
        int p2 = distance2.indexOf(' ');
        if (p2 >= 0)
        {
            dist2 = distance2.substring(0, p2);
        }

        String distance3 =dist3TF.getText();
        int p3 = distance3.indexOf(' ');
        if (p3 >= 0)
        {
            dist3 = distance3.substring(0, p3);
        }

        float d1=Float.parseFloat(dist1);
        float d2=Float.parseFloat(dist2);
        float d3=Float.parseFloat(dist3);

        float smallest;
        if(d1<d2 && d1<d3)
        {
            smallest = d1;
            closestPSAPLabel.setText("The closest PSAP is the Boston PSAP which is at "+smallest+" km distance from the emergency location");
        }
        else if(d2<d3)
        {
            smallest = d2;
            closestPSAPLabel.setText("The closest PSAP is the Cambridge PSAP which is at "+smallest+" km distance from the emergency location");
        }
        else
        {
            smallest = d3;
            closestPSAPLabel.setText("The closest PSAP is the Malden PSAP which is at "+smallest+" km distance from the emergency location");
        }

    }//GEN-LAST:event_findTheDistBtnActionPerformed

    private void locateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_locateBtnActionPerformed
        // TODO add your handling code here:
        try
        {
//            
//        Browser browser = BrowserFactory.create();
//        JFrame frame = new JFrame("JxBrowser Google Maps");
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.add(browser.getView().getComponent(), BorderLayout.CENTER);
//        frame.setSize(700, 500);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//        browser.loadURL("http://maps.google.com");
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);
        JFrame frame = new JFrame("Emergency Location");
        
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        browser.loadURL("http://maps.google.com/?q="+location);
        }
        catch(Exception e)
        {
        e.printStackTrace();
        }
       
    }//GEN-LAST:event_locateBtnActionPerformed

    private void reportAnEmergencyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportAnEmergencyBtnActionPerformed
        // TODO add your handling code here:
        
        if(callersPhoneNumberTF.getText().matches("[0-9]+")&& (callersPhoneNumberTF.getText().length()==10))
       
        {
        callerPhoneNumberErr.setText("");
        findTheDistBtn.setEnabled(true);
        routeCallBtn.setEnabled(true);
        e=emergencyDirectory.createEmergency();
        e.setLocationOfEmergency(locationEmergencyTF.getText());
        e.setCallersPhoneNumber(callersPhoneNumberTF.getText());
        e.setNatureOfEmergency((String) natureOfEmergencyCombo.getSelectedItem());
        e.setDescription((String) descriptionCombo.getSelectedItem());
        e.setPriority(prioritySlider.getValue());
        e.setEmergencyStatus("Reported");
        Date d = new Date();
        e.setReportedTime(d);
        JOptionPane.showMessageDialog(this,"Emergency has been reported!");
        locateBtn.setEnabled(true);
        reportAnEmergencyBtn.setEnabled(false);
        callersPhoneNumberTF.setEditable(false);
        location=e.getLocationOfEmergency().replaceAll("\\s","+");
        }

       else
        {
           
            if(!callersPhoneNumberTF.getText().matches("[0-9]+"))
            {
            callerPhoneNumberErr.setText("Enter only numeric values");
            }
            
            else
            {
                callerPhoneNumberErr.setText("Enter a 10 digit numeric value");
            }
        }
    }//GEN-LAST:event_reportAnEmergencyBtnActionPerformed

    private void natureOfEmergencyComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_natureOfEmergencyComboActionPerformed
        // TODO add your handling code here:
        String emrg = (String) natureOfEmergencyCombo.getSelectedItem();
        if (emrg != null){
            populateDescriptionComboBox(emrg);
        }
    }//GEN-LAST:event_natureOfEmergencyComboActionPerformed

    private void addHospitalBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addHospitalBtnActionPerformed
        // TODO add your handling code here:
        
        if(hospitalNameTF.getText().trim().matches("[a-zA-Z0-9 ]+")&&(!hospitalAddressTF.getText().trim().equals(""))&&(specialityTF.getText().trim().matches("[a-zA-Z0-9 ]+"))&&(noOfBedsTF.getText().trim().matches("[0-9]+"))&&(noOfEmptyBedsTF.getText().trim().matches("[0-9]+")))
        {
            for(Network n:system.getNetworkList())
            {
                for(Hospital h:n.getHospitalList())
                {
                    if(h.getHospitalName().equalsIgnoreCase(hospitalNameTF.getText()))
                    {
                        hospitalAlreadyPresent=true;
                        break;
                    }
                }
                
                if(hospitalAlreadyPresent==true)
                    break;
                
            }
            
            if(hospitalAlreadyPresent==true)
            {
                hospNameErr.setText("The hospital with same name exists");
            }
            
            else
            {

            Network network=(Network) netCombo.getSelectedItem();
            if(network!=null)
            {
            Hospital h=network.addHospital(hospitalNameTF.getText());
            h.setHospitalName(hospitalNameTF.getText().trim());
            h.setHospitalLocation(hospitalAddressTF.getText().trim());
            h.setSpeciality(specialityTF.getText().trim());
            h.setNumberOfBeds(Integer.parseInt(noOfBedsTF.getText().trim()));
            h.setNumberOfEmptyBeds(Integer.parseInt(noOfEmptyBedsTF.getText().trim()));  
            JOptionPane.showMessageDialog(this,"The hospital is successfully added to the network");
            hospitalNameTF.setText("");
            hospitalAddressTF.setText("");
            specialityTF.setText("");
            noOfBedsTF.setText("");
            noOfEmptyBedsTF.setText("");
 
            }
            
            }
            
        }
        
        else
        {
            if(!hospitalNameTF.getText().trim().matches("[a-zA-Z0-9 ]+"))
            {
                hospNameErr.setText("Enter a valid hospital name");
            }
            
            if(hospitalAddressTF.getText().trim().equals(""))
            {
                hospAddressErr.setText("Enter a valid hospital address");
            
            }
            
            if(hospitalAddressTF.getText().trim().equals(""))
            {
                hospAddressErr.setText("Enter a valid hospital address");
            }
            
            if(!specialityTF.getText().trim().matches("[a-zA-Z0-9 ]+"))
            {
                specilaityErr.setText("Enter a valid speciality");
            }
            
            if(!noOfBedsTF.getText().trim().matches("[0-9]+"))
            {
                noOfBedsErr.setText("Enter valid no of beds");
            }
            
            if(!noOfEmptyBedsTF.getText().trim().matches("[0-9]+"))
            {
                noOfEmptyBedsErr.setText("Enter valid no of empty beds");
            }
        }
        
    }//GEN-LAST:event_addHospitalBtnActionPerformed

    private void createAdminButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createAdminButtonActionPerformed
        // TODO add your handling code here:
        entUsernameIsUnique=false;
        String username = username_Admin_TF.getText().trim();
        char[] passwordCharArray = passwordAdminTF.getPassword();
        String password = String.valueOf(passwordCharArray).trim();
        if(name_AdminTF.getText().trim().matches("[a-zA-Z0-9 ]+")&&username_Admin_TF.getText().trim().matches("[a-zA-Z0-9]+")&&(password.matches("[a-zA-Z0-9]+")))
        {
            entUsernameIsUnique=system.checkIfUserNameIsUnique(username, password);
              if(entUsernameIsUnique==true)
            {
         Enterprise enterprise = (Enterprise) entAdminCombo.getSelectedItem();

        String name = name_AdminTF.getText().trim();

        EnterpriseAdmin admin=new EnterpriseAdmin();
        enterprise.getEmployeeDirectory().createEmployee(admin);
        enterprise.getUserAccountDirectory().createUserAccount(username, password, admin, new Emergency911EnterpriseAdminRole());
        JOptionPane.showMessageDialog(this,"The enterprise admin is successfully created");

        // UserAccount account = enterprise.getUserAccountDirectory().createUserAccount(username, password, employee, new AdminRole());
        populateAdminDetailsTable();
        name_AdminTF.setText("");
        username_Admin_TF.setText("");
        passwordAdminTF.setText("");

            
        Network network=(Network) networkJCombo.getSelectedItem();
        }
        else
            {
                usernameAdminErr.setText("Username already exists");
            }  
            
        }
        
        else
        {
        if(!name_AdminTF.getText().trim().matches("[a-zA-Z0-9 ]+"))
            {
                nameAdminErr.setText("Enter a valid name");
            }
        
        if(!username_Admin_TF.getText().trim().matches("[a-zA-Z0-9]+"))
            {
                usernameAdminErr.setText("Enter a valid username");   
            }
        if(!password.matches("[a-zA-Z0-9]+"))
            {
                passwordAdminErr.setText("Enter a valid password");
                
            }
        
        }
       
    }//GEN-LAST:event_createAdminButtonActionPerformed

    private void networkAdminComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_networkAdminComboActionPerformed
        // TODO add your handling code here:
        Network network = (Network) networkAdminCombo.getSelectedItem();
        if (network != null){
            populateEnterpriseAdminComboBox(network);
           
        }
    }//GEN-LAST:event_networkAdminComboActionPerformed

    private void ent911AddressTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ent911AddressTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ent911AddressTFActionPerformed

    private void entTypeComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entTypeComboActionPerformed
        // TODO add your handling code here:
        Enterprise.EnterpriseType type = (Enterprise.EnterpriseType) entTypeCombo.getSelectedItem();
        if(type==EnterpriseType.EMEREGENCY911ENTERPRISE)
        {
            ent911AddressTF.setVisible(true);
            ent911AddressLabel.setVisible(true);
        }
        else
        {
            ent911AddressTF.setVisible(false);
            ent911AddressLabel.setVisible(false);
        }
    }//GEN-LAST:event_entTypeComboActionPerformed

    private void createEntBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createEntBtnActionPerformed
        // TODO add your handling code here:
      
        enterpriseAlreadyPresent=false;
        Network network = (Network) networkCombo.getSelectedItem();
        Enterprise.EnterpriseType type = (Enterprise.EnterpriseType) entTypeCombo.getSelectedItem();

        if (network == null || type == null) {
            JOptionPane.showMessageDialog(null, "Kindly choose both the options to proceed further");
            return;
        }

        String name = entNameTf.getText().trim();
        if(type==EnterpriseType.EMEREGENCY911ENTERPRISE)
        {
            if(entNameTf.getText().trim().matches("[a-zA-Z0-9 ]+")&&(!ent911AddressTF.getText().trim().equals("")))
            {
                for(Network n:system.getNetworkList())
                {
                for(Enterprise ent:n.getEntDirObj().getEnterpriseList())
                {
                   if( ent.getName().equalsIgnoreCase(entNameTf.getText()))
                   {
                      enterpriseAlreadyPresent=true;
                      break;
                   }
                }
                if(enterpriseAlreadyPresent==true)
                    entNameErr.setText("This enterprise already exists");
                      break;
                }
                
                if(enterpriseAlreadyPresent==false)
                {
                     Enterprise enterprise = network.getEntDirObj().createAndAddEnterprise(name, type,ent911AddressTF.getText().trim());
                     JOptionPane.showMessageDialog(this,"The enterprise has been succesfully added to the network");
                     entNameTf.setText("");
                     ent911AddressTF.setText("");
                     populateEntTable();
                     populateAdminNetworkCombo();
                }
                
            }
            
            else
            {
                if(!entNameTf.getText().trim().matches("[a-zA-Z0-9 ]+"))
                {
                entNameErr.setText("Enter a valid enterprise name");
                }
                
                if(ent911AddressTF.getText().equals(""))
                {
                    entAddressErr.setText("Enter a valid enterprise address");
                }
            }
           
        }
        else
        {
            if(entNameTf.getText().trim().matches("[a-zA-Z0-9 ]+"))
            {
                for(Network n:system.getNetworkList())
                {
                for(Enterprise ent:n.getEntDirObj().getEnterpriseList())
                {
                   if( ent.getName().equalsIgnoreCase(entNameTf.getText()))
                   {
                      enterpriseAlreadyPresent=true;
                      break;
                   }
                }
                if(enterpriseAlreadyPresent==true)
                     entNameErr.setText("This enterprise already exists");
                      break;
                }
                
                if(enterpriseAlreadyPresent==false)
                {
                     Enterprise enterprise = network.getEntDirObj().createAndAddEnterprise(name, type,ent911AddressTF.getText().trim());
                     JOptionPane.showMessageDialog(this,"The enterprise has been succesfully added to the network");
                     entNameTf.setText("");
                     ent911AddressTF.setText("");
                     populateEntTable();
                     populateAdminNetworkCombo();
                }
            }
            
            else
            {
                if(!entNameTf.getText().matches("[a-zA-Z0-9]+"))
                {
                entNameErr.setText("Enter a valid enterprise name");
                }
            }
            
        }
        
    }//GEN-LAST:event_createEntBtnActionPerformed

    private void addNetworkBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNetworkBtnActionPerformed
        // TODO add your handling code here:
       networkAlreadyPresent=false;
        if(networkNameTF.getText().trim().matches("[a-zA-Z0-9 ]+"))
        {
           
            for(Network net:system.getNetworkList())
            {
                if(net.getNetworkName().equalsIgnoreCase(networkNameTF.getText()))
                {
                   networkAlreadyPresent=true;
                   break;
                }
     
            }  
            
            if(networkAlreadyPresent==false)
        {
            if(networkNameErr.getText().equals(""))
            {
             Network n=system.addNetwork();
             n.setNetworkName(networkNameTF.getText().trim());
             JOptionPane.showMessageDialog(this,"The network is successfully created");
             populateNetworkTable();
             networkNameTF.setText("");
             populateCombo();
            }
        }  
         
        else
         {
            networkNameErr.setText("This network already exists");
         }
       
                
        }
        
        else
        {   
            networkNameErr.setText("Enter the network name");
        } 
        
       
    }//GEN-LAST:event_addNetworkBtnActionPerformed

    private void netComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_netComboActionPerformed
        // TODO add your handling code here:
        Network network=(Network) netCombo.getSelectedItem();
        if(network!=null)
        {
            hospitalNameTF.setEnabled(true);
            hospitalAddressTF.setEnabled(true);
            specialityTF.setEnabled(true);
            noOfBedsTF.setEnabled(true);
            noOfEmptyBedsTF.setEnabled(true);
           
        }
    }//GEN-LAST:event_netComboActionPerformed

    private void hospitalAdminPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hospitalAdminPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hospitalAdminPasswordActionPerformed

    private void networkJComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_networkJComboActionPerformed
        // TODO add your handling code here:
        Network network=(Network) networkJCombo.getSelectedItem();
       if(network!=null)
       {
           populateHospitalTable(network);
       }
        
    }//GEN-LAST:event_networkJComboActionPerformed

    private void createAdminBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createAdminBtnActionPerformed
        // TODO add your handling code here:
        char[] passwordCharArray = hospitalAdminPassword.getPassword();
        String password = String.valueOf(passwordCharArray).trim(); 
        String username=hospitalAdminUsernameTF.getText().trim();
        int rowSelected=hospitalTable.getSelectedRow();
        if(rowSelected>=0)
        {
        if(hospitalAdminName.getText().trim().matches("[a-zA-Z0-9 ]+")&&(hospitalAdminUsernameTF.getText().trim().matches("[a-zA-Z0-9]+"))&&(password.matches("[a-zA-Z0-9]+")))
        {
            userNameIsUnique=system.checkIfUserNameIsUnique(username, password);

            if(userNameIsUnique==true)
            {
        Hospital h=(Hospital) hospitalTable.getValueAt(rowSelected,0);
        HospitalEnterpriseAdmin admin=new HospitalEnterpriseAdmin();
        admin.setName(hospitalAdminName.getText());
        h.getEmployeeDirectory().createEmployee(admin);
       
        h.getUserAccountDirectory().createUserAccount(username,password,admin,new HospitalEnterpriseAdminRole());
        JOptionPane.showMessageDialog(this,"The hospital admin is successfully created");
        hospitalAdminName.setText("");
        hospitalAdminUsernameTF.setText("");
        hospitalAdminPassword.setText("");

            
        Network network=(Network) networkJCombo.getSelectedItem();
        }
        else
            {
                userNaemErr.setText("Username already exists");
            }  
            }
              
       
             else
        {
            if(!hospitalAdminName.getText().matches("[a-zA-Z0-9]+"))
            {
                nameErr.setText("Enter a valid name");
            }
            
            if(!hospitalAdminUsernameTF.getText().matches("[a-zA-Z0-9]+"))
            {
                userNaemErr.setText("Enter a valid username");
            }
            
            if(!password.matches("[a-zA-Z0-9]+"))
            {
                passErr.setText("Enter a valid password");
            }
        }
       
            }

    else 
        {
            JOptionPane.showMessageDialog(this,"Please choose a hospital from the table");
        }
        
    }//GEN-LAST:event_createAdminBtnActionPerformed

    private void networkNameTFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_networkNameTFFocusGained
        // TODO add your handling code here:
        networkNameErr.setText("");
    }//GEN-LAST:event_networkNameTFFocusGained

    private void entNameTfFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_entNameTfFocusLost
        // TODO add your handling code here:
        entNameErr.setText("");
    }//GEN-LAST:event_entNameTfFocusLost

    private void ent911AddressTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ent911AddressTFFocusLost
        // TODO add your handling code here:
       
    }//GEN-LAST:event_ent911AddressTFFocusLost

    private void entNameTfFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_entNameTfFocusGained
        // TODO add your handling code here:
         
    }//GEN-LAST:event_entNameTfFocusGained

    private void ent911AddressTFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ent911AddressTFFocusGained
        // TODO add your handling code here:
        entAddressErr.setText("");
    }//GEN-LAST:event_ent911AddressTFFocusGained

    private void hospitalNameTFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_hospitalNameTFFocusGained
        // TODO add your handling code here:
        hospNameErr.setText("");
    }//GEN-LAST:event_hospitalNameTFFocusGained

    private void hospitalAddressTFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_hospitalAddressTFFocusGained
        // TODO add your handling code here:
       hospAddressErr.setText("");
    }//GEN-LAST:event_hospitalAddressTFFocusGained

    private void specialityTFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_specialityTFFocusGained
        // TODO add your handling code here:
        specilaityErr.setText("");
    }//GEN-LAST:event_specialityTFFocusGained

    private void noOfBedsTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noOfBedsTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noOfBedsTFActionPerformed

    private void noOfBedsTFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_noOfBedsTFFocusGained
        // TODO add your handling code here:
       noOfBedsErr.setText("");
    }//GEN-LAST:event_noOfBedsTFFocusGained

    private void noOfEmptyBedsTFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_noOfEmptyBedsTFFocusGained
        // TODO add your handling code here:
        noOfEmptyBedsErr.setText("");
    }//GEN-LAST:event_noOfEmptyBedsTFFocusGained

    private void hospitalAdminNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_hospitalAdminNameFocusGained
        // TODO add your handling code here:
        nameErr.setText("");
    }//GEN-LAST:event_hospitalAdminNameFocusGained

    private void hospitalAdminUsernameTFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_hospitalAdminUsernameTFFocusGained
        // TODO add your handling code here:
        userNaemErr.setText("");
    }//GEN-LAST:event_hospitalAdminUsernameTFFocusGained

    private void hospitalAdminPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_hospitalAdminPasswordFocusGained
        // TODO add your handling code here:
        passErr.setText("");
    }//GEN-LAST:event_hospitalAdminPasswordFocusGained

    private void name_AdminTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_name_AdminTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_name_AdminTFActionPerformed

    private void name_AdminTFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_name_AdminTFFocusGained
        // TODO add your handling code here:
        nameAdminErr.setText("");
    }//GEN-LAST:event_name_AdminTFFocusGained

    private void username_Admin_TFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_username_Admin_TFFocusGained
        // TODO add your handling code here:
        usernameAdminErr.setText("");
    }//GEN-LAST:event_username_Admin_TFFocusGained

    private void passwordAdminTFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordAdminTFFocusGained
        // TODO add your handling code here:
        passwordAdminErr.setText("");
    }//GEN-LAST:event_passwordAdminTFFocusGained


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addHospitalBtn;
    private javax.swing.JButton addNetworkBtn;
    private javax.swing.JTable addNetworkTable;
    private javax.swing.JLabel bostonDitsLabel;
    private javax.swing.JLabel callerPhoneNumberErr;
    private javax.swing.JTextField callersPhoneNumberTF;
    private javax.swing.JLabel camDistLabel;
    private javax.swing.JLabel closestPSAPLabel;
    private javax.swing.JButton createAdminBtn;
    private javax.swing.JButton createAdminButton;
    private javax.swing.JPanel createEntAdminPanel;
    private javax.swing.JButton createEntBtn;
    private javax.swing.JPanel createEnterprisePanel;
    private javax.swing.JPanel createHospitalAdminPanel;
    private javax.swing.JLabel createHospitalHeader;
    private javax.swing.JPanel createHospitalPanel;
    private javax.swing.JPanel createNetworkPanel;
    private javax.swing.JLabel description;
    private javax.swing.JComboBox descriptionCombo;
    private javax.swing.JLabel descriptionErr;
    private javax.swing.JTextField dist2TF;
    private javax.swing.JTextField dist3TF;
    private javax.swing.JTextField distTF1;
    private javax.swing.JLabel emergencyLocation;
    private javax.swing.JLabel ent911AddressLabel;
    private javax.swing.JTextField ent911AddressTF;
    private javax.swing.JLabel entAdNameL;
    private javax.swing.JLabel entAddressErr;
    private javax.swing.JComboBox entAdminCombo;
    private javax.swing.JTable entAdminTable;
    private javax.swing.JLabel entAdminUserL;
    private javax.swing.JLabel entNameErr;
    private javax.swing.JLabel entNameLabel;
    private javax.swing.JTextField entNameTf;
    private javax.swing.JComboBox entTypeCombo;
    private javax.swing.JLabel entTypeLabel;
    private javax.swing.JLabel enterpriseLabel;
    private javax.swing.JTable enterpriseTable;
    private javax.swing.JButton findTheDistBtn;
    private javax.swing.JLabel header;
    private javax.swing.JLabel hospAddressErr;
    private javax.swing.JLabel hospNameErr;
    private javax.swing.JLabel hospitalAddressLabel;
    private javax.swing.JTextField hospitalAddressTF;
    private javax.swing.JTextField hospitalAdminName;
    private javax.swing.JPasswordField hospitalAdminPassword;
    private javax.swing.JTextField hospitalAdminUsernameTF;
    private javax.swing.JLabel hospitalNameLabel;
    private javax.swing.JTextField hospitalNameTF;
    private javax.swing.JTable hospitalTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton locateBtn;
    private javax.swing.JTextField locationEmergencyTF;
    private javax.swing.JLabel locationErr;
    private javax.swing.JLabel maldenDistLabel;
    private javax.swing.JPanel manageEmergencySystem;
    private javax.swing.JTabbedPane managedEmeregencyPanel;
    private javax.swing.JLabel nameAdminErr;
    private javax.swing.JLabel nameErr;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField name_AdminTF;
    private javax.swing.JLabel natureOfEmergency;
    private javax.swing.JComboBox natureOfEmergencyCombo;
    private javax.swing.JLabel natureOfemergencyErr;
    private javax.swing.JComboBox netCombo;
    private javax.swing.JLabel netLabel;
    private javax.swing.JComboBox networkAdminCombo;
    private javax.swing.JComboBox networkCombo;
    private javax.swing.JComboBox networkJCombo;
    private javax.swing.JLabel networkJLabel;
    private javax.swing.JLabel networkLabel;
    private javax.swing.JLabel networkNameErr;
    private javax.swing.JLabel networkNameLabel;
    private javax.swing.JTextField networkNameTF;
    private javax.swing.JScrollPane networkTab;
    private javax.swing.JTable networkTable;
    private javax.swing.JLabel netwrkL;
    private javax.swing.JLabel noOfBedsErr;
    private javax.swing.JLabel noOfBedsLabel;
    private javax.swing.JTextField noOfBedsTF;
    private javax.swing.JLabel noOfEmptyBedsErr;
    private javax.swing.JTextField noOfEmptyBedsTF;
    private javax.swing.JLabel numberOfEmptyBeds;
    private javax.swing.JLabel passErr;
    private javax.swing.JLabel passwordAdminErr;
    private javax.swing.JPasswordField passwordAdminTF;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JLabel phoneNumberOfCaller;
    private javax.swing.JPanel pickTheCall;
    private javax.swing.JLabel priorityLabel;
    private javax.swing.JSlider prioritySlider;
    private javax.swing.JButton reportAnEmergencyBtn;
    private javax.swing.JButton routeCallBtn;
    private javax.swing.JPanel search911Dept;
    private javax.swing.JLabel specialityLabel;
    private javax.swing.JTextField specialityTF;
    private javax.swing.JLabel specilaityErr;
    private javax.swing.JScrollPane table;
    private javax.swing.JTable timeFrameTable;
    private javax.swing.JLabel userNaemErr;
    private javax.swing.JLabel userNameLabel;
    private javax.swing.JLabel usernameAdminErr;
    private javax.swing.JTextField username_Admin_TF;
    // End of variables declaration//GEN-END:variables
}
