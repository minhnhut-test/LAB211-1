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

public class ImportReceipt extends Receipt{
    private static int autoCode = 0;

    public ImportReceipt() {
   
    }
    
    
// ip 0000001  
// ip 0000002

//ep 0000001
    

    public static int getAutoCode(){
        int max = autoCode;
       
        for( Receipt receipt : ListReceipt.getListReceipt()){
            if(receipt.getCode().contains("IP")){
                int tmp = Integer.parseInt(receipt.getCode().substring(2));
                if(tmp > max){
                    max = tmp;
                    // max=2;
                }    
            }
        }
         autoCode = max +1; // 2 muc dich
        return autoCode;
      
    }
    
}
