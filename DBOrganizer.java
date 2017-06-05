package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class DBOrganizer {
    private static Connection conn=null;
    public static Connection getConnection(){
        String IP=null, dbName=null, usrName=null, pwd=null;
        String propertyFile=DBOrganizer.class.getResource("../info/dbConfig.properties").getPath();
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(propertyFile));
        } catch (IOException ioe) {
                System.out.println("Properties file IO Exception: " + ioe);
        }
        IP = props.getProperty("IP");
        dbName = props.getProperty("dbName");
        usrName = props.getProperty("usrName");
        pwd = props.getProperty("pwd");
        Connection con=null;
        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            con = DriverManager.getConnection("jdbc:oracle:thin:@"+IP+"/"+dbName+"",""+usrName+"",""+pwd+"");
        }catch(SQLException sqle){
            System.out.println("PROBLEM IN GETTING CONNECTION");
            sqle.printStackTrace();
        }
        return con;
    }
    public static boolean isAuthorized(User user){
        boolean result=false;
        conn=getConnection();
        if(conn != null){
            StringBuffer query=new StringBuffer("select username, upassword from uaccounts where username = '");
            query.append(user.getUserName()).append("'");
            try {
                Statement stmt=conn.createStatement();
                ResultSet rs=stmt.executeQuery(query.toString());
                if(rs.next()){
                    String userName=rs.getString(1);
                    String password=rs.getString(2);
                    if(user.getUserName().equals(userName) && user.getPassword().equals(password)){
                        result=true;
                    }
                }
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
    
    public static UserAccounts getEmailDetails(String userName){
        UserAccounts userAccounts=null;
        conn=getConnection();
        if(conn != null){
            StringBuffer query=new StringBuffer("select emailid, emailpwd from uemails where username = '");
            query.append(userName).append("'");
            try {
                Statement stmt=conn.createStatement();
                ResultSet rs=stmt.executeQuery(query.toString());
                ArrayList<User> accounts=null;
                while(rs.next()){
                    User user=new User(rs.getString(1), rs.getString(2));
                    if(accounts == null){
                        accounts=new ArrayList<User>();
                    }
                    accounts.add(user);
                }
                userAccounts=new UserAccounts(userName, accounts);
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return userAccounts;
    }
}
