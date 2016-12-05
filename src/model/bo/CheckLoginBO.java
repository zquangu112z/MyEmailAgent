/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bo;

import model.bean.Infor;
import model.dao.CheckLoginDAO;

/**
 *
 * @author nguqt
 */
public class CheckLoginBO {

    public Infor check(String id, String pass) {
        CheckLoginDAO checkLoginDAO = new CheckLoginDAO();
        return checkLoginDAO.check(id, pass);
    }

}
