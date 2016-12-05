package helper;

import java.util.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author nguqt
 */
public class Gmail {

    Properties properties;
    Session session;
    String host = "pop.gmail.com";
    String username = "timexdanang@gmail.com";

    public Gmail() {
        //Get the session object  
        properties = new Properties();

        properties.put("mail.pop3.host", host);
        properties.put("mail.pop3.port", "995");
        // properties.put("mail.pop3.starttls.enable", "true");
        properties.put("mail.pop3.user", username);
            //properties.put("mail.pop3.disablecapa", true);
        // Start SSL connection
        // properties.put("mail.pop3.socketFactory", 995);
        properties.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    }

    public void sendMail(String from, String to) {

    }

    public boolean checkLoginGmail(String gmail, String password) {
//        gmail = "timexdanang@gmail.com";
//        password = "quangu112";
        try {
            Session session = Session.getDefaultInstance(properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("timexdanang@gmail.com", "quangu112");//change accordingly  
                        }
                    });
            Store store = session.getStore("pop3");
            store.connect("smtp.gmail.com", "timexdanang@gmail.com", "quangu112");

            return true;
        } catch (Exception ex) {
            System.out.println("" + ex);
            return false;
        }
    }
    /*
     public void sendMail() {
     String to = "zquangu112z@gmail.com";//change accordingly  

     session = Session.getDefaultInstance(props,
     new javax.mail.Authenticator() {
     protected PasswordAuthentication getPasswordAuthentication() {
     return new PasswordAuthentication("timexdanang@gmail.com", "quangu112");//change accordingly  
     }
     });

     //composr email with attachment
     try {
     MimeMessage message = new MimeMessage(session);
     message.setFrom(new InternetAddress("timexdanang@gmail.com"));//change accordingly  
     message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
     message.setSubject("Quang Ngu De Thuong");

     Multipart multipart = new MimeMultipart();
     // Create the message part 
     BodyPart messagePart = new MimeBodyPart();
     messagePart.setText("Nhiep anh gia dep trai nhat duyen hai mien Trung");

     // Create the attachment part 
     BodyPart attachmentPart = new MimeBodyPart();
     String filename = "C:\\Users\\nguqt\\Desktop\\Desktop\\avatar.jpg";
     DataSource source = new FileDataSource(filename);
     attachmentPart.setDataHandler(new DataHandler(source));
     attachmentPart.setFileName(filename);

     multipart.addBodyPart(attachmentPart);
     multipart.addBodyPart(messagePart);

     // Send the complete message parts
     message.setContent(multipart);
     //send message  
     Transport.send(message);

     System.out.println("message sent successfully");

     } catch (Exception e) {
     System.out.println("" + e);
     }

     }
     */

    public static void main(String[] args) {
    }

}
