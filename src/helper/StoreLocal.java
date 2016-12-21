package helper;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import model.bean.MailContent;

/**
 * Locally store/restore mail
 *
 * @author nguqt
 */
public class StoreLocal {

    public boolean storeMailsToPref(ArrayList<MailContent> mailContents, Preferences pref, String username) {
        try {
            MailContent mc;
            for (int i = 0; i < 10; i++) {
                mc = mailContents.get(i);
                pref.put(username + "subject" + i, mc.getSubject());
                pref.put(username + "from" + i, mc.getFrom());
                pref.put(username + "to" + i, mc.getTo());
                pref.put(username + "time" + i, mc.getTime());
                pref.put(username + "body" + i, mc.getBody());
                pref.put(username + "type" + i, mc.getType() + "");
            }
        } catch (ArrayIndexOutOfBoundsException aioobe) {//phong truong hop list mail < 10 
            System.out.println("Loi luu mail to preference" + aioobe);
        } catch (Exception e) {
            System.out.println("Loi luu mail" + e);
            return false;
        }
        System.out.println("Store mail local thanh congs");
        return true;
    }

    public ArrayList<MailContent> restoreMailsFromPref(Preferences pref, String username) {
        ArrayList<MailContent> mailContents = new ArrayList<>();
        MailContent mc;
        try {
            for (int i = 0; i < 5; i++) {
                mc = new MailContent(pref.get(username + "subject" + i, "loading..."),
                        pref.get(username + "from" + i, "loading..."),
                        pref.get(username + "to" + i, "loading..."),
                        pref.get(username + "time" + i, "loading..."),
                        pref.get(username + "body" + i, "loading..."),
                        Integer.parseInt(pref.get(username + "type" + i, "1")));
                mailContents.add(mc);
            }
        } catch (ArrayIndexOutOfBoundsException aioobe) {//phong truong hop list mail < 10 
            System.out.println("Loi luu mail to preference" + aioobe);
        } catch (Exception e) {
            System.out.println("Loi luu mail" + e);

        }
        System.out.println("Restore mail local thanh congs");
        return mailContents;
    }

    public void test() {
        Preferences pref = Preferences.userRoot().node(this.getClass().getName());
        try {
            pref.clear();
        } catch (BackingStoreException ex) {
            Logger.getLogger(StoreLocal.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<MailContent> mailContents = this.restoreMailsFromPref(pref, "timexdanang@gmail.com");

        this.storeMailsToPref(mailContents, pref, "timexdanang@gmail.com");
        this.restoreMailsFromPref(pref, "timexdanang@gmail.com");
        System.out.println("So luong mail: " + mailContents.size());
        System.out.println("Mail so 1: " + mailContents.get(0).getBody());

    }

    public static void main(String[] args) {
        StoreLocal sm = new StoreLocal();
        sm.test();
    }
}
