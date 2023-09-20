/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataLayer.WareHouse;

import DataLayer.Product.ListProduct;
import DataLayer.Product.Product;
import java.util.ArrayList;
import DataLayer.ReadStoreData.Data;
import DataLayer.Validation.Validation;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author nhutm
 */
public class ListReceipt extends ArrayList implements IListReceipt{
    String url = "D:\\FPT University\\Fall2023\\LAB211\\Lab1\\ConvenienceStore\\src\\DataLayer\\ReadStoreData\\wareHouse.dat";
    SimpleDateFormat dateFormat  = new SimpleDateFormat("HH:mm dd/MM/yyyy");
    Scanner sc = new Scanner(System.in);
    Validation validation = new Validation();
    private static  ArrayList<Receipt> listReceipt =  new ArrayList<>();
    public ListReceipt(){
        Data read = new Data();
        read.readDataReceipt(url, listReceipt); 
    }

    public static ArrayList<Receipt> getListReceipt() {
        return listReceipt;
    }
    
    // 1 2 3 4 5 5 6
    // 1000000
    //Ip 0000010
    public String autoCode(String prefix, int number) {
        String result = prefix;
        String formatNumber = String.format("%07d",number);
        result += formatNumber;
        return result;
    }
    
    @Override
    public void createImportReceipt() {
        // code , time || những cái product người dùng nhập vao
        //list product to add receipt
        ArrayList<Product> productTemp = new ArrayList<>();
        Receipt receipt = new ImportReceipt();
        //ImportReceipt.getAutoCode() = 1 
        String code = autoCode("IP",ImportReceipt.getAutoCode());
        Date nowDate = new Date();
        //set auto code and time
        receipt.setCode(code);
        receipt.setTime(nowDate);
        //add product
        int i= 0;
        System.out.println("Products import!");
        try{
        do{
            System.out.print("Code product: ");
            String codeProduct = sc.nextLine().trim().toUpperCase();
            if(!validation.checkNoEmpty(codeProduct)){
                throw new Exception(); 
            };
            String nameProduct;
            int index =validation.findCode(codeProduct, ListProduct.getListProduct());
            if(!(index >= 0)){
                System.out.print("Name product: ");
                nameProduct = sc.nextLine().toUpperCase();
                nameProduct = validation.removeBlankString(nameProduct);
            }else{
                nameProduct =ListProduct.getListProduct().get(index).getName();
            }
            
            
            
            System.out.print("Production date HH:mm dd/MM/yyyy: ");
            String productionDateS = sc.nextLine();
            productionDateS = validation.inputProductionDate(productionDateS);
           
            System.out.print("Expired date HH:mm dd/MM/yyyy: ");
            String expDateS = sc.nextLine();
            expDateS = validation.inputExpDateReciept(productionDateS,expDateS);
            
            System.out.print("Quantity product : ");
            int quantity = Integer.parseInt(sc.nextLine());
            quantity = validation.inputQuatity(quantity);
            Date productionDate = validation.executeNullDate(productionDateS);
            Date expDate = validation.executeNullDate(expDateS);
            
            // check object 
            
            Product productInReceipt = new Product(codeProduct, nameProduct, productionDate, expDate, quantity);
            // add vào products (mảng tạm chứa product lưu vào receipt)
            //dùng callback để fix lỗi----
            productTemp = validation.executeProductInReciept(productInReceipt, productTemp);
            
            System.out.println("1.End the import receipt !");
            System.out.println("Other. Add more product !");
            System.out.print("Chose (1-other): ");
            String numberI = sc.nextLine();
            if(validation.removeBlankString(numberI).equals("1")){
                i = Integer.parseInt(numberI);
            }else{
                i = 0;
            }
            
        }while(!(i == 1));
            receipt.setProduct(productTemp);        
        }catch (Exception e){
            System.out.println("Code product is not null!");
            receipt.setProduct(productTemp);
        }finally{
             listReceipt.add(receipt);
        }
        
        
    }
    


    //support for create export
    //
    public int inputQuantityReciept(int index, int quantity){
        
       while(quantity <= 0 || ListProduct.getListProduct().get(index).getQuantity() - quantity < 0){
           if(quantity <= 0){
            System.out.println("Quantity must not negative number!");
            System.out.println("Enter quantity: ");
            quantity = Integer.parseInt(sc.nextLine());
           }else if(ListProduct.getListProduct().get(index).getQuantity() - quantity < 0){
               System.out.println("Current quantity: "+ ListProduct.getListProduct().get(index).getQuantity());
               System.out.println("Product quantity is not enough! ");
               System.out.println("Enter quantity: ");
               quantity = Integer.parseInt(sc.nextLine());
           }
       }
       ListProduct.getListProduct().get(index).setQuantity(ListProduct.getListProduct().get(index).getQuantity()-quantity);
       return quantity;
    }
    public void printLine(){
        for(int i =0 ; i < 82+71; i++) System.out.print("-");
        System.out.println("");
    }
    @Override
    public void createExportReceipt() {
        
        //list product to add receipt
        ArrayList<Product> products = new ArrayList<>();
        Receipt receipt = new ExportReceipt();
        String code = autoCode("EP",ExportReceipt.getAutoCode());
        Date nowDate = new Date();
            //set auto code and time
        receipt.setCode(code);
        receipt.setTime(nowDate);
        int i= 0;
        System.out.println("Products export!");
        try{
            do{
                System.out.print("Code product: ");
                String codeProduct = sc.nextLine().trim().toUpperCase();
                if(!validation.checkNoEmpty(codeProduct)){
                    throw new Exception(); 
                };
                int index = validation.findCode(codeProduct,ListProduct.getListProduct());
                if( index > -1){
                    validation.showAProduct(index, ListProduct.getListProduct());
                    System.out.println("Quantity product");
                    String quantityS = sc.nextLine();
                    if(!validation.checkNoEmpty(quantityS)){
                        System.out.println("Quanity must not null!");
                        return;
                    }
                    int quantity = Integer.parseInt(quantityS);
                    quantity = inputQuantityReciept(index,quantity);
                    System.out.println(quantity);
                    Product productInReceipt = new Product(codeProduct,  ListProduct.getListProduct().get(index).getName(), ListProduct.getListProduct().get(index).getProductionDate(), ListProduct.getListProduct().get(index).getExpDate(), quantity);
                    products.add(productInReceipt);
                }else{
                    System.out.println("Product does not exist!");
                }
                printLine();
                System.out.println("1.End the export receipt !");
                System.out.println("Other. Add more product !");
                System.out.print("Chose (1-other): ");
                String numberI = sc.nextLine();
                printLine();
                if(validation.removeBlankString(numberI).equals("1")){
                    i = Integer.parseInt(numberI);
                }else{
                    i = 0;
                }
            }while(i != 1);
            
            receipt.setProduct(products);
            listReceipt.add(receipt);
         }catch(Exception e){
             System.out.println(e);
         }
    }
    
    public ArrayList<Receipt> searchProductInReceipt(String code){
        ArrayList<Receipt> receipts = new ArrayList<>();
        for(int i  = 0; i < listReceipt.size(); i++){
            int index = validation.findCode(code,listReceipt.get(i).getProduct());
            if(index > -1){
                receipts.add(listReceipt.get(i));
            }
        }
        return receipts;
    }
    
   
    public void showReceipt(ArrayList<Receipt> receipts,String code){
        for(int i = 0; i < receipts.size(); i++){
            printLine();
            System.out.println("\t\t\t\t\t\t Import/export receipt of a product".toUpperCase());
            printLine();
            String time = validation.executeNullDate(receipts.get(i).getTime());
            System.out.println("Receipt code: "+receipts.get(i).getCode() +"\t\t\t\t" + "Reciept time: "+ time);
            printLine();
            validation.showAHeadTable();
            validation.showAProduct(code,receipts.get(i).getProduct());
            
        }
    }
    @Override
    public void impExpReceipt() {
       System.out.println("Import/export receipt of a product!");
       System.out.print("Code product: ");
       String codeProduct = sc.nextLine().trim().toUpperCase();
       int index = validation.findCode(codeProduct,ListProduct.getListProduct());
       if(index >-1){
           ArrayList<Receipt> list = new ArrayList<>();
        
           list = searchProductInReceipt(codeProduct);
           showReceipt(list,codeProduct);
       }else{
           System.out.println("Product does not exist!");
       }
    }
    
    
    public void store(){
        Data read = new Data();
        read.storeDataReceipt(url, listReceipt);
    }

    
}
