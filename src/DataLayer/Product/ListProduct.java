/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataLayer.Product;


import java.util.ArrayList;
import DataLayer.ReadStoreData.Data;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import DataLayer.Validation.Validation;
import java.util.Collections;

/**
 *
 * @author nhutm
 */
//CẦN XỬ LÝ TỐI ƯU HÓA CODE TRONG NHẬP DỮ LIỆU
public class ListProduct extends ArrayList implements IListProduct{
    private static  ArrayList<Product> list = new ArrayList<>();
    String url ="D:\\FPT University\\Fall2023\\LAB211\\Lab1\\ConvenienceStore\\src\\DataLayer\\ReadStoreData\\product.dat";
    Scanner sc = new Scanner(System.in);
    SimpleDateFormat dateFormat  = new SimpleDateFormat("HH:mm dd/MM/yyyy");
    Validation validation = new Validation();
    
    
    public ListProduct(){  
        Data readData = new Data();
        readData.readDataProduct(url,list);
    }
    
    public static ArrayList<Product> getListProduct(){
        return list;
    }
  
    public int findCode(String code){
        int result = -1;
        for(int i = 0 ; i < list.size(); i++){
            if(list.get(i).getCode().equals(code)){
                result = i;
                return result;
            }
        }
        return result;
    }
   
    @Override
    public void addProduct() {
        try{
            System.out.println("New product!");
         
            System.out.print("Code product: ");
            String codeProduct = sc.nextLine().trim().toUpperCase();
            if(!validation.checkNoEmpty(codeProduct)) throw new Exception();
            if(findCode(codeProduct) > -1){
                System.out.println("Code is duplicate!");
                return;
            }
            System.out.print("Name product: ");
             
            String nameProduct = sc.nextLine().toUpperCase();  
            nameProduct = validation.removeBlankString(nameProduct);
            
            System.out.print("Production date HH:mm dd/MM/yyyy: ");
            String productionDateS = sc.nextLine();
            productionDateS = validation.inputProductionDate(productionDateS);
            
            System.out.print("Expired date HH:mm dd/MM/yyyy: ");
            String expDateS = sc.nextLine();
            expDateS = validation.inputExpDate(productionDateS,expDateS);
            
            System.out.print("Quantity product : ");
            int quantity = Integer.parseInt(sc.nextLine());
            quantity = validation.inputQuatity(quantity);
            
            Date productionDate = validation.executeNullDate(productionDateS);
            Date expDate = validation.executeNullDate(expDateS);
            Product product = new Product(codeProduct, nameProduct, productionDate, expDate, quantity);
            list.add(product);
        }catch(Exception e){
            System.out.println("Code product not null!");
        }
    }

    @Override
    public void updateProduct() {
        try{
            System.out.println("Update product!");
            System.out.print("Code product: ");
            String codeProduct = sc.nextLine().trim().toUpperCase();
            int index = findCode(codeProduct);
            if(index == -1){
                System.out.println("Product does not exist!");
                return;
            }
            
            System.out.print("Name product: ");
            String nameProduct = sc.nextLine(); 
            if(validation.checkNoEmpty(nameProduct)){
                nameProduct = validation.removeBlankString(nameProduct);
                list.get(index).setName(nameProduct);
            }
            
            System.out.print("Production date HH:mm dd/MM/yyyy: ");
            String productionDateS = sc.nextLine();
            productionDateS = validation.inputProductionDate(productionDateS);
            if(productionDateS != null){
                 Date productionDate = dateFormat.parse(productionDateS);
                 list.get(index).setProductionDate(productionDate);
            }
            
            System.out.print("Expired date HH:mm dd/MM/yyyy: ");
            String expDateS = sc.nextLine();
            expDateS = validation.inputExpDate(productionDateS,expDateS);
            if(expDateS != null){
                 Date expDate = dateFormat.parse(expDateS);
                 list.get(index).setExpDate(expDate);
            }
            
            System.out.print("Quantity product : ");
            String quantityS = sc.nextLine();
            if(validation.checkNoEmpty(quantityS)){
                int quantity = Integer.parseInt(quantityS);
                quantity = validation.inputQuatity(quantity);
                list.get(index).setQuantity(quantity);
            }
            
         
        }catch (Exception e){
            System.out.println("Code product not null!");
        }
    }
  
    
    @Override
    public void deleteProduct() {
       try{
            System.out.println("Delete product!");
        System.out.print("Code product: ");
        String codeProduct = sc.nextLine().trim().toUpperCase();
            
        int index = findCode(codeProduct);
            if(index == -1){
                System.out.println("Product does not exist!");
                return;
        }
        
        System.out.println(list.get(index).toString());
        System.out.println("Do you want to delete it? Y/N");
        if(!("Y".equals(sc.nextLine().trim().toUpperCase()))){
            System.out.println("Delete fail!");
            return;
        }
        System.out.println("Delete success!");
        list.remove(index);
       }catch(Exception e){
           System.out.println("Code product not null!");
       }
    }
    
    
    @Override
    public void showAll() {
        validation.showAHeadTable();
        list.forEach((product) -> {
            String productionDateString = validation.executeNullDate(product.getProductionDate());
            String expDateString = validation.executeNullDate(product.getExpDate());
            System.out.println("|\t  " + product.getCode() + "  \t|\t  " + product.getName() + "          \t\t|\t  " + productionDateString+ "  \t|\t  " + expDateString+ "  \t|\t     "+ product.getQuantity() + "  \t|");
        });
    }

    @Override
    public void productExpired() {
        Date nowDate = new Date();
        System.out.println("Products that have expired !");
        validation.showAHeadTable();
        for(int i =0 ; i < list.size(); i++){
            Date expDate = list.get(i).getExpDate();
            
            
            //ngày sản xuất 12/2/2023   -->  13/9/2023
            // hsd 13/9/2021
            
            //subdate(ngày sản xuất,hsd) < 0
            // hsd - nsx // -number  < 0 
            //if(hsd.getTime - nsx.getTime < 0){ 
            
            //nowDate 17/9/2023
            //sub date 
            if(!(expDate == null)){
                if(validation.subDate(expDate, nowDate) > 0 && list.get(i).getQuantity() > 0){
                validation.showAProduct(i,list);
                
            }
            }
            
        }
       
    }

    @Override
    public void productIsSelling() {
        Date nowDate = new Date();
        System.out.println("Products that the store is selling !");
        validation.showAHeadTable();
        for(int i =0 ; i < list.size(); i++){
            Date expDate = list.get(i).getExpDate();
            if(!(expDate == null)){
                if(validation.subDate(list.get(i).getExpDate(), nowDate) < 0){
                validation.showAProduct(i,list);
                }         
            }
            
        }
    }

    @Override
    public void productOutOfStock() {
        System.out.println("Products that are running out of stock !");
        validation.showAHeadTable();
        ArrayList<Product> products = new ArrayList<>();
        for(int i =0 ; i < list.size(); i++){
            if(list.get(i).getQuantity() <= 3){
                products.add(list.get(i));
            }
        }
        // u r i 
        Collections.sort(products,(Product pro1 , Product pro2) -> pro1.getQuantity() - pro2.getQuantity());
      
        for(int j =0; j < products.size(); j++){
           validation.showAProduct(j,products);
       }
    }

    public void storeDataProduct(){
        Data write = new Data();
        write.storeDataProduct(url, list);
    }
        
}
