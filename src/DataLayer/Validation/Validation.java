/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataLayer.Validation;

import DataLayer.Product.ListProduct;
import DataLayer.Product.Product;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nhutm
 */
public class Validation implements IValidation{
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy"); //00:00 12/2/2023
    Scanner sc = new Scanner(System.in);
    @Override
    public boolean checkDateValid(String date) {
        //00:00  12/2/2023
       String[] data = date.split(" ");
       String[] dayMonthYear = data[1].split("/");
       int day = Integer.parseInt(dayMonthYear[0]);
       int month = Integer.parseInt(dayMonthYear[1]);
       int year = Integer.parseInt(dayMonthYear[2]);
       boolean result = false;
       if(dayMonthYear[0].length() > 2 || dayMonthYear[1].length() > 2 ||  year < 1900) return false;
       if((year % 4 == 0 && year % 100 != 0 )||  year % 400 == 0){
           if(month >= 1 && month <= 12){
               if(month == 2){
                   if(day >= 1 && day <= 29){
                       result = true;
                   }
               }else if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 ||month == 10 || month == 12){
                   if(day >= 1 && day <= 31) result = true;
               }else{
                   if(day >=1 && day <= 30) result = true;
               }
           }
       }else{
           if(month >= 1 && month <= 12){
               if(month == 2){
                   if(day >= 1 && day <= 28){
                       result = true;
                   }
               }else if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 ||month == 10 || month == 12){
                   if(day >= 1 && day <= 31) result = true;
               }else{
                   if(day >=1 && day <= 30) result = true;
               }
           }
       }
       return result;
    }

    @Override
    public long subDate(Date date1, Date date2) {
        //ngày b :1/1/2021 = 10000
        //daily product 7:00 17/9/2023 -> 15h 17/9/2023
        //ngày a : 17/9/2023 == number > 10000
        long dayNumber = date2.getTime() - date1.getTime();
        // 1000*60*60
        //86000000 milisecond //
        // 1ngay =24h = 24*(60*60*1000)    /(60*60*1000)
        return dayNumber/(1000*60*60); // giờ
    }
    
    @Override
    //true la tra ve mang kh empty 
    //str ="       ass " str.isEmpty() => false
    public boolean checkNoEmpty(String str){
        if(!str.isEmpty() && !str.replaceAll(" ","").isEmpty() && !str.replaceAll("\t","").isEmpty()){
            return true;
        }
        return false;
    }
    
    @Override
    public String removeBlankString(String str) {
        String result = " ";
        String[] data = str.trim().split("\\s+");
        for(int i  =0 ; i < data.length; i++){
            if(!data[i].isEmpty()){
                result += data[i] +" ";
            }
        }

        return result.trim();
    }

    @Override
    public int inputQuatity(int quantity ) {
    
       
        while(quantity <= 0){
             System.out.println("Quantity must not negative number!");
             System.out.print("Enter quantity: ");
             quantity = Integer.parseInt(sc.nextLine());
        }
     
       return quantity;
    }
    
    
    
    //mở rộng method cho người dùng nhập vào 2 giá trị của date cho phép người dùng nhập vào 12/2/2023 hoặc 00:00 12/2/2023
    // khi nhập 12/2/2023 sẽ auto nhập vào 00:00 cho giá trị giờ
    
    //00:00  12/2/2023   // 12/2/2023 
    // product A 12/2/2023 B 13/2/2023  // daily --> 6:00 12/2/2023 -> 
    public String executeInputStringDate(String str){
        //    12/2/2023
        String result = "";
        if(str.contains(":")){
            return str;
        }else{
            result = "00:00 " + str;  // 00:00 12/2/2023
        }
        return result;
        /*
        hàm giúp thoát lỗi trong array của checkvaliddate
        khi phân tách dữ liệu String
        */
    } 
    
    
    
    
    
    @Override
    // ""
    public String inputProductionDate(String productionDateS) {
       if(!checkNoEmpty(productionDateS)){
           return null;
       }
       
       productionDateS= executeInputStringDate(productionDateS);
       //productionDateS 00:00 12/2/2023
       while(!checkDateValid(productionDateS)){
           System.out.println("Date is not valid!");
           System.out.print("Enter production date: ");
           productionDateS = sc.nextLine();
           if(!checkNoEmpty(productionDateS)){
                return null;
           }
           productionDateS = executeInputStringDate(productionDateS);
       }
       return productionDateS; //00:00 1/2/2024
    }

    @Override
    public String inputExpDate(String Date, String expDate) {
        
        if(!checkNoEmpty(expDate)){
           return null;
        }
        expDate= executeInputStringDate(expDate);
        if(Date == null){
            Date = "00:00 00/0/0000";
        }
        try {
            while(!checkDateValid(expDate) || subDate(dateFormat.parse(Date), dateFormat.parse(expDate)) <= 0){          
                if(!checkDateValid(expDate)){
                     System.out.println("Date is not valid!");
                }else {
                    System.out.println("Expired date smaller than production date!");
                }
                System.out.print("Enter expriration date: ");
                expDate = sc.nextLine();
                if(!checkNoEmpty(expDate)){
                    return null;
                }
                expDate= executeInputStringDate(expDate);
                
            }
        } catch (ParseException ex) {
            Logger.getLogger(Validation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return expDate;
    }
    public String inputExpDateReciept(String Date,String expDate) {
        if(!checkNoEmpty(expDate)){
           return null;
        }
        expDate= executeInputStringDate(expDate);
        if(Date == null){
            Date = "00:00 00/0/0000";
        }
        try {
            while(!checkDateValid(expDate) || subDate(dateFormat.parse(Date), dateFormat.parse(expDate)) <= 0 || subDate(new Date(), dateFormat.parse(expDate)) <= 0){          
                if(!checkDateValid(expDate)){
                     System.out.println("Date is not valid!");
                }else if(subDate(dateFormat.parse(Date), dateFormat.parse(expDate)) <= 0){
                    System.out.println("Expired date smaller than production date!");
                }
                else {
                    System.out.println("The product have expried date!");
                }
                System.out.print("Enter expriration date: ");
                expDate = sc.nextLine();
                if(!checkNoEmpty(expDate)){
                    return null;
                }
                expDate= executeInputStringDate(expDate);
                
            }
        } catch (ParseException ex) {
            Logger.getLogger(Validation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return expDate;
    }
    //method dùng để xử lý ngày null khi lấy từ dữ liệu
    // Date = null;
    public String executeNullDate(Date dateInput){
        String dateOutput ="";
        if(dateInput == null ){
                dateOutput ="\tN/A\t";
            }else{
                dateOutput = dateFormat.format(dateInput);
         }
        return dateOutput;
    }
    
    public Date executeNullDate(String dateInput) throws ParseException{
        Date dateOutput;
        
        if(dateInput == null || dateInput.equals("N/A")){
                dateOutput = null;
        }else{
            
                dateOutput = dateFormat.parse(dateInput);
        }
        return dateOutput;
    }
    
    public void showAHeadTable(){
        String headTable = "|\t  " + "Code" + "  \t|\t  " + "Name" + "          \t\t|\t  " + "Production Date" + "  \t|\t  " + "Expiration Date"+ "  \t|\t  "+ "Quantity" + "  \t|";
        System.out.println(headTable);
        for(int i =0 ; i < headTable.length()+62; i++) System.out.print("-");
        System.out.println("");
    }
    public void showAProduct(int index, ArrayList<Product> products){
        String productionDateString = executeNullDate(products.get(index).getProductionDate());
        String expDateString = executeNullDate(products.get(index).getExpDate());
        System.out.println("|\t  " + products.get(index).getCode() + "  \t|\t  " + products.get(index).getName() + "          \t\t|\t  " + productionDateString+ "  \t|\t  " + expDateString+ "  \t|\t     "+ products.get(index).getQuantity() + "  \t|");
    }
    public void showAProduct(String code,ArrayList<Product> list){
        
        for(Product product : list){
            
            if(product.getCode().equals(code.toUpperCase())){
               
                 String productionDateString = executeNullDate(product.getProductionDate());
                 String expDateString = executeNullDate(product.getExpDate());
                 System.out.println("|\t  " + product.getCode() + "  \t|\t  " + product.getName()+"  " + "          \t\t|\t  " + productionDateString+ "  \t|\t  " + expDateString+ "  \t|\t     "+ product.getQuantity() + "  \t|");
            }
        } 
    }
    public int findCode(String code,ArrayList<Product> list){
        int result = -1;
        for(int i = 0 ; i <  list.size(); i++){
            if( list.get(i).getCode().equals(code)){
                result = i;
                return result;
            }
        }
        return result;
    }

    /// xử lý đồng bộ dữ liệu bên ListProduct
    public  ArrayList<Product> executeProductInReciept(Product product, ArrayList<Product> productTemp){
            //tạo ra biến gán để thực hiện tạo một đối tượng mới ==> fix lỗi tự cộng dồn của receipt khi kh có dữ liệu fix cứng
            int quantityProduct =product.getQuantity(); //11 
            
            int index = findCode(product.getCode(), ListProduct.getListProduct()); 
            if(index >=0 ){
                //
                int quantity = ListProduct.getListProduct().get(index).getQuantity() + quantityProduct; //21
                ListProduct.getListProduct().get(index).setQuantity(quantity);
               
                // 14/2/2023   13/10/2023  /// enter
                if(!(product.getProductionDate() == null)){
                   ListProduct.getListProduct().get(index).setProductionDate(product.getProductionDate());
                }
                if(!(product.getExpDate() == null)){
                    ListProduct.getListProduct().get(index).setExpDate(product.getExpDate());
                }
                ///
            }else{
                 ListProduct.getListProduct().add(product);
            }
       //  
       Product product1 = new Product(product.getCode(),product.getName(),product.getProductionDate(),product.getExpDate(), quantityProduct);
       if(productTemp.add(product1)){
           System.out.println("Add product success to receipt!");
       }
       // object product1, object product2
       return productTemp;
    }
}
