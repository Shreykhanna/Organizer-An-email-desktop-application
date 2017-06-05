package model;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author admin
 */
public class RetrieveEmails {
    
    Store store=null;
    Folder inbox=null;
    private Message[] messages=null;
    
    public Message[] getMessages() {
        return messages;
    }

    public void setMessages(Message[] messages) {
        this.messages = messages;
    }
    
    public void connect_to_MailServer(String emailAddress, String pwd){
        String hostName="pop.mail.yahoo.com";
        final String userName=emailAddress;
        final String password=pwd;
        String provider="pop3";
        Properties props=new Properties();
        props.put("mail.pop3.host" , hostName);
        props.put("mail.pop3.user" , userName);
        // Start SSL connection
        
        props.put("mail.pop3.socketFactory" , 995 );
        props.put("mail.pop3.socketFactory.class" , "javax.net.ssl.SSLSocketFactory" );
        props.put("mail.pop3.port" , 995);
        Session session=Session.getDefaultInstance(props,new Authenticator() {
        @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication( userName , password);
            }
        });
        try {
            store=session.getStore(provider);
//            store.connect(hostName, userName, password);
            store.connect(hostName,995, userName, password);
        } catch (NoSuchProviderException ex) {
            ex.printStackTrace();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
    
    public Message[] retrieveInbox(){
        Message[] messages=null;
        try {
            inbox=store.getFolder("INBOX");
            if(inbox != null){
                inbox.open(Folder.READ_ONLY);
                messages=inbox.getMessages();
            }
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return messages;
    }
    
    public void cleanUp(){
        try {
            inbox.close(false);
            store.close();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
    
//    public static void main(String args[]){
//        RetrieveEmails emails=new RetrieveEmails();
//        emails.connect_to_MailServer("paul_siddharth@yahoo.com", "27566897!@#sid");
//        Message[] messages=emails.retrieveInbox();
//        System.out.println("Count = "+messages.length);
//        for(int i=messages.length -1;i>=0;i--){
//            try {
////                System.out.println(messages[i].getH);
//                String from = InternetAddress.toString(messages[i].getFrom( ));
//                if(from != null){
//                    System.out.println("From - "+from);
//                }
//                String subject = messages[i].getSubject();
//                if (subject != null)
//                    System.out.println("Subject: " + subject);
//            } catch (MessagingException ex) {
//                ex.printStackTrace();
//            }
//        }
//        emails.cleanUp();
//    }
    public boolean process(String emailAddress, String password){
        boolean result=false;
        connect_to_MailServer(emailAddress, password);
        messages=retrieveInbox();
        if(messages != null){
            result=true;
        }
        return result;
    }
}
