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
import model.bean.MailContent;

/**
 *
 * @author nguqt
 */
public class Gmail {

    Properties properties;
    static Session session;
    static Store store;
    static String username, password;

    public Gmail() {
        //Get the session object  
        properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
    }

    /**
     * Check login
     *
     * @param gmail
     * @param password
     * @return
     */
    public boolean connectGmail(String gmail, String pass) {
        try {
            this.username = gmail;
            this.password = pass;

            initSession();
            //store = session.getStore("pop3");
            store = session.getStore("imaps");

            store.connect("smtp.gmail.com", this.username, this.password);
            System.out.println("asasdfasd");

            return true;
        } catch (Exception ex1) {
            System.out.println("ex1" + ex1);
            return false;
        }
    }

    private void initSession() {
        session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    public ArrayList<MailContent> getInboxMails(String gmail, String pass) {
        ArrayList<MailContent> mailContents = new ArrayList<>();

        try {

            System.setProperty("mail.mime.multipart.ignoreexistingboundaryparameter", "true");
            try {
                //System.out.println("this.username: " + this.username);
                connectGmail(gmail, pass);//TODO remove

            } catch (Exception e) {

                System.out.println("da ket noi: " + e);
            }
            //create the folder object and open it
//            Folder emailFolder = store.getFolder("[Gmail]/Sent Mail");
            Folder emailFolder = store.getFolder("Inbox");

//            Folder[] f = store.getDefaultFolder().list();
//            for (Folder fd : f) {
//                System.out.println(">> " + fd.getName());
//            }
            /*
             >> INBOX
             >> [Gmail]
             */
            emailFolder.open(Folder.READ_ONLY);
//            emailFolder.open(Folder.HOLDS_MESSAGES);

            //Get all Message objects from this Folder.
            Message[] messages = emailFolder.getMessages();

            System.out.println("messages.length---" + messages.length);
            Message message;
            for (int i = 0, n = 10; i < n; i++) {
                try {
                    message = messages[i];
//                    System.out.println("---------------------------------");
//                    System.out.println("Email Number " + (i + 1));
//                    System.out.println("Subject: " + message.getSubject());
//                    System.out.println("From: " + message.getFrom()[0]);
//                    System.out.println("Text: " + getTextFromMessage(message));
                    mailContents.add(new MailContent(message.getSubject(), message.getFrom()[0] + "", username, "now", getTextFromMessage(message), 0));
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                    break;
                } catch (MessagingException e) {
                    System.out.println("loi o day" + e);
                }
            }
            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            System.out.println("" + e);
        } catch (Exception e) {
            System.out.println("" + e);
        }

        return mailContents;
    }

    /**
     * Default: user = "timexdanang@gmail.com", pass = "quangu112"
     *
     * @return
     */
    public ArrayList<MailContent> getInboxMails() {
        ArrayList<MailContent> mailContents = new ArrayList<>();

        try {

            try {
                //System.out.println("this.username: " + this.username);
                //connectGmail(this.username, this.password);//TODO remove
                connectGmail("timexdanang@gmail.com", "quangu112");//TODO remove
            } catch (Exception e) {
                System.out.println("da ket noi: " + e);
            }
            //create the folder object and open it
            //Folder emailFolder = store.getFolder("INBOX");
            //Folder emailFolder = store.getFolder("[Gmail]/Sent Mail");
            Folder emailFolder = store.getFolder("[Gmail]/Inbox");

//            emailFolder.open(Folder.READ_ONLY);
            emailFolder.open(Folder.HOLDS_MESSAGES);

            //Get all Message objects from this Folder.
            Message[] messages = emailFolder.getMessages();

            System.out.println("messages.length---" + messages.length);

            for (int i = 0, n = 10; i < n; i++) {
                try {
                    Message message = messages[i];
                    System.out.println("---------------------------------");
                    System.out.println("Email Number " + (i + 1));
                    System.out.println("Subject: " + message.getSubject());
                    System.out.println("From: " + message.getFrom()[0]);
                    //System.out.println("Text: " + getTextFromMessage(message));
                    mailContents.add(new MailContent(message.getSubject(), message.getFrom()[0] + "", username, "now", getTextFromMessage(message), 0));
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                    break;
                }
            }
            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            System.out.println("" + e);
        } catch (MessagingException e) {
            System.out.println("loi o day" + e);
        } catch (Exception e) {
            System.out.println("" + e);
        }

        return mailContents;
    }

    String result = "";
    MimeMultipart mimeMultipart;

    private String getTextFromMessage(Message message) throws Exception {
        result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    BodyPart bodyPart;

    private String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart) throws Exception {
        result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                System.out.println("Xu li html");
                result = result + bodyPart.getContent().toString().replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
            }
//            else if (bodyPart.getContent() instanceof MimeMultipart) {
            //result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
//            }
        }
        return result;
    }

    /**
     * Testing
     *
     * @param args
     */
    public static void main(String[] args) {
        Gmail gmail = new Gmail();
        //phai connect truoc (stimulate dang nhap) , 
        //khong duoc connect trong sendMail vi loi khac username so voi nguoi 
        //dang dang nhap
        System.out.println("" + gmail.connectGmail("timexdanang@gmail.com", "quangu112"));
        Gmail gmail2 = new Gmail();
//        try {
//            String [] tos = new String[]{"zquangu112z@gmail.comdf", "nguqtruong@gmail.com"};
//            gmail2.sendMail("timexdanang@gmail.com", "quangu112", tos, "asjdfhasd", "jahsdflaksd", null);
//        } catch (Exception me) {
//            
//            System.out.println("Gui mail bi loi" + me);
//            System.out.println("Chua dang nhap (connect email");
//        }
        gmail2.getInboxMails("timexdanang@gmail.com", "quangu112");

    }

    /**
     * Simple Sending Mail Method: without attachments, multi addresses
     *
     * @param from
     * @param pass
     * @param to
     * @param subject
     * @param content
     * @throws MessagingException
     */
    public void sendMail(String from, String pass, String to, String subject, String content) throws MessagingException {

        try {
            initSession();
        } catch (Exception e) {
            System.out.println("initSession lan 2" + e);
        }
        //composr email with attachment
        //try {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));//change accordingly  
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);

        Multipart multipart = new MimeMultipart();
        // Create the message part 
        BodyPart messagePart = new MimeBodyPart();
        messagePart.setText(content);

        multipart.addBodyPart(messagePart);

        // Send the complete message parts
        message.setContent(multipart);
        //send message  
        Transport.send(message);

        System.out.println("message sent successfully");
    }

    /**
     * Send mail with attachments
     *
     * @param from
     * @param pass
     * @param to
     * @param subject
     * @param content
     * @param attchmentsPath
     * @throws MessagingException
     */
    public void sendMail(String from, String pass, String to, String subject, String content, ArrayList<String> attchmentsPath) throws MessagingException {

//        if (attchmentsPath.isEmpty()) {
//            this.sendMail(from, pass, to, subject, content);
//            return;
//        }
        try {
            initSession();
        } catch (Exception e) {
            System.out.println("initSession lan 2" + e);
        }
        //composr email with attachment
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));//change accordingly  
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        message.setSubject(subject);

        Multipart multipart = new MimeMultipart();
        // Create the message part 
        BodyPart messagePart = new MimeBodyPart();
        messagePart.setText(content);

        multipart.addBodyPart(messagePart);
        // Create the attachment part 
        try {
            for (String path : attchmentsPath) {
                BodyPart attachmentPart = new MimeBodyPart();
                DataSource source = new FileDataSource(path);
                attachmentPart.setDataHandler(new DataHandler(source));
                attachmentPart.setFileName(path);
                multipart.addBodyPart(attachmentPart);
            }
        } catch (Exception ex) {
            System.out.println("Khong co attchment: " + ex);
        }

        // Send the complete message parts
        message.setContent(multipart);
        //send message  
        Transport.send(message);

        System.out.println("message sent successfully");
    }

    /**
     * Send mail with attachments & multi address
     *
     * @param from
     * @param pass
     * @param to
     * @param subject
     * @param content
     * @param attchmentsPath
     * @throws MessagingException
     */
    public void sendMail(String from, String pass, String[] to, String subject, String content, ArrayList<String> attchmentsPath) throws MessagingException {

        try {
            initSession();
            //connectGmail(from, pass);
        } catch (Exception e) {
            System.out.println("initSession lan 2" + e);
        }
        //composr email with attachment
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setSubject(subject);

        InternetAddress[] address = new InternetAddress[to.length];
        for (int i = 0; i < to.length; i++) {
            address[i] = new InternetAddress(to[i]);
        }
        message.addRecipients(Message.RecipientType.TO, address);

        Multipart multipart = new MimeMultipart();
        // Create the message part 
        BodyPart messagePart = new MimeBodyPart();
        messagePart.setText(content);

        multipart.addBodyPart(messagePart);
        // Create the attachment part 
        try {
            for (String path : attchmentsPath) {
                BodyPart attachmentPart = new MimeBodyPart();
                DataSource source = new FileDataSource(path);
                attachmentPart.setDataHandler(new DataHandler(source));
                attachmentPart.setFileName(path);
                multipart.addBodyPart(attachmentPart);
            }
        } catch (Exception ex) {
            System.out.println("Khong co attchment: " + ex);
        }

        // Send the complete message parts
        message.setContent(multipart);
        //send message  
        Transport.send(message);

        System.out.println("message sent successfully");

    }
}

//old version: using pop3 => problem: can't get email by label. Just "INBOX" folder
///**
// *
// * @author nguqt
// */
//public class Gmail {
//
//    Properties properties;
//    static Session session;
//    static Store store;
//    String host = "pop.gmail.com";
//    static String username, password;
//
//    public Gmail() {
//        //Get the session object  
//        properties = new Properties();
//
//        properties.put("mail.pop3.host", host);
//        properties.put("mail.pop3.port", "995");
//        //properties.put("mail.pop3.user", username);
//        properties.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//
//        properties.put("mail.smtp.host", "smtp.gmail.com");
//        properties.put("mail.smtp.socketFactory.port", "465");
//        properties.put("mail.smtp.socketFactory.class",
//                "javax.net.ssl.SSLSocketFactory");
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.port", "465");
//
//    }
//
//    /**
//     * Check login
//     *
//     * @param gmail
//     * @param password
//     * @return
//     */
//    public boolean connectGmail(String gmail, String pass) {
//        try {
//            this.username = gmail;
//            this.password = pass;
//
//            initSession();
//            store = session.getStore("pop3");
//            store.connect("smtp.gmail.com", this.username, this.password);
//            System.out.println("asasdfasd");
//
//            return true;
//        } catch (Exception ex1) {
//            System.out.println("ex1" + ex1);
//            return false;
//        }
//    }
