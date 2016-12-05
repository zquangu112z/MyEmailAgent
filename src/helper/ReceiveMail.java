package helper;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import com.sun.mail.pop3.POP3Store;
import javax.mail.PasswordAuthentication;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.BodyPart;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;

public class ReceiveMail {

    public static void check(String host, String storeType, final String user, final String password) {
        try {

            //create properties field
            Properties properties = new Properties();

            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "995");
            // properties.put("mail.pop3.starttls.enable", "true");
            properties.put("mail.pop3.user", user);
            //properties.put("mail.pop3.disablecapa", true);
            // Start SSL connection
            // properties.put("mail.pop3.socketFactory", 995);
            properties.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            Session emailSession = Session.getDefaultInstance(properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(user, password);//change accordingly  
                        }
                    });

            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore(storeType);
            store.connect(host, user, password);

            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);

//            for (int i = 0, n = messages.length; i < n; i++) {
            for (int i = 0, n = 10; i < n; i++) {
                Message message = messages[i];
                System.out.println("---------------------------------");
                System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Text: " + getTextFromMessage(message));
            }
            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
//            String html = (String) bodyPart.getContent();
//            result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
            }
        }
        return result;
    }

    public static void main(String[] args) {

        String host = "pop.gmail.com";// change accordingly
        String mailStoreType = "pop3";
        final String username = "timexdanang@gmail.com";// change accordingly
        final String password = "quangu112";// change accordingly

        check(host, mailStoreType, username, password);

    }

}
//
//public class ReceiveMail {
//
//    public static void main(String[] args) {
//
//        String mailStoreType = "pop3";
//        String host = "pop.gmail.com";
//        final String user = "timexdanang@gmail.com";
//        final String password = "quangu112";
//        try {
//            //1) get the session object  
//            Properties properties = new Properties();
//            properties.put("mail.pop3.host", host);
//            properties.put("mail.pop3.port", "995");
//            properties.setProperty("mail.pop3.socketFactory.port", "995");
//            properties.put("mail.pop3.socketFactory.class",
//                    "javax.net.ssl.SSLSocketFactory");
//            properties.setProperty("mail.pop3.socketFactory.fallback", "false");
//
//            properties.put("mail.pop3.starttls.enable", "true");
//            Session emailSession = Session.getDefaultInstance(properties);
//            Session session = Session.getDefaultInstance(properties,
//                    new javax.mail.Authenticator() {
//                        protected PasswordAuthentication getPasswordAuthentication() {
//                            return new PasswordAuthentication(user, password);//change accordingly  
//                        }
//                    });
//
//            //2) create the POP3 store object and connect with the pop server  
//            POP3Store emailStore = (POP3Store) session.getStore(mailStoreType);
//            emailStore.connect(host, user, password);
//
//            //3) create the folder object and open it  
//            Folder emailFolder = emailStore.getFolder("INBOX");
//            emailFolder.open(Folder.READ_ONLY);
//
//            //4) retrieve the messages from the folder in an array and print it  
//            Message[] messages = emailFolder.getMessages();
//            for (int i = 0; i < messages.length; i++) {
//                Message message = messages[i];
//                System.out.println("---------------------------------");
//                System.out.println("Email Number " + (i + 1));
//                System.out.println("Subject: " + message.getSubject());
//                System.out.println("From: " + message.getFrom()[0]);
//                System.out.println("Text: " + message.getContent().toString());
//            }
//
//            //5) close the store and folder objects  
//            emailFolder.close(false);
//            emailStore.close();
//
//        } catch (NoSuchProviderException e) {
//            e.printStackTrace();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//}
