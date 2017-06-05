package model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.String;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

public class Organizer {
    JFrame flashWindow=null, userWindow=null,composeFrame=null;
    JPanel flashPanel=null, registerPanel=null, userPanel=null, folderPanel=null, listPanel=null, editorPanel=null,panel;
    JTabbedPane registrationPane, accountTabsPane;
    JButton registered, inbox, outbox, sent, draft, compose, gp_next;
    Font headlinefont,textfont;
    Validator validity;
    JTable table;
    int height, width,tab_index;
    Cursor hand_cursor;
    
    //General Panel UI
    JTextField firstName, lastName, userName, user_name;
    JPasswordField password, confirmPassword, upassword;
    JLabel generalContent=null, fname,lname,uname,pwd,cpwd,password_length,password_match,app_title;
    
    //Contact Panel UI
    JLabel contactContent=null, adrs,pin, mob, tele, email_label,pincode_length;
    JTextField address, pincode, mobile, tele_phone, email;
    
    //Setting Panel UI
    JLabel emailID_text, password_text;
    JButton edit;
    JTextField emailId;
    JPasswordField id_password;
    JScrollPane folder_scroll, list_scroll, editor_scroll,table_scroll;
   
     //Compose panel
     JLabel to=null, cc=null, subject=null, action=null;
     JTextField to_field=null, cc_field=null, subject_field=null;
     JTextArea message=null;
     JButton send=null;
     JScrollPane message_pane=null;
    
    
    UserAccounts userAccounts;
    AccountPanel accntPanel;
    JTree tree;
    int headerHeight, folderHeight;
    
    final int PAGESIZE=5;
    //-----------------------------------------------------------------------------------------------------
    // Organizer constructor
    public Organizer()
    {
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Dimension dimn=toolkit.getScreenSize();
        setHeight(dimn.height);
        setWidth(dimn.width);
        hand_cursor=new Cursor(Cursor.HAND_CURSOR);
        validity=new Validator();
        headerHeight=50;
        folderHeight=30;
    }
    
    // getHeight() - get Screen Height
    public int getHeight() {
        return height;
    }
    
    //setHeight() - set height variable with the Screen Height
    public void setHeight(int height) {
        this.height = height;
    }
    
    //getWidth() - get Screen Width
    public int getWidth() {
        return width;
    }
    
    //setWidth() - set width variable with the Screen Width
    public void setWidth(int width) {
        this.width = width;
    }
    
    //#####################################################################################################
    //------------------------------GUI methods for flash window-------------------------------------------
    
    //header() - to create Organizer header of a screen
    private void header(JPanel panel){
        ImageIcon icon=new ImageIcon(Organizer.class.getResource("../resource/organizer.png").getPath());
        app_title=new JLabel(icon);
        app_title.setBounds(0,2,180,50);
        
        JPanel appname_panel=new JPanel();
        appname_panel.setLayout(null);
        appname_panel.setBounds(0,0,getWidth(),headerHeight);
        appname_panel.setBackground(Color.blue);
        appname_panel.add(app_title);
        panel.add(appname_panel);
    }
    
    private void setFonts(){
        headlinefont=new Font("Comic Sans MS", Font.PLAIN, 14);
        textfont=new Font("Comic Sans MS", Font.PLAIN, 12);
    }
    //flashBody() - to create GUI body of Flash Window
    private void flashBody(JPanel panel){
        JPanel appname_panel=new JPanel();
        appname_panel.setLayout(null);
        appname_panel.setBounds(0,0,getWidth(),getHeight());
        appname_panel.setBackground(Color.white);
        StringBuffer headline=new StringBuffer();
        StringBuffer multiline=new StringBuffer();
        setFonts();
        headline.append("<html>About Organizer</html>");
        multiline.append("<html>");
        multiline.append("<br>> Organize Emails");
        multiline.append("<br>> Send Emails");
        multiline.append("<br>> Print Emails");
        multiline.append("</html>");
        // Left Side------------------------------------------------------------
        JLabel about=new JLabel(headline.toString());
        about.setFont(headlinefont);
        about.setBounds(20, 1, 200, 200);
        JLabel abouttext=new JLabel(multiline.toString());
        abouttext.setFont(textfont);
        abouttext.setBounds(20, 40, 200, 200);
        JButton register=new JButton("Register");
        register.setFont(textfont);
        register.setCursor(hand_cursor);
        register.addActionListener(new RegisterListener());
//        register.setBackground(Color.white);
        register.setForeground(Color.red);
//        register.setBorder(null);
        register.setBounds(20, 240, 90, 30);
        appname_panel.add(about);
        appname_panel.add(abouttext);
        appname_panel.add(register);
        
        // Right Side-----------------------------------------------------------
        StringBuffer fieldNames=new StringBuffer();
        fieldNames.append("<html>User name<br><br>Password</html>");
        JLabel fields=new JLabel(fieldNames.toString());
        fields.setFont(textfont);
        fields.setBounds(getWidth()/4+40, 40, 200, 200);
        user_name=new JTextField();
        user_name.setBounds(getWidth()/4+120, 110, 150, 25);
        upassword=new JPasswordField();
        upassword.setBounds(getWidth()/4+120, 145, 150, 25);
        JButton forgot=new JButton("forgot password ?");
        forgot.setFont(textfont);
        forgot.setCursor(hand_cursor);
        forgot.addActionListener(new ForgotListener());
        forgot.setBorder(null);
        forgot.setBackground(Color.white);
        forgot.setForeground(Color.blue);
        forgot.setBounds(getWidth()/4+162, 172, 120, 25);
        
        JButton login=new JButton("Login");
        login.setCursor(hand_cursor);
        login.setFont(textfont);
        login.setForeground(Color.red);
        login.addActionListener(new LoginListener());
        login.addKeyListener(new key());
        login.setBounds(getWidth()/4+121, 210, 90, 25);
        
        appname_panel.add(fields);
        appname_panel.add(user_name);
        appname_panel.add(upassword);
        appname_panel.add(forgot);
        appname_panel.add(login);
        panel.add(appname_panel);
    }
    
    //dialog_header() - to create header of a dialog box
    private void dialog_header(JPanel panel){
        ImageIcon icon=new ImageIcon(Organizer.class.getResource("../resource/dialog_oz.png").getPath());
        JLabel app_title=new JLabel(icon);
        app_title.setBounds(90,2,180,50);
        
        JPanel appname_panel=new JPanel();
        appname_panel.setLayout(null);
        appname_panel.setBounds(0,0,getWidth(),50);
        appname_panel.setBackground(Color.white);
        appname_panel.add(app_title);
        panel.add(appname_panel);
    }
    
    //registration() - create gui once Register button is clicked
    private void registration(JFrame frame){
        registrationPane=new JTabbedPane();
        GeneralPanel general_panel=new GeneralPanel();
        ContactPanel contact_panel=new ContactPanel();
        SettingPanel setting_panel=new SettingPanel();
        registrationPane.addTab("General",general_panel);
        registrationPane.addTab("Contacts",contact_panel);
        registrationPane.addTab("Settings",setting_panel);
        registrationPane.setBounds(0,0,getWidth()/2,getHeight()/2);
        registrationPane.setBackground(Color.white);
        frame.add(registrationPane);
    }
    
    //#####################################################################################################
    //-------------------------------------P A N E L S----------------------------------------------------
    
    //flashWindow() - function to invoke createFlashPanel() and display flash window
    private void flashWindow(){
        flashWindow=new JFrame("Organizer");
        flashWindow.setLayout(null);
        createFlashPanel(flashWindow);
        flashWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        flashWindow.setSize(getWidth()/2, getHeight()/2);
        flashWindow.setLocation(getWidth()/4, getHeight()/4);
        flashWindow.setResizable(false);
        flashWindow.setVisible(true);
    }
    
    //createFlashPanel() - to create gui for flash welcome window
    private void createFlashPanel(JFrame frame){
        flashPanel =new JPanel();
        flashPanel.setLayout(null);
        flashPanel.setSize(getWidth()/2, getHeight()/2);
        flashPanel.setVisible(true);
        header(flashPanel);
        flashBody(flashPanel);
        frame.add(flashPanel);
    }
    
    //createRegisterPanel() - to create gui for registration window which appears in Register button click
    private void createRegisterPanel(JFrame frame){
        flashPanel.setVisible(false);
        if(registerPanel == null){
            registerPanel=new JPanel();
            registerPanel.setLayout(null);
            registerPanel.setSize(getWidth()/2, getHeight()/2);
            registerPanel.setVisible(true);
    //        header(registerPanel);
            registration(frame);
            frame.add(registerPanel);
        }else{
            registerPanel.setVisible(true);
            registered.setVisible(true);
        }
    }
    //-----------------------------Registration Panel GUI classes-------------------------------------------
    //GeneralPanel - this panel contains GUI for General Tab in Registration Panel
    class GeneralPanel extends JPanel{
        GeneralPanel(){
            setLayout(null);
            StringBuffer label_text=new StringBuffer();
            label_text.append("<html>");
            label_text.append("Fill general details");
            label_text.append("</html>");
            
            generalContent=new JLabel(label_text.toString());
            generalContent.setFont(textfont);
            generalContent.setBounds(getWidth()/4+20, getHeight()/4+2, 250, 100);
            fname=new JLabel("First Name");
            fname.setFont(textfont);
            fname.setBounds(getWidth()/4+20, getHeight()/4-35, 250, 250);
            lname=new JLabel("Last Name");
            lname.setFont(textfont);
            lname.setBounds(getWidth()/4+20, getHeight()/4, 250, 250);
            uname=new JLabel("User Name");
            uname.setFont(textfont);
            uname.setBounds(getWidth()/4+20, getHeight()/4+35, 250, 250);
            pwd=new JLabel("Password");
            pwd.setFont(textfont);
            pwd.setBounds(getWidth()/4+20, getHeight()/4+70, 250, 250);
            cpwd=new JLabel("Confirm Password");
            cpwd.setFont(textfont);
            cpwd.setBounds(getWidth()/4+20, getHeight()/4+105, 250, 250);
            firstName=new JTextField();
            lastName=new JTextField();
            userName=new JTextField();
            password=new JPasswordField();
            confirmPassword=new JPasswordField();
            firstName.setBounds(getWidth()/4+200, getHeight()/4+80, 150, 25);
            lastName.setBounds(getWidth()/4+200, getHeight()/4+115, 150, 25);
            userName.setBounds(getWidth()/4+200, getHeight()/4+150, 150, 25);
            password.setBounds(getWidth()/4+200, getHeight()/4+185, 150, 25);
            confirmPassword.setBounds(getWidth()/4+200, getHeight()/4+220, 150, 25);
            gp_next=new JButton("Next");
            gp_next.setForeground(Color.blue);
            gp_next.setFont(textfont);
            gp_next.addActionListener(new NBListener());
            gp_next.setBounds(getWidth()/4+200, getHeight()/4+260, 80, 25);
            
            password_length=new JLabel("password length is less than 8 characters");
            password_length.setFont(textfont);
            password_length.setForeground(Color.red);
            password_length.setBounds(getWidth()/4+362, getHeight()/4+185,250,30);
            password_length.setVisible(false);
            
            password_match=new JLabel("password doesnt match");
            password_match.setForeground(Color.red);
            password_match.setBounds(getWidth()/4+350, getHeight()/4+200, 200, 30);
            password_match.setVisible(false);
            
            registered=new JButton("already registered ?");
            registered.setForeground(Color.blue);
            registered.setBackground(Color.white);
            registered.setBorder(null);
            registered.setFont(textfont);
            registered.addActionListener(new NBListener());
            registered.setBounds(getWidth()/4+495, getHeight()/4+20, 150, 25);
            registered.setVisible(true);
            setBackground(Color.white);
            add(registered);
            add(generalContent);
            add(fname);
            add(lname);
            add(uname);
            add(pwd);
            add(cpwd);
            add(firstName);
            add(lastName);
            add(userName);
            add(password);
            add(password_length);
            add(confirmPassword);
            add(password_match);
            add(gp_next);
            setVisible(true);
        }
    }
    
    //ContactPanel - this panel contains GUI for Contact Tab in Registration Panel
    class ContactPanel extends JPanel{
        JButton back,next;
        ContactPanel(){
            setLayout(null);
            StringBuffer label_text=new StringBuffer();
            label_text.append("<html>");
            label_text.append("Fill contact details");
            label_text.append("</html>");
            contactContent=new JLabel(label_text.toString());
            contactContent.setFont(textfont);
            contactContent.setBounds(getWidth()/4+20, getHeight()/4+2, 250, 100);
            adrs=new JLabel("Address");
            adrs.setFont(textfont);
            adrs.setBounds(getWidth()/4+20, getHeight()/4-35, 250, 250);
            pin=new JLabel("Pin Code");
            pin.setFont(textfont);
            pin.setBounds(getWidth()/4+20, getHeight()/4, 250, 250);
            mob=new JLabel("Mobile");
            mob.setFont(textfont);
            mob.setBounds(getWidth()/4+20, getHeight()/4+35, 250, 250);
            tele=new JLabel("Tele-Phone");
            tele.setFont(textfont);
            tele.setBounds(getWidth()/4+20, getHeight()/4+70, 250, 250);
            email_label=new JLabel("Email");
            email_label.setFont(textfont);
            email_label.setBounds(getWidth()/4+20, getHeight()/4+105, 250, 250);
            address=new JTextField();
            pincode=new JTextField();
            mobile=new JTextField();
            tele_phone=new JTextField();
            email=new JTextField();
            address.setBounds(getWidth()/4+200, getHeight()/4+80, 150, 25);
            pincode.setBounds(getWidth()/4+200, getHeight()/4+115, 150, 25);
            mobile.setBounds(getWidth()/4+200, getHeight()/4+150, 150, 25);
            tele_phone.setBounds(getWidth()/4+200, getHeight()/4+185, 150, 25);
            email.setBounds(getWidth()/4+200, getHeight()/4+220, 150, 25);
            back=new JButton("Back");
            back.setForeground(Color.blue);
            back.setFont(textfont);
            back.addActionListener(new NBListener());
            back.setBounds(getWidth()/4+20, getHeight()/4+260, 80, 25);
            next=new JButton("Next");
            next.setForeground(Color.blue);
            next.setFont(textfont);
            next.addActionListener(new NBListener());
            next.setBounds(getWidth()/4+200, getHeight()/4+260, 80, 25);
            setBackground(Color.white);
            
            pincode_length=new JLabel("*Pincode length invalid");
            pincode_length.setForeground(Color.red);
            pincode_length.setBounds(getWidth()/4+350, getHeight()/4+115, 150, 30);
            pincode_length.setVisible(false);
            
            add(contactContent);
            add(adrs);
            add(pin);
            add(pincode_length);
            add(mob);
            add(tele);
            add(email_label);
            add(address);
            add(pincode);
            add(mobile);
            add(tele_phone);
            add(email);
            add(back);
            add(next);
            setVisible(true);
            Object ob=null;
        }
    }
    
    
    //SettingPanel - this panel contains GUI for Setting Tab in Registration Panel
    class SettingPanel extends JPanel{
        String [] accounts={"Select", "Yahoo", "Gmail"};
        String [] columnnames={"Userid","Password"};
        JLabel labelContent, accountLabel;
        
        JButton back,addAccounts,addButton;
        JComboBox accountType;
        SettingPanel(){
            setLayout(null);
            StringBuffer label_text=new StringBuffer();
            label_text.append("<html>");
            label_text.append("<br>Add account");
            label_text.append("</html>");
            labelContent=new JLabel(label_text.toString());
            labelContent.setFont(headlinefont);
            labelContent.setBounds(getWidth()/4+20, getHeight()/2+2, 200, 50);
            addAccounts=new JButton("+");
            addAccounts.setBackground(Color.green);
            addAccounts.setForeground(Color.white);
            addAccounts.setBorder(null);
            addAccounts.setFont(new Font("Arial", Font.BOLD, 32));
            addAccounts.setBounds(getWidth()/4+105, getHeight()/4+30, 20, 20);
            addAccounts.addActionListener(new AccountListener());
            accountLabel=new JLabel("Account Type");
            accountLabel.setFont(textfont);
            accountLabel.setBounds(getWidth()/4+20, getHeight()/2+50, 200, 50);
            accountLabel.setVisible(false);
            emailID_text=new JLabel();
            emailID_text.setFont(textfont);
            emailID_text.setBounds(getWidth()/4+20, getHeight()/2+100, 200, 50);
            emailID_text.setVisible(false);
            password_text=new JLabel("Password");
            password_text.setFont(textfont);
            password_text.setBounds(getWidth()/4+20, getHeight()/2+150, 200, 50);
            password_text.setVisible(false);
            
            accountType=new JComboBox(accounts);
            accountType.setBounds(getWidth()/4+105, getHeight()/4+65, 100, 20);
            accountType.addItemListener(new AddAcountListener());
            accountType.setFont(textfont);
            accountType.setBackground(Color.white);
            accountType.setForeground(Color.blue);
            accountType.setVisible(false);
            
            emailId=new JTextField();
            emailId.setBounds(getWidth()/4+105, getHeight()/4+115, 150, 25);
            emailId.setVisible(false);
            
            id_password=new JPasswordField();
            id_password.setBounds(getWidth()/4+105, getHeight()/4+165, 150, 25);
            id_password.setVisible(false);
            
            back=new JButton("Back");
            back.setForeground(Color.blue);
            back.setFont(textfont);
            back.addActionListener(new NBListener());
            back.setBounds(getWidth()/4+20, getHeight()/4+260, 80, 25);
            
            edit=new JButton("Edit");
            edit.setForeground(Color.blue);
            edit.setFont(textfont);
            edit.setBounds(getWidth()/4+600, getHeight()/2+20, 80,25);
            edit.addActionListener(new Edit());
            
            addButton=new JButton("Add");
            addButton.setForeground(Color.blue);
            addButton.setFont(textfont);
            addButton.addActionListener(new NBListener());
            addButton.setBounds(getWidth()/4+170, getHeight()/4+260, 80, 25);
            addButton.setVisible(false);
            
            table=new JTable();
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            setBackground(Color.white);
            
            add(labelContent);
            add(accountLabel);
            add(addAccounts);
            add(accountType);
            add(emailID_text);
            add(emailId);
            add(password_text);
            add(id_password);
            add(back);
            add(addButton);
            add(table);
            add(edit);
            setVisible(true);
        }
        
        //-----------------------------Inner classes in SettingPanel---------------------------------------
        //Edit - inner class to handle action event when Edit button in Setting tab is clicked
        class Edit implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }
        
        //AccountListener - inner class to handle action event when + button in Setting tab is clicked
        class AccountListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent item) {
                addAccounts.setEnabled(false);
                accountLabel.setVisible(true);
                accountType.setVisible(true);
            }
        }
        
        //AddAcountListener - inner class to handle item event when drop down value for Account type changes in Setting tab is clicked
        class AddAcountListener implements ItemListener{
            @Override
            public void itemStateChanged(ItemEvent event) {
                int state=event.getStateChange();
                if(state == 1){
                    String accountName=accountType.getSelectedItem().toString();
                    if(accountName.equals("Select")){
                        emailID_text.setVisible(false);
                        emailId.setVisible(false);
                        password_text.setVisible(false);
                        id_password.setVisible(false);
                        addButton.setVisible(false);
                    }else{
                        emailID_text.setText(accountName+" ID");
                        emailID_text.setVisible(true);
                        emailId.setVisible(true);
                        password_text.setVisible(true);
                        id_password.setVisible(true);
                        addButton.setVisible(true);
                    }
                }
            }
            
        }
    }
    //-----------------------------End of Registration Panel GUI classes------------------------------------
    
    //-----------------------function to validate fields in Registration Panel------------------------------
    //checkGeneralPanel() - to validate field of General Tab
    public boolean  checkGeneralPanel(){
        boolean makeTransition=true;
        if(validity.isEmpty(firstName)){
            makeTransition=false;
            fname.setForeground(Color.red);
        }else{
            fname.setForeground(Color.black);
        }
        if(validity.isEmpty(lastName)){
            makeTransition=false;
            lname.setForeground(Color.red);
        }else{
            lname.setForeground(Color.black);
        }
        if(validity.isEmpty(userName)){
            makeTransition=false;
            uname.setForeground(Color.red);
        }else{
            uname.setForeground(Color.black);
        }
        if(validity.isEmpty(password)){
            makeTransition=false;
            pwd.setForeground(Color.red);
        }else{
            pwd.setForeground(Color.black);
        }
        
        if(validity.checkLength(password)){
            pwd.setForeground(Color.black);
            password_length.setVisible(false);
        }else{
            makeTransition=false;
            pwd.setForeground(Color.red);
            if(password.getPassword().length > 0){
                password_length.setVisible(true);
            }else{
                password_length.setVisible(false);
            }
        }
        if(validity.isEmpty(confirmPassword)){
            makeTransition=false;
            cpwd.setForeground(Color.red);
        }else{
            cpwd.setForeground(Color.black);
        }
        return makeTransition;
    }
    
    //checkContactPanel() - to validate field of Contact Tab
    public boolean checkContactPanel(){
        boolean makeTransition=true;
        if(validity.isEmpty(address)){
            makeTransition=false;
            adrs.setForeground(Color.red);
        }else{
            adrs.setForeground(Color.black);
        }
        if(validity.isEmpty(pincode)){
            makeTransition=false;
            pin.setForeground(Color.red);
        }else{
            pin.setForeground(Color.black);
        }
        if(validity.isEmpty(mobile)){
            makeTransition=false;
            mob.setForeground(Color.red);
        }else{
            mob.setForeground(Color.black);
        }
        if(validity.isEmpty(tele_phone)){
            makeTransition=false;
            tele.setForeground(Color.red);
        }else{
            tele.setForeground(Color.black);
        }
        if(validity.isEmpty(email)){
            makeTransition=false;
            email_label.setForeground(Color.red);
        }else{
            email_label.setForeground(Color.black);
        }
        return makeTransition;
    }
    
    //checkSettingPanel() - to validate fields of Setting Tab
    public boolean checkSettingPanel(){
        boolean makeTransition=true;
        if(validity.isEmpty(emailId)){
            makeTransition=false;
            emailID_text.setForeground(Color.red);
        }else{
            emailID_text.setForeground(Color.black);
        }
        if(validity.isEmpty(id_password)){
            makeTransition=false;
            password_text.setForeground(Color.red);
        }else{
            password_text.setForeground(Color.black);
        }
        return makeTransition;
    }
    
    class ComposePanel extends JPanel{
        String items[]={"-Select-", "Reply", "Reply ALL", "Forward", "Delete"};
        int c_w, c_h;
        ComposePanel(int width, int height){
            c_w=width;
            c_h=height;
//            setSize(getWidth(), getHeight());
//            setBackground(Color.red);
//            setVisible(true);
            setLayout(null);
            setBounds(0,0,getWidth(),getHeight());
//            setBackground(Color.red);
            to=new JLabel("TO");
            to.setBounds(10, 10, 50, 25);
            to.setFont(textfont);
            to_field=new JTextField();
            to_field.setBounds(80, 10, c_w+100, 25);
            cc=new JLabel("CC");
            cc.setBounds(10, 40, 50, 30);
            cc.setFont(textfont);
            cc_field=new JTextField();
            cc_field.setBounds(80, 40, c_w+100, 25);
            subject=new JLabel("Subject");
            subject.setBounds(10, 70, 50, 25);
            subject.setFont(textfont);
            subject_field=new JTextField();
            subject_field.setBounds(80, 70, c_w+100, 25);
            action=new JLabel("Action");
            action.setBounds(10, 100, 50, 25);
            action.setFont(textfont);
         // actionbox=new JComboBox<String>(items);
           // actionbox.setFont(textfont);
           // actionbox.setBounds(80, 100, c_w+100, 25);
            message=new JTextArea();
            message.setBounds(10,180, c_w+190, c_h);
            message_pane=new JScrollPane(message);
            message_pane.setBounds(10,180, c_w+190, c_h);
            send=new JButton("Send");
            send.setBounds(c_w+100, 180+c_h+20, 80, 25);
            send.setFont(textfont);
            send.addActionListener(new Send());
//            to.setVisible(true);
            add(to);
            add(to_field);
            add(cc);
            add(cc_field);
            add(subject);
            add(subject_field);
            add(action);
           // add(actionbox);
            add(message_pane);
            add(send);
            setVisible(true);
        }
    }
    //-------------------------------------END OF P A N E L S----------------------------------------------
    
    //----------------------------------------USER WINDOW--------------------------------------------------
    
    private void composeWindow(){
        if(composeFrame == null){
            composeFrame=new JFrame("Compose Email");
            composeFrame.addWindowListener(new ComposeWindowAdapter());
            composeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            composeFrame.setSize(getWidth()/2, getHeight()-100);
            ComposePanel composePanel=new ComposePanel(getWidth()/3, getHeight()/2);
            composeFrame.add(composePanel);
            composeFrame.setLocation(getWidth()/4, 10);
            composeFrame.setVisible(true);
        }
    }
    //userWindow() - to display user window of a registered user
    private void userWindow(){
        flashWindow.dispose();
        userWindow =new JFrame("User Window");
        userWindow.setLayout(null);
        accountTabs(userWindow);
        userWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userWindow.setSize(getWidth(), getHeight());
        userWindow.setResizable(true);
        userWindow.setVisible(true);
    }
    
    //accountTabs() - function to create tab pane for user window
    private void accountTabs(JFrame frame){
        accountTabsPane=new JTabbedPane();
        ArrayList<User> accounts=userAccounts.getAccounts();
        String tabName=null;
//        for(int i=0;i<accounts.size();i++){
          for(int i=0;i<accounts.size()-1;i++){
            accntPanel=new AccountPanel();
            String emailAddress=accounts.get(i).getUserName();
            if(emailAddress.contains("@yahoo") || emailAddress.contains("@ymail")){
                tabName="Yahoo";
            }else if(emailAddress.contains("@gmail")){
                tabName="Gmail";
            }
            tabName+=" - "+emailAddress;
            accountTabsPane.addTab(tabName,accntPanel);
        }
        accountTabsPane.addChangeListener(new Change());
        accountTabsPane.setBounds(0,0,getWidth(),getHeight());
        accountTabsPane.setBackground(Color.white);
        frame.add(accountTabsPane);
    }
    
    //AccountPanel
    class AccountPanel extends JPanel{
        AccountPanel(){
            setLayout(new BorderLayout(10, 10));
            generateFolders();
            Panel();
            //createListPanel();
            add(folder_scroll, BorderLayout.WEST);
            setVisible(true);
        }
        public void Panel()
        {
            panel=new JPanel();
            panel.setLayout(null);
            Combobox();
            Object rowData[][]=getEmails();
//            System.out.println("Length = "+rowData.length);
            table(rowData);
            panel.setBackground(Color.white);
            add(panel);
                    
        }
        public void Combobox()
        {
          String item[]={"-Select-","Reply","ReplyAll","Forward","Delete"};  
          JComboBox actionbox=new JComboBox(item);
          JLabel action=new JLabel("Action");
          action.setBounds(50,30, 80, 30);
          action.setFont(textfont);
          actionbox.setBounds(120,30,100,30);
          actionbox.setFont(textfont);
          panel.add(action);
          panel.add(actionbox);

        }
        public void table(Object rowData[][])
        {
            String columnNames[]={"", "From", "Subject"};
            TableModel model = new MyTableModel(rowData,columnNames);
            table=new JTable(model);
            table.setCellSelectionEnabled(true);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table.getColumnModel().getColumn(0).setPreferredWidth(2);
            table.getColumnModel().getColumn(1).setPreferredWidth(300);
            table.getColumnModel().getColumn(2).setPreferredWidth(830);
            table.addMouseListener(new TableContentSelect());
            table_scroll=new JScrollPane(table);
//            System.out.println(getWidth()+", "+getHeight());
            table_scroll.setBounds(0,70,1150,600);
            panel.add(table_scroll);
        }
        
        class MyTableModel extends AbstractTableModel{
            Object rowData[][] =null;
            String columnNames[]=null;
            MyTableModel(Object rowData[][], String columnNames[]){
                this.rowData=rowData;
                this.columnNames=columnNames;
            }

//            String columnNames[] = { "English", "Boolean" };

            public boolean isCellEditable(int rowIndex, int mColIndex) {
                boolean result=false;
                if(mColIndex == 0){
                    result=true;
                }
                return result;
            }

            public int getColumnCount() {
                return columnNames.length;
              }

            public String getColumnName(int column) {
                return columnNames[column];
              }

            public int getRowCount() {
              return rowData.length;
            }

            public Object getValueAt(int row, int column) {
//                System.out.println(row+" , "+column);
              return rowData[row][column];
            }

            public Class getColumnClass(int column) {
//                System.out.println(getValueAt(0, column).getClass());
              return (getValueAt(0, column).getClass());
            }

            public void setValueAt(Object value, int row, int column) {
              rowData[row][column] = value;
            }
        }
        
        private void generateFolders(){
            DefaultMutableTreeNode root=new DefaultMutableTreeNode("user ID");
            DefaultMutableTreeNode compose=new DefaultMutableTreeNode("Compose");
            DefaultMutableTreeNode inbox=new DefaultMutableTreeNode("Inbox");
            DefaultMutableTreeNode sent=new DefaultMutableTreeNode("Sent");
            DefaultMutableTreeNode draft=new DefaultMutableTreeNode("Draft");
            DefaultMutableTreeNode outbox=new DefaultMutableTreeNode("Outbox");
            
            root.add(inbox);
            root.add(sent);
            root.add(draft);
            root.add(outbox);
            root.add(compose);
            
            tree=new JTree(root);
            tree.addTreeSelectionListener(new FolderTreeListener());
            tree.addMouseListener(new FolderTreeMouseListener());
            tree.setFont(textfont);
            DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
            Icon closedIcon = new ImageIcon(Organizer.class.getResource("../resource/closed.png").getPath());
            Icon openIcon = new ImageIcon(Organizer.class.getResource("../resource/open.png").getPath());
            Icon leafIcon = new ImageIcon(Organizer.class.getResource("../resource/leaf.png").getPath());
            renderer.setClosedIcon(closedIcon);
            renderer.setOpenIcon(openIcon);
            renderer.setLeafIcon(leafIcon);
            folder_scroll=new JScrollPane(tree);
            folder_scroll.setPreferredSize(new Dimension(170, getHeight()));
            folder_scroll.setVisible(true);
        }
        
        private Object [][] generateRowData(Message messages[]){
            Object rowData[][]=new Object[messages.length - (messages.length-PAGESIZE)][];
            
            ArrayList<Message> messageList=new ArrayList<Message>(Arrays.asList(messages));
            for(int i=messageList.size()-1,index=0;i>=messageList.size()-PAGESIZE;i--,index++){
                try {
                    Object row[]=new Object[3];
                    row[0]=Boolean.FALSE;
//                    System.out.println(InternetAddress.toString(messageList.get(i).getFrom()));
                    row[1]=InternetAddress.toString(messageList.get(i).getFrom());
                    row[2]=messageList.get(i).getSubject();
                    rowData[index]=row;
                } catch (MessagingException ex) {
                    Logger.getLogger(Organizer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return rowData;
        }
        private Object[][] getEmails(){
            RetrieveEmails email=new RetrieveEmails();
            ArrayList<User> useraccounts=userAccounts.getAccounts();
            Object output[][]=null;
            if(email.process(useraccounts.get(0).getUserName(), useraccounts.get(0).getPassword())){
                Message[] messages=email.getMessages();
                Object rowData[][]=generateRowData(messages);
                output=rowData;
            }
            return output;
        }

        private void createEditorPanel(){
            editorPanel=new JPanel();
            editorPanel.setLayout(null);
            editorPanel.setBounds((int)((getWidth()-getWidth()*0.10)*0.415), headerHeight+2, getWidth(), getHeight());
            editorPanel.setBackground(Color.green);
            editorPanel.setVisible(true);
        }
    }
    
    private JButton createFolder(String name, int y){
        JButton button=new JButton(name);
        button.setForeground(Color.blue);
        button.setBackground(Color.white);
        button.setFont(textfont);
        button.setBounds(0, y, (int)(getWidth()*0.10), folderHeight);
        button.setVisible(true);
        button.addActionListener(new FolderListener());
        folderPanel.add(button);
        return button;
    }
    
    //#####################################################################################################
    //-------------------------------------EVENT LISTENER--------------------------------------------------
    
    //RegisterListener - class to handle action event when Register button is clicked
    class RegisterListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event) {
            createRegisterPanel(flashWindow);
            gp_next.setVisible(true);
        }
    }
    class key extends KeyAdapter
    {
         public boolean checkUser(){
            boolean result=true;
            User user=new User(user_name.getText(), String.valueOf(upassword.getPassword()));
            if(!DBOrganizer.isAuthorized(user)){
                result=false;
            }else{
                userAccounts=DBOrganizer.getEmailDetails(user.getUserName());
            }
            return result;
        }
  
        public void keyPressed(KeyEvent e) 
        {
//            System.out.println(e.getKeyCode());
           if(e.getKeyCode()==10)
           {
//               System.out.println("inside here");
               if(checkUser())
               {
                   userWindow();
               }
           }
        }
    }
    
    //NBListener - class to handle action event when next/back/add/already registered? button is clicked
    class NBListener implements ActionListener{
        public boolean checklength()  
        {
           boolean makeTransition=true;
           if(pincode.getText().length()<6)
           {
               makeTransition=false;
               pincode_length.setVisible(true);
           }
            return makeTransition;
        }
        
        @Override
        public void actionPerformed(ActionEvent item) {
            String buttonName=item.getActionCommand();
            int index=registrationPane.getSelectedIndex();
            if(buttonName.toLowerCase().equals("next")){
                int current_index=registrationPane.getSelectedIndex();
                boolean makeTransition=true;
                if(current_index == 0){
                    makeTransition=checkGeneralPanel();
                }else if(current_index == 1){
                    makeTransition=checkContactPanel();
                    makeTransition=checklength();
                }
                if(makeTransition){
                    registrationPane.setSelectedIndex(++index);
                }
            }else if(buttonName.toLowerCase().equals("back")){
                registrationPane.setSelectedIndex(--index);
            }else if(buttonName.toLowerCase().equals("add")){
                boolean makeTransition=true;
                makeTransition=checkSettingPanel();
            }else{
                registered.setVisible(false);
                gp_next.setVisible(false);
                registerPanel.setVisible(false);
                flashPanel.setVisible(true);
            }
        }
    }
    
    //ForgotListener - class to handle action event when forgot button is clicked
    class ForgotListener implements ActionListener{
        JDialog forgotDialog;
        JLabel text;
        JTextField text_field;
        JPasswordField password_field;
        JButton ok;
        @Override
        public void actionPerformed(ActionEvent event) {
            forgotDialog=new JDialog(flashWindow, true);
            forgotDialog.setSize(getWidth()/6, getHeight()/2);
            
            JPanel dialogPanel=new JPanel();
            dialogPanel.setLayout(null);
            dialogPanel.setBackground(Color.white);
            dialogPanel.setSize(getWidth()/6, getHeight()/2);
            dialog_header(dialogPanel);
            JLabel optionLabel=new JLabel("Choose an option");
            optionLabel.setFont(textfont);
            optionLabel.setBounds(2, 52, 100, 30);
            ButtonGroup radioGroup=new ButtonGroup();
            JRadioButton id=new JRadioButton("username");
            id.setBackground(Color.white);
            id.setFont(textfont);
            id.setBounds(20, 90, 100, 30);
            id.addActionListener(new OptionListener());
            JRadioButton password=new JRadioButton("password");
            password.setBackground(Color.white);
            password.setFont(textfont);
            password.setBounds(20, 120,100, 30);
            password.addActionListener(new OptionListener());
            
            radioGroup.add(id);
            radioGroup.add(password);
            
            text=new JLabel();
            text.setBackground(Color.white);
            text.setFont(textfont);
            text.setBounds(20, 160, 150, 30);
            text.setVisible(false);

            text_field=new JTextField();
            text_field.setBounds(20, 210, 150, 30);
            text_field.setVisible(false);
            
            password_field=new JPasswordField();
            password_field.setBounds(20, 210, 150, 30);
            password_field.setVisible(false);
            
            ok=new JButton("OK");
            ok.setFont(textfont);
            ok.setForeground(Color.red);
            ok.setBounds(100, 260, 70, 20);
            ok.addActionListener(new OptionListener());
            ok.setVisible(false);
            
            dialogPanel.add(optionLabel);
            dialogPanel.add(id);
            dialogPanel.add(password);
            dialogPanel.add(text);
            dialogPanel.add(text_field);
            dialogPanel.add(password_field);
            dialogPanel.add(ok);
            
            forgotDialog.add(dialogPanel);
            forgotDialog.setLocation((getWidth()/4)+(getWidth()/6), getHeight()/4);
            forgotDialog.setVisible(true);
        }
    
        //OptionListener - class to handle action event when radio button is clicked in forgot dialog window
        class OptionListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent event) {
                Object source=event.getSource();
                if(source instanceof JRadioButton){
                    JRadioButton button =(JRadioButton)source;
                    String buttonName=button.getText();
                    text.setText("Enter your "+buttonName);
                    text.setVisible(true);
                    if(buttonName.toLowerCase().equals("username")){
                        text_field.setText("");
                        text_field.setVisible(true);
                        password_field.setVisible(false);
                    }else{
                        text_field.setVisible(false);
                        password_field.setText("");
                        password_field.setVisible(true);
                    }
                    ok.setVisible(true);
                }else if (source instanceof JButton){
                    JButton button=(JButton)source;
                    if(button.getText().toLowerCase().equals("ok")){
                        forgotDialog.dispose();
                    }
                }
            }
        }
    }
    
    //LoginListener - class to handle action event when Login button is clicked in flash window
    class LoginListener implements ActionListener{
        public boolean checkUser(){
            boolean result=true;
            User user=new User(user_name.getText(), String.valueOf(upassword.getPassword()));
            if(!DBOrganizer.isAuthorized(user)){
                result=false;
            }else{
                userAccounts=DBOrganizer.getEmailDetails(user.getUserName());
//                System.out.println(userAccounts.getUserName());
//                ArrayList<User> accounts=userAccounts.getAccounts();
//                for(int i=0;i<accounts.size();i++){
//                    System.out.println(accounts.get(i).getUserName()+"\t\t"+accounts.get(i).getPassword());
//                }
            }
            return result;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if(checkUser()){
                userWindow();
            }
        }
    }
    
    //FolderListener - class to handle action event when inbox/sent/outbox/draft button is clicked 
    class FolderListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
//           System.out.println(e.getActionCommand());
           RetrieveEmails emails=new RetrieveEmails();
           String yahooFile=Organizer.class.getResource("../info/yahoo.properties").getPath();
//           System.out.println(yahooFile);
        }
    }
    
    class FolderTreeListener implements TreeSelectionListener{

        @Override
        public void valueChanged(TreeSelectionEvent event) {
//            System.out.println("valueChanged");
            DefaultMutableTreeNode node=(DefaultMutableTreeNode)event.getNewLeadSelectionPath().getLastPathComponent();
            String nodeName=node.getUserObject().toString();
            if(nodeName.toLowerCase().equals("compose")){
                composeWindow();
            }else if(nodeName.toLowerCase().equals("inbox")){
                
            }else if(nodeName.toLowerCase().equals("sent")){
                
            }else if(nodeName.toLowerCase().equals("outbox")){
                
            }else if(nodeName.toLowerCase().equals("draft")){
                
            }
        }
    }
    
    class FolderTreeMouseListener extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent event) {
//            System.out.println("Click");
            int x = (int) event.getPoint().getX();
            int y = (int) event.getPoint().getY();
            TreePath path = tree.getPathForLocation(x, y);
            String nodeName=((DefaultMutableTreeNode)path.getLastPathComponent()).getUserObject().toString();
            if(nodeName.toLowerCase().equals("compose")){
                composeWindow();
            }
        }

    }
    
    class ComposeWindowAdapter extends WindowAdapter{

        @Override
        public void windowClosing(WindowEvent e) {
            composeFrame=null;
        }
    }
    class Change implements ChangeListener
    {

        @Override
        public void stateChanged(ChangeEvent event) 
        {
            tab_index=accountTabsPane.getSelectedIndex();     
//            sendmail(index);
        }
        
    }
    public void sendmail(int index)
    {
        ArrayList<User> accounts=userAccounts.getAccounts();
        String from=accounts.get(index).getUserName();
        if(index==0)
        {
        String subject=subject_field.getText();
        String message_content=message.getText();
//        String from=cc_field.getText();
        String hostname="pop.mail.yahoo.com";
        String[] to=to_field.getText().split(","); //shrey_khanna@ymail.com, demo@jkt.com
       
        Properties props=fetchMailProperties(index);
//        props.put("mail.smtp.host", hostname);
//        props.put("mail.smtp.user", from);
//        props.put("mail.smtp.auth", "false");
        
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];
            for( int i=0; i < to.length; i++ ){
                toAddress[i] = new InternetAddress(to[i].trim());
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
//                System.out.println(to[i].trim());
            }
            String datecur=Calendar.getInstance().getTime().toString();
            String datecur2[]=datecur.split(" ");
            String curdate=datecur2[1]+" "+datecur2[2]+" "+datecur2[5];
            
            message.setSubject(subject);
            message.setContent(message_content, "text/html");
            Transport transport = session.getTransport("smtp");
            transport.connect();
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException ex) 
        {
            ex.printStackTrace(); 
        
        }
     
       }
        else if(index==1)
        {
        String subject=subject_field.getText();
        String message_content=message.getText();
//        String from=cc_field.getText();
        String hostname="pop.gmail.com";
        String[] to=to_field.getText().split(",");
       
        Properties props=fetchMailProperties(index);
//        props.put("mail.smtp.host", hostname);
//        props.put("mail.smtp.user", from);
//        props.put("mail.smtp.auth", "false");
        
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];
            for( int i=0; i < to.length; i++ ){
                toAddress[i] = new InternetAddress(to[i].trim());
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }
            String datecur=Calendar.getInstance().getTime().toString();
            String datecur2[]=datecur.split(" ");
            String curdate=datecur2[1]+" "+datecur2[2]+" "+datecur2[5];
            
            message.setSubject(subject);
            message.setContent(message_content, "text/html");
            Transport transport = session.getTransport("smtp");
            transport.connect();
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException ex) 
        {
            ex.printStackTrace(); 
        
        }
     
    }
        }
 
  
    class Send implements ActionListener
    {

    @Override
    public void actionPerformed(ActionEvent e) 
    {
      sendmail(tab_index);
    }
        
    }
    class TableContentSelect extends MouseAdapter{
        JTable table;
        @Override
        public void mouseClicked(MouseEvent event) {
//            System.out.println(event.getSource());
            if(event.getSource() instanceof JTable){
                table=(JTable)event.getSource();
//                System.out.println(event.getButton());
//                System.out.println("Total rows = "+table.getRowCount());
                System.out.println("Current selected = "+table.rowAtPoint(event.getPoint())+" , "+table.columnAtPoint(event.getPoint()));
            }
        }
    }
    //#####################################################################################################
    //main() - main business logic goes here
    public static void main(String args[])
    {
        Organizer application=new Organizer();
        application.flashWindow();
    }
    
    // fetchMailProperties = to fetch properties for a mail account
    public Properties fetchMailProperties(int index){
        String propertyFile=null;
        String resource="";
        switch(index){
            case 0:
                resource="../info/yahoo.properties";
            break;
            case 1:
                resource="../info/gmail.properties";
            break;
        }
        propertyFile=Organizer.class.getResource(resource).getPath();
        Properties props=new Properties();
        try {
                props.load(new FileInputStream(propertyFile));
        } catch (IOException ioe) {
                System.out.println("Properties file IO Exception: " + ioe);
        }
        return props;
    }
    
}
