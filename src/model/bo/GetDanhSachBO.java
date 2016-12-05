/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bo;

import model.bean.Infor;
import model.dao.GetDanhSachDO;

/**
 *
 * @author nguqt
 */
public class GetDanhSachBO {
    public Infor[] getds(){
        GetDanhSachDO getdsDO = new GetDanhSachDO();
        return getdsDO.getds();
    }
}
