/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataLayer.WareHouse;

/**
 *
 * @author nhutm
 */
// ep 00001 
// ep 00002
//ep 00003
//imp 00001
//imp 00002
public class ExportReceipt extends Receipt{
    private static int autoCode = 0;
    
    public ExportReceipt() {
       
    }
    
    public static int getAutoCode(){
        int max = autoCode;
        for( Receipt receipt : ListReceipt.getListReceipt()){
            if(receipt.getCode().contains("EP")){
                int tmp = Integer.parseInt(receipt.getCode().substring(2));
                if(tmp > max){
                    max = tmp;
                }    
            }
        }
        autoCode = max +1;
        return autoCode;
    }
   
}
