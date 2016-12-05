/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import model.bean.Infor;

/**
 *
 * @author nguqt
 */
public class CheckLoginDAO {

    Infor[] danhSach() {
        Infor infor = new Infor("chipheo", "yeuthino", "Chi Pheo", "35");
        Infor infor2 = new Infor("thino", "yeubanthan", "Thi No", "34");
        Infor infor3 = new Infor("bakien", "khongyeuai", "Ba Kien", "65");
        Infor infor4 = new Infor("timexdanang@gmail.com", "quangu112", "Chi Pheo", "35");
        return new Infor[]{infor, infor2, infor3, infor4};
    }

    public Infor check(String id, String pass) {
        Infor[] infors = danhSach();
        for (int i = 0; i < infors.length; i++) {
            if (id.compareTo(infors[i].getId()) == 0 && pass.compareTo(infors[i].getPass()) == 0) {
                return infors[i];
            }
        }
        return null;
    }
}
