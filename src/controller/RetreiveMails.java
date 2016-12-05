/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import helper.ReceiveMail;
import static helper.ReceiveMail.check;
import model.bean.MailContent;

/**
 *
 * @author nguqt
 */
public class RetreiveMails {

    ReceiveMail rm = new ReceiveMail();
    String host = "pop.gmail.com";// change accordingly
    String mailStoreType = "pop3";
    final String username = "timexdanang@gmail.com";// change accordingly
    final String password = "quangu112";// change accordingly

    public RetreiveMails() {
        
    }
    
    //lan dau load mail 
    public void loadMail_Inbox(){
        rm.check(host, mailStoreType, username, password);
    }
}
