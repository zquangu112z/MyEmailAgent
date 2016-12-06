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
public class SendEmail {

    //String to = "zquangu112z@gmail.com";//change accordingly  
    Properties props;
    
    public SendEmail() {
        //Get the session object  
        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
    }
    
    
    public void sendMail(String from, String pass, String to, String subject, String content) {
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, pass);//change accordingly  
                    }
                });

        //composr email with attachment
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));//change accordingly  
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Quang Ngu De Thuong");
            
            Multipart multipart = new MimeMultipart();
            // Create the message part 
            BodyPart messagePart = new MimeBodyPart();
            messagePart.setText("Nhiep anh gia dep trai nhat duyen hai mien Trung");

            // Create the attachment part 
            BodyPart attachmentPart = new MimeBodyPart();
            String filename = "C:\\Users\\nguqt\\Desktop\\images.jpg";
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

    public static void main(String[] args) {
        SendEmail se = new SendEmail();
        se.sendMail("timexdanang@gmail.com", "quangu112", "zquangu112z@gmail.com", "hello", "hello");
    }
}
