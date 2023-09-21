/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataLayer.ReadStoreData;

import DataLayer.Product.Product;
import DataLayer.WareHouse.Receipt;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import java.util.StringTokenizer;
import DataLayer.Validation.Validation;
/**
 *
 * @author nhutm
 */
public class Data implements IData{
    SimpleDateFormat dateFormat  = new SimpleDateFormat("HH:mm dd/MM/yyyy");
    Validation validation = new Validation();
    @Override
    public void readDataProduct(String url, ArrayList<Product> list) {
       
        try {
            File file = new File(url);
            if(!file.exists()){ //
                file.createNewFile();
            }
            FileReader fr   = new FileReader(file);
            BufferedReader bf = new BufferedReader(fr);
            String details;
            while((details = bf.readLine()) != null){
                //code ; name ; prodate; exp; 10 
                // .....
                StringTokenizer stk = new StringTokenizer(details,";");
                if(stk.countTokens() < 5) return;
                String code = stk.nextToken().toUpperCase().trim();
                String name = stk.nextToken().toUpperCase().trim();
                Date productionDate = validation.executeNullDate(stk.nextToken().toUpperCase().trim());
                Date expDate = validation.executeNullDate(stk.nextToken().toUpperCase().trim());
                int quantity = Integer.parseInt(stk.nextToken().toUpperCase().trim());
                Product product = new Product(code, name, productionDate, expDate, quantity);
                list.add(product);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void storeDataProduct(String url, ArrayList<Product> list) {
        
        //date == null;
          try {
            FileWriter writer = new FileWriter(url);
            for(int i = 0; i < list.size(); i++){
                //list.get(0).getProductionDate() => Date HH:mm dd/MM/yyy // null 
                //format:  Code; Name; productionDate; expDate; quantity
               writer.write(list.get(i).getCode() + "; " + list.get(i).getName() +"; " +validation.executeNullDate(list.get(i).getProductionDate()) + "; " + validation.executeNullDate(list.get(i).getExpDate()) + "; " + list.get(i).getQuantity()+ "\n");
            }
            System.out.println("File saved successfully!");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing the file.");
            e.printStackTrace();
        }
    }
    
    //0000001;09/09/2023   !   000001, NƯỚC MÍA, 02/12/2023, 02/12/2024, 2; 000001, NƯỚC MÍA, 02/12/2023, 02/12/2024, 2; 000001, NƯỚC MÍA, 02/12/2023, 02/12/2024, 2;
    @Override
    public void readDataReceipt(String url, ArrayList<Receipt> list) {
           try{
            File file = new File(url);
            if(!file.exists()){
                file.createNewFile();
            };
            try (Scanner scanner = new Scanner(file)) {
                while(scanner.hasNextLine()){
                    String line = scanner.nextLine();
                    if(line.isEmpty()) return;
                    String[] data = line.split("!");
                    
                    if(data.length < 2) return;
                    //xử lý data từ file vào arraylist                      
                        //Xử lý code import/export 
                        String[] codeAndTime = data[0].split(";");
                        // code import; date ! araylist product 
                        String code = codeAndTime[0].toUpperCase().trim();
                        Date time = dateFormat.parse(codeAndTime[1].toUpperCase().trim());
                        //xử lý arraylist product
                        ArrayList<Product> products = new ArrayList<>();
                        String[] aProduct =  data[1].split(";");
                        for(int i =0; i < aProduct.length; i++){
                            
                            String[] inforProduct = aProduct[i].split(",");
                            String codeProduct = inforProduct[0].toUpperCase().trim();
                            String nameProduct = inforProduct[1].toUpperCase().trim();
                            Date productionDate = validation.executeNullDate(inforProduct[2].toUpperCase().trim());
                            Date expDate = validation.executeNullDate(inforProduct[3].toUpperCase().trim());
                            int quantity = Integer.parseInt(inforProduct[4].toUpperCase().trim());
                            
                            
                            
                            Product product = new Product(codeProduct, nameProduct, productionDate, expDate, quantity);
                            products.add(product);
                        }
                        Receipt receipt = new Receipt(code, time, products) {};
                        list.add(receipt);
                    //
                }
            }
        }catch(Exception e){
             System.out.println(e);
        }
    }

    @Override
    public void storeDataReceipt(String url, ArrayList<Receipt> list) {
              try {
            FileWriter writer = new FileWriter(url);
            
            /// ip0001  araylist product  9 ld0001  /ip 0002 8 /ip 0003 2
            for(int i = 0; i < list.size(); i++){
                //format:  Code; Time; information product
                String time = dateFormat.format(list.get(i).getTime());
                String codeAndTime = list.get(i).getCode() + ";" + time +"!"; //ip00001 ; 12/2/2023 ! ld 0001, name, productdat, expdate, quantity ;
                //
                //ip00001 ; 12/2/2023 ! ld 0001, name, productdat, expdate, quantity ;
                String products = "";
                for(int j =0 ; j < list.get(i).getProduct().size(); j++){
                    products += list.get(i).getProduct().get(j).toString() +";";
                }
                String receipt = codeAndTime + products +"\n";
                writer.write(receipt);
            }
            System.out.println("File saved successfully!");
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

 
    
}
