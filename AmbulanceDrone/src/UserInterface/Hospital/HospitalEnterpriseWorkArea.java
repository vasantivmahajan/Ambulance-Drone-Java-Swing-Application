/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.Hospital;

import EmergencySystem.Emergency.Emergency;
import EmergencySystem.EmergencySystem;
import EmergencySystem.Enterprise.Enterprise;
import EmergencySystem.Network.Network;
import Employee.Ambulance;
import Employee.Doctor;
import Employee.Employee;
import Hospital.Hospital;
import Hospital.Organisation.AmbulanceOrganisation;
import Hospital.Organisation.DoctorOrganization;
import Hospital.Organisation.Organisation;
import Hospital.Organisation.Organisation.Type;
import Hospital.Role.AmbulanceRole;
import Hospital.Role.DoctorRole;
import Hospital.UserAccount.UserAccount;
import Hospital.WorkQueue.Emergency911DepartmentWorkRequest;
import Hospital.WorkQueue.WorkRequest;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Vasanti
 */
public class HospitalEnterpriseWorkArea extends javax.swing.JPanel {

    /**
     * Creates new form HospitalEnterpriseWorkArea
     */
    private JPanel userProcessContainer;
    private UserAccount account;
    private EmergencySystem system;
    private Network network;
   
    private Hospital hospital;
    private Emergency e;
    public HospitalEnterpriseWorkArea(JPanel userProcessContainer, UserAccount account, EmergencySystem system, Network network, Enterprise enterprise) {
        initComponents();
        this.userProcessContainer=userProcessContainer;
        this.account=account;
        this.system=system;
        this.network=network;
        
        this.hospital= (Hospital)enterprise;
        alertambulanceBtn.setEnabled(false);
        populateOrganisationCombo();
        populateWorkRequestTable();
        populateAmbulanceTable();
        populateOrganisationTable();
        
       
        
    }
    
    
    public void populateAmbulanceTable()
    {
        DefaultTableModel model = (DefaultTableModel) ambulanceTable.getModel();
        model.setRowCount(0);
        for(Network n:system.getNetworkList())
        {
            for(Hospital h:n.getHospitalList())
            {
               for(UserAccount ua:h.getUserAccountDirectory().getUserAccountList())
               {
                   if(ua==account)
                   {
                       for(Organisation org:h.getOrganizationDirectory().getOrganisationList())
                       {
                           if(org instanceof AmbulanceOrganisation)
                           {
                               for(Employee e:org.getEmployeeDirectory().getEmployeeList())
                               {
                                   if(((Ambulance)e).getAvailability().equalsIgnoreCase("Available"))
                                   {
                                     Object[] row = new Object[2];
                                     row[0]= ((Ambulance)e);
                                     row[1] = ((Ambulance)e).getAvailability();
                                     model.addRow(row);
                                   }
                                     
                               }
                           }
                       }
                   }
               }
            }
        }
        {
           
        }
        
    }
   public void populateWorkRequestTable()
   {
       DefaultTableModel model = (DefaultTableModel) emergencyTable.getModel();
        
        model.setRowCount(0);
        
        for (WorkRequest workRequest:account.getWorkQueue().getWorkRequestList()){
            Object[] row = new Object[4];
            if(((Emergency911DepartmentWorkRequest) workRequest).getEmergency().getPriority()>=7)
            {
            row[2] = "Critical";
           
            }
            
            else if(((Emergency911DepartmentWorkRequest) workRequest).getEmergency().getPriority()<7 &&((Emergency911DepartmentWorkRequest) workRequest).getEmergency().getPriority()>4)
            {
                row[2]="Moderate";
            }
            
            else
            {
                 row[2]="Trivial";
            }
            row[0]= ((Emergency911DepartmentWorkRequest) workRequest);
            row[1]=((Emergency911DepartmentWorkRequest) workRequest).getEmergency();
            row[3] = ((Emergency911DepartmentWorkRequest) workRequest).getEmergency().getNatureOfEmergency();
           
            model.addRow(row);
        }
   }
    
    public Hospital getHospital()
    {
        Hospital hospital=null;
        for(Hospital h:network.getHospitalList())
        {
            for(UserAccount ua:h.getUserAccountDirectory().getUserAccountList())
            {
                if(ua==account)
                {
                    hospital=h;
                }
            }
        }
        return hospital;
    }
    public void populateOrganisationCombo()
      {
           organisationCombo.removeAllItems();
        for (Type type : Organisation.Type.values())
        {
            if( (type.getValue().equals(Type.AMBULANCE.getValue()))|| (type.getValue().equals(Type.DOCTOR.getValue())))
                organisationCombo.addItem(type);
        }
      }
    
    public void populateOrganisationTable()
    {
    DefaultTableModel model = (DefaultTableModel) hosptOrganisationTable.getModel();
        
        model.setRowCount(0);
        
        for (Organisation organisation : hospital.getOrganizationDirectory().getOrganisationList())
        {
            Object[] row = new Object[1];
            row[0] = organisation;

            model.addRow(row);
        }
        if(hosptOrganisationTable.getRowCount()>=2)
        {
            addOrgBtn.setEnabled(false);
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
        enterpriseWorkArea = new javax.swing.JTabbedPane();
        workQueue = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        emergencyTable = new javax.swing.JTable();
        processEmergencyBtn = new javax.swing.JButton();
        alertAmbulance = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ambulanceTable = new javax.swing.JTable();
        alertambulanceBtn = new javax.swing.JButton();
        manageOrgPanel = new javax.swing.JPanel();
        organisationLabel = new javax.swing.JLabel();
        organisationCombo = new javax.swing.JComboBox();
        addOrgBtn = new javax.swing.JButton();
        organisationTable = new javax.swing.JScrollPane();
        hosptOrganisationTable = new javax.swing.JTable();
        nameLabel = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        nameTF = new javax.swing.JTextField();
        usernameTF = new javax.swing.JTextField();
        createEmployee = new javax.swing.JButton();
        passwordTF = new javax.swing.JPasswordField();
        nameErr = new javax.swing.JLabel();
        userNameErr = new javax.swing.JLabel();
        passwordErr = new javax.swing.JLabel();
        availabilityLabel = new javax.swing.JLabel();
        availabilityTF = new javax.swing.JTextField();
        availErr = new javax.swing.JLabel();
        specialityLabel = new javax.swing.JLabel();
        specialityTF = new javax.swing.JTextField();
        specialityErr = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        header.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        header.setText("Hospital Enterprise Admin Work Area");

        workQueue.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        emergencyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sender", "Location of emergency", "Crticality", "Nature of emergency"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
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

        processEmergencyBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        processEmergencyBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/Hospital/images/1449792327_process.png"))); // NOI18N
        processEmergencyBtn.setText("Process the emergency");
        processEmergencyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                processEmergencyBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout workQueueLayout = new javax.swing.GroupLayout(workQueue);
        workQueue.setLayout(workQueueLayout);
        workQueueLayout.setHorizontalGroup(
            workQueueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(workQueueLayout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(workQueueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, workQueueLayout.createSequentialGroup()
                        .addComponent(processEmergencyBtn)
                        .addGap(236, 236, 236))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, workQueueLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36))))
        );
        workQueueLayout.setVerticalGroup(
            workQueueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(workQueueLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(processEmergencyBtn)
                .addContainerGap(311, Short.MAX_VALUE))
        );

        enterpriseWorkArea.addTab("Work queue", workQueue);

        alertAmbulance.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ambulanceTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ambulance Name", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(ambulanceTable);

        alertambulanceBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        alertambulanceBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/Hospital/images/1449793544_ambulance.png"))); // NOI18N
        alertambulanceBtn.setText("Alert ambulance");
        alertambulanceBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alertambulanceBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout alertAmbulanceLayout = new javax.swing.GroupLayout(alertAmbulance);
        alertAmbulance.setLayout(alertAmbulanceLayout);
        alertAmbulanceLayout.setHorizontalGroup(
            alertAmbulanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(alertAmbulanceLayout.createSequentialGroup()
                .addContainerGap(121, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(105, 105, 105))
            .addGroup(alertAmbulanceLayout.createSequentialGroup()
                .addGap(273, 273, 273)
                .addComponent(alertambulanceBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        alertAmbulanceLayout.setVerticalGroup(
            alertAmbulanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(alertAmbulanceLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(alertambulanceBtn)
                .addContainerGap(349, Short.MAX_VALUE))
        );

        enterpriseWorkArea.addTab("Alert Ambulance", alertAmbulance);

        manageOrgPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        organisationLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        organisationLabel.setText("Organisation");

        organisationCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        organisationCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                organisationComboActionPerformed(evt);
            }
        });

        addOrgBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        addOrgBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/Hospital/images/1449768059_More.png"))); // NOI18N
        addOrgBtn.setText("Add Organisation");
        addOrgBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addOrgBtnActionPerformed(evt);
            }
        });

        hosptOrganisationTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Organisation"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        organisationTable.setViewportView(hosptOrganisationTable);

        nameLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        nameLabel.setText("Name:");

        usernameLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        usernameLabel.setText("Username:");

        passwordLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        passwordLabel.setText("Password:");

        nameTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nameTFFocusGained(evt);
            }
        });

        usernameTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                usernameTFFocusGained(evt);
            }
        });

        createEmployee.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        createEmployee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/Hospital/images/1449791895_user_male2.png"))); // NOI18N
        createEmployee.setText("Create employee");
        createEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createEmployeeActionPerformed(evt);
            }
        });

        passwordTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passwordTFFocusGained(evt);
            }
        });

        nameErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        nameErr.setForeground(new java.awt.Color(102, 102, 102));

        userNameErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        userNameErr.setForeground(new java.awt.Color(102, 102, 102));

        passwordErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        passwordErr.setForeground(new java.awt.Color(102, 102, 102));

        availabilityLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        availabilityLabel.setText("Availability:");

        availabilityTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                availabilityTFFocusGained(evt);
            }
        });

        availErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        availErr.setForeground(new java.awt.Color(102, 102, 102));

        specialityLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        specialityLabel.setText("Speciality:");

        specialityTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                specialityTFFocusGained(evt);
            }
        });

        specialityErr.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        specialityErr.setForeground(new java.awt.Color(102, 102, 102));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("Select the organisation");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout manageOrgPanelLayout = new javax.swing.GroupLayout(manageOrgPanel);
        manageOrgPanel.setLayout(manageOrgPanelLayout);
        manageOrgPanelLayout.setHorizontalGroup(
            manageOrgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageOrgPanelLayout.createSequentialGroup()
                .addGap(270, 270, 270)
                .addComponent(addOrgBtn)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageOrgPanelLayout.createSequentialGroup()
                .addContainerGap(169, Short.MAX_VALUE)
                .addGroup(manageOrgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageOrgPanelLayout.createSequentialGroup()
                        .addComponent(organisationLabel)
                        .addGap(47, 47, 47)
                        .addComponent(organisationCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(223, 223, 223))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageOrgPanelLayout.createSequentialGroup()
                        .addGroup(manageOrgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameLabel)
                            .addComponent(passwordLabel)
                            .addGroup(manageOrgPanelLayout.createSequentialGroup()
                                .addGroup(manageOrgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(availabilityLabel)
                                    .addComponent(usernameLabel)
                                    .addComponent(specialityLabel))
                                .addGap(66, 66, 66)
                                .addGroup(manageOrgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(nameErr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(nameTF)
                                    .addComponent(usernameTF)
                                    .addComponent(userNameErr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(passwordTF, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                                    .addComponent(passwordErr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(availabilityTF)
                                    .addComponent(availErr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(specialityTF)
                                    .addComponent(specialityErr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(183, 183, 183))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageOrgPanelLayout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(258, 258, 258))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageOrgPanelLayout.createSequentialGroup()
                        .addComponent(organisationTable, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(167, 167, 167))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageOrgPanelLayout.createSequentialGroup()
                        .addComponent(createEmployee)
                        .addGap(243, 243, 243))))
        );
        manageOrgPanelLayout.setVerticalGroup(
            manageOrgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageOrgPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(manageOrgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(organisationCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(organisationLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addOrgBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(organisationTable, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(7, 7, 7)
                .addGroup(manageOrgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameErr, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(manageOrgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameLabel)
                    .addComponent(usernameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userNameErr, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(manageOrgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel)
                    .addComponent(passwordTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passwordErr, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(manageOrgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(availabilityLabel)
                    .addComponent(availabilityTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(availErr, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(manageOrgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(specialityLabel)
                    .addComponent(specialityTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(specialityErr, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(createEmployee)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        enterpriseWorkArea.addTab("Manage Organisations", manageOrgPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(250, 250, 250)
                        .addComponent(header))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(enterpriseWorkArea, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(header)
                .addGap(18, 18, 18)
                .addComponent(enterpriseWorkArea, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addOrgBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addOrgBtnActionPerformed
        // TODO add your handling code here:
        Type type = (Type) organisationCombo.getSelectedItem();
        hospital.getOrganizationDirectory().createOrganisation(type);
        populateOrganisationTable();
    }//GEN-LAST:event_addOrgBtnActionPerformed

    private void createEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createEmployeeActionPerformed
        // TODO add your handling code here:
        boolean userNameIsUnique;
//        specialityErr.setVisible(false);
//        specialityLabel.setVisible(false);
//        specialityTF.setVisible(false);
        String username = usernameTF.getText().trim();
         char[] passwordCharArray = passwordTF.getPassword();
        String password = String.valueOf(passwordCharArray).trim(); 
        
       
        
        
        
//        if(nameTF.getText().trim().matches("[a-zA-Z0-9 ]+")&&username.matches("[a-zA-Z0-9]+")&&password.matches("[a-zA-Z0-9]+")&&availabilityTF.getText().trim().matches("[a-zA-Z0-9]+"))
//        {
//            userNameIsUnique=system.checkIfUserNameIsUnique(username, password);
//            if(userNameIsUnique==true)
//            {
                int rowNumber=hosptOrganisationTable.getSelectedRow();
                if(rowNumber>=0)
                {
                    Organisation org=(Organisation) hosptOrganisationTable.getValueAt(rowNumber,0);
                    if(org instanceof DoctorOrganization)
                    {
                        
                    
                    if(nameTF.getText().trim().matches("[a-zA-Z0-9 ]+")&&username.matches("[a-zA-Z0-9]+")&&password.matches("[a-zA-Z0-9]+")&&availabilityTF.getText().trim().matches("[a-zA-Z0-9]+")&&specialityTF.getText().trim().matches("[a-zA-Z0-9]+"))
                    {
                   
                    userNameIsUnique=system.checkIfUserNameIsUnique(username, password);
                    if(userNameIsUnique==true)
                    {
                    Doctor d=new Doctor();
                    d.setName(nameTF.getText().trim());
                    org.getEmployeeDirectory().createEmployee(d);
                    org.getUserAccountDirectory().createUserAccount(username, password, d,new DoctorRole());  
                    JOptionPane.showMessageDialog(this,"The doctor employee is created successfully");
                    nameTF.setText("");
                    usernameTF.setText("");
                    passwordTF.setText("");
                    availabilityTF.setText("");
                    specialityTF.setText("");
                    }
                    
                    else
                    {
                    userNameErr.setText("This username already exists"); 
                    }
                    }
                    
                    else
                    {
                        if(!nameTF.getText().trim().matches("[a-zA-Z0-9 ]+"))
                           {
                            nameErr.setText("Enter a valid name");
                           }
            
                        if(!username.matches("[a-zA-Z0-9]+"))
                            {
                            userNameErr.setText("Enter a valid username");
                            }
            
                        if(!password.matches("[a-zA-Z0-9]+"))
                            {
                            passwordErr.setText("Enter a valid password");
                        
                            }
                        
                        if(!availabilityTF.getText().trim().matches("[a-zA-Z0-9]+"))
                            {
                            availErr.setText("Enter valid availability");
                            }
                        
                        if(!specialityTF.getText().trim().matches("[a-zA-Z0-9]+"))
                            {
                            specialityErr.setText("Enter a valid speciality");
                            }
                            
                        
                        
                    }
                    
                   
                    }
                     else if(org instanceof AmbulanceOrganisation)
                    {
                        
                        
                        if(nameTF.getText().trim().matches("[a-zA-Z0-9 ]+")&&username.matches("[a-zA-Z0-9]+")&&password.matches("[a-zA-Z0-9]+")&&availabilityTF.getText().trim().matches("[a-zA-Z0-9]+"))
                        {
                         userNameIsUnique=system.checkIfUserNameIsUnique(username, password);
                        if(userNameIsUnique==true)
                        {
                        Ambulance a=new Ambulance();
                        a.setName(nameTF.getText().trim());
                        org.getEmployeeDirectory().createEmployee(a);
                        org.getUserAccountDirectory().createUserAccount(username, password, a,new AmbulanceRole());
                        JOptionPane.showMessageDialog(this,"The ambulance employee is created successfully");
                        nameTF.setText("");
                        usernameTF.setText("");
                        passwordTF.setText("");
                        availabilityTF.setText("");
                        
                        }
                        
                        else
                        {
                            userNameErr.setText("This username already exists"); 
                        }
                         
                        }
                        
                        else
                        {
                        if(!nameTF.getText().trim().matches("[a-zA-Z0-9 ]+"))
                           {
                            nameErr.setText("Enter a valid name");
                           }
            
                        if(!username.matches("[a-zA-Z0-9]+"))
                            {
                            userNameErr.setText("Enter a valid username");
                            }
            
                        if(!password.matches("[a-zA-Z0-9]+"))
                            {
                            passwordErr.setText("Enter a valid password");
                        
                            }
                        
                        if(!availabilityTF.getText().trim().matches("[a-zA-Z0-9]+"))
                            {
                            availErr.setText("Enter valid availability");
                            }
                        }
                            
        
                    }
        }
        
        else
        {
            JOptionPane.showMessageDialog(this,"Select an organisation from the table");
        }
          
          //  }
            
//            else
//            {
//               userNameErr.setText("This username already exists"); 
//            }
    //    }
        
//        else
//        {
//            if(!nameTF.getText().trim().matches("[a-zA-Z0-9 ]+"))
//            {
//            nameErr.setText("Enter a valid name");
//            }
//            
//            if(!username.matches("[a-zA-Z0-9]+"))
//            {
//                userNameErr.setText("Enter a valid username");
//            }
//            
//            if(!password.matches("[a-zA-Z0-9]+"))
//            {
//                passwordErr.setText("Enter a valid password");
//                        
//            }
      //  }
        
        
        
        
    }//GEN-LAST:event_createEmployeeActionPerformed

    private void alertambulanceBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alertambulanceBtnActionPerformed
        // TODO add your handling code here:
        int rowSelected=ambulanceTable.getSelectedRow();
        int select=emergencyTable.getSelectedRow();
        WorkRequest request=null;
        if(select>=0)
        {
            request = (WorkRequest) emergencyTable.getValueAt(select, 0);
           
        }
        
        else
        {
        JOptionPane.showMessageDialog(this,"Select an emergency from the emergency table");
        }
        if(rowSelected>=0)
        {
             Emergency emer=(Emergency) emergencyTable.getValueAt(select, 1);
            Ambulance a=(Ambulance) ambulanceTable.getValueAt(rowSelected,0);
           
                for(Organisation org:hospital.getOrganizationDirectory().getOrganisationList())
                {
                    if(org instanceof AmbulanceOrganisation)
                    {
                        for(UserAccount user:org.getUserAccountDirectory().getUserAccountList())
                        {
                            if((user.getEmployee()==a)&&(!emer.getEmergencyStatus().equalsIgnoreCase("Assigned to ambulance")))
                            {
                          
                                request.setSender(account);
                                request.setReceiver(user);
                                emer.setEmergencyStatus("Assigned to ambulance");
                                user.getWorkQueue().getWorkRequestList().add(request);
                                ((Ambulance)user.getEmployee()).setAvailability("Not available");
                                 JOptionPane.showMessageDialog(this,"The ambulance "+a.getName()+ " has been alerted about the emergency");
                                 populateAmbulanceTable();
                                 Date d=new Date();
                                 emer.setAmbulanceDispatched(d);
                                 emer.setTotalTimeToDispatchAmbulance((emer.getAmbulanceDispatched().getTime()-emer.getHospitalAlerted().getTime())/ 1000 % 60);
                                System.err.println("The time from hospital alerted to ambulance dipatched"+emer.getTotalTimeToDispatchAmbulance());
                                 break;
                            }
                            
                            else
                            {
                                if(emer.getEmergencyStatus().equalsIgnoreCase("Assigned to ambulance"))
                                {
                                JOptionPane.showMessageDialog(this, "For this emergency the ambulance has already been alerted");
                                break;
                                }
                                
                            }
                        }
                    }
                }
            
        }
        
        else{
        JOptionPane.showMessageDialog(this,"Choose a ambulance from the table");
        }
    }//GEN-LAST:event_alertambulanceBtnActionPerformed

    private void processEmergencyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_processEmergencyBtnActionPerformed
        // TODO add your handling code here:
        int rowSelected=emergencyTable.getSelectedRow();
        if(rowSelected>=0)
        {
            e=(Emergency) emergencyTable.getValueAt(rowSelected, 1);
            JOptionPane.showMessageDialog(this, "Alert the ambulance");
             alertambulanceBtn.setEnabled(true);
            
        }
        
        else
        {
            JOptionPane.showMessageDialog(this,"Choose the emergency from the table");
            
        }
    }//GEN-LAST:event_processEmergencyBtnActionPerformed

    private void nameTFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameTFFocusGained
        // TODO add your handling code here:
        nameErr.setText("");
    }//GEN-LAST:event_nameTFFocusGained

    private void usernameTFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usernameTFFocusGained
        // TODO add your handling code here:
        userNameErr.setText("");
    }//GEN-LAST:event_usernameTFFocusGained

    private void passwordTFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordTFFocusGained
        // TODO add your handling code here:
        passwordErr.setText("");
    }//GEN-LAST:event_passwordTFFocusGained

    private void organisationComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_organisationComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_organisationComboActionPerformed

    private void availabilityTFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_availabilityTFFocusGained
        // TODO add your handling code here:
        availErr.setText("");
    }//GEN-LAST:event_availabilityTFFocusGained

    private void specialityTFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_specialityTFFocusGained
        // TODO add your handling code here:
        specialityErr.setText("");
    }//GEN-LAST:event_specialityTFFocusGained

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        int rowSelected=hosptOrganisationTable.getSelectedRow();
        if(rowSelected>=0)
        {
                    hosptOrganisationTable.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                          public void mouseClicked(java.awt.event.MouseEvent evt) 
                          {
                          int row = hosptOrganisationTable.rowAtPoint(evt.getPoint());
                          int col = hosptOrganisationTable.columnAtPoint(evt.getPoint());
                          if (row >= 0 && col >= 0)
                            {
                               specialityErr.setVisible(true);
                               specialityLabel.setVisible(true);
                               specialityTF.setVisible(true);
                               nameTF.setEditable(true);
                               usernameTF.setEditable(true);
                               passwordTF.setEditable(true);
                               availabilityTF.setEditable(true);
                               specialityTF.setEditable(true);
                               

                            }
                          if(row >= 1 && col >= 0)
                          {
                              specialityErr.setVisible(false);
                              specialityLabel.setVisible(false);
                              specialityTF.setVisible(false);
                              nameTF.setEditable(true);
                               usernameTF.setEditable(true);
                               passwordTF.setEditable(true);
                               availabilityTF.setEditable(true);
                               
                              
                          }
                          }
                    });         
        }
        
        else
        {
            JOptionPane.showMessageDialog(this, "Choose an organisation from the table");
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addOrgBtn;
    private javax.swing.JPanel alertAmbulance;
    private javax.swing.JButton alertambulanceBtn;
    private javax.swing.JTable ambulanceTable;
    private javax.swing.JLabel availErr;
    private javax.swing.JLabel availabilityLabel;
    private javax.swing.JTextField availabilityTF;
    private javax.swing.JButton createEmployee;
    private javax.swing.JTable emergencyTable;
    private javax.swing.JTabbedPane enterpriseWorkArea;
    private javax.swing.JLabel header;
    private javax.swing.JTable hosptOrganisationTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel manageOrgPanel;
    private javax.swing.JLabel nameErr;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTF;
    private javax.swing.JComboBox organisationCombo;
    private javax.swing.JLabel organisationLabel;
    private javax.swing.JScrollPane organisationTable;
    private javax.swing.JLabel passwordErr;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPasswordField passwordTF;
    private javax.swing.JButton processEmergencyBtn;
    private javax.swing.JLabel specialityErr;
    private javax.swing.JLabel specialityLabel;
    private javax.swing.JTextField specialityTF;
    private javax.swing.JLabel userNameErr;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameTF;
    private javax.swing.JPanel workQueue;
    // End of variables declaration//GEN-END:variables
}
