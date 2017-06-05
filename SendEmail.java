import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vostro
 */
public class SendEmail {
    /**************************************************************************/
    //  sendMail -  send the html content to a group of users
    /**************************************************************************/
    public static void sendMail(String content){
        String hostname="appmail.federated.fds";
        String from="astra@macys.com";
//        String[] to = {"yc03sp1@macys.com", "p139ag1@macys.com", "yc03g1s@macys.com", "yc03r3b@macys.com"};
        String[] to = {"yc03sp1@macys.com"};
        //configuration details used to configure Session object
        
        Properties props=new Properties();
        props.put("mail.smtp.host", hostname);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.auth", "false");
        
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];
            for( int i=0; i < to.length; i++ ){
                toAddress[i] = new InternetAddress(to[i]);
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }
            String datecur=Calendar.getInstance().getTime().toString();
            String datecur2[]=datecur.split(" ");
            String curdate=datecur2[1]+" "+datecur2[2]+" "+datecur2[5];
            
            message.setSubject("SEO Broken Links Report for "+curdate);
            message.setContent(content, "text/html");
            Transport transport = session.getTransport("smtp");
            transport.connect();
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        
    }
    public static void main(String args[]){
	sendMail("<a href=\"http://11.16.155.103/iw-cc/teamsite/DownloadFile.jsp\"><font style=\"color:red\">download</font></a>");
    }
}

