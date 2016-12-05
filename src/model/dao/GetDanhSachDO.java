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
public class GetDanhSachDO {
    Infor[] danhSach() {
        Infor infor = new Infor("chipheo", "yeuthino", "Chi Pheo", "35");
        Infor infor2 = new Infor("thino", "yeubanthan", "Thi No", "34");
        Infor infor3 = new Infor("bakien", "khongyeuai", "Ba Kien", "65");
        return new Infor[] {infor, infor2, infor3};
    }
    
    public Infor [] getds(){
        return danhSach();
    }
}
