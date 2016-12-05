/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bo;

import helper.Gmail;
import javax.mail.Session;

/**
 *
 * @author nguqt
 */
public class CheckLoginBO {

   

    public boolean checkLoginGmail(String gmail, String password) {
        return new Gmail().checkLoginGmail(gmail, password);
    }

}
