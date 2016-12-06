package helper;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import model.bean.MailContent;

/**
 *
 * @author nguqt
 */
public class Gmail {

    Properties properties;
    static Session session;
    static Store store;
    String host = "pop.gmail.com";
    String username = "timexdanang@gmail.com";

    static int start = 0;
    static int end = 4;
    static int jump = 5;

    public Gmail(String username) {
        //Get the session object  
        properties = new Properties();

        properties.put("mail.pop3.host", host);
        properties.put("mail.pop3.port", "995");
        properties.put("mail.pop3.user", username);
        properties.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    }

    public Gmail() {
        //Get the session object  
        properties = new Properties();

        properties.put("mail.pop3.host", host);
        properties.put("mail.pop3.port", "995");
        properties.put("mail.pop3.user", username);
        properties.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    }

    //TODO remove permanent parametter
    /**
     * Check login
     *
     * @param gmail
     * @param password
     * @return
     */
    public boolean connectGmail(String gmail, String password) {
        try {
            session = Session.getDefaultInstance(properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("timexdanang@gmail.com", "quangu112");//change accordingly  
                        }
                    });
            store = session.getStore("pop3");
            store.connect("smtp.gmail.com", "timexdanang@gmail.com", "quangu112");
            System.out.println("asasdfasd");

            //TODO reset count mail
            start = 0;
            end = 4;
            return true;
        } catch (Exception ex) {
            System.out.println("" + ex);
            return false;
        }
    }

    public ArrayList<MailContent> getInboxMails() {
        ArrayList<MailContent> mailContents = new ArrayList<>();

        try {

            try {
                connectGmail("timexdanang@gmail.com", "quangu112");
            } catch (Exception e) {
                System.out.println("da ket noi: " + e);
            }
            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");

//            emailFolder.open(Folder.READ_ONLY);
            emailFolder.open(Folder.HOLDS_MESSAGES);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();//Get all Message objects from this Folder.
            start = end;
            end += jump;

            System.out.println("messages.length---" + messages.length);

            //            for (int i = 0, n = messages.length; i < n; i++) {
            for (int i = 0, n = 10; i < n; i++) {
                try {
                    Message message = messages[i];
                    System.out.println("---------------------------------");
                    System.out.println("Email Number " + (i + 1));
                    System.out.println("Subject: " + message.getSubject());
                    System.out.println("From: " + message.getFrom()[0]);
                    System.out.println("Text: " + getTextFromMessage(message));
                    //TODO change parametter: to
                    //MailContent mailContent = new MailContent(message.getSubject(), message.getFrom()[0]+"", username, "now" , getTextFromMessage(message),0 );
                    mailContents.add(new MailContent(message.getSubject(), message.getFrom()[0] + "", username, "now", getTextFromMessage(message), 0));
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                    break;
                }
            }
            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            System.out.println("" + e);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("" + e);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("" + e);
        }

        return mailContents;
    }

    private static String getTextFromMessage(Message message) throws Exception {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private static String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart) throws Exception {
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                System.out.println("TODO " + "xu li html");
                result = bodyPart.getContent().toString().replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Gmail gmail = new Gmail();
        System.out.println("" + gmail.connectGmail("sad", "asdf"));
        Gmail gmail2 = new Gmail();
        gmail2.getInboxMails();
    }

    public void sendMail(String timexdananggmailcom, String to, String subject, String content) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
