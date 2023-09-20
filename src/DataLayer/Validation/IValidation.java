/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataLayer.Validation;


import java.util.Date;

/**
 *
 * @author nhutm
 */

public interface IValidation {
    public boolean checkDateValid(String date);
    public long subDate(Date date1,Date date2);
    public String removeBlankString(String str);
    public boolean checkNoEmpty(String str);
    public int inputQuatity(int quantity);
    public String inputProductionDate(String productionDateS);
    public String inputExpDate(String Date,String expDate);
}
