/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bo;

import helper.Gmail;
import java.util.ArrayList;
import model.bean.MailContent;

/**
 *
 * @author nguqt
 */
public class ParseMails {

    public ParseMails() {

    }

    //lan dau load mail 
    public ArrayList<MailContent> loadMailTo_Inbox() {
        Gmail gmail2 = new Gmail();
        return gmail2.getInboxMails();
    }
}
