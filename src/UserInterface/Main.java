/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import DataLayer.Product.ListProduct;
import DataLayer.WareHouse.ListReceipt;


/**
 *
 * @author nhutm
 */





public class Main {
    
    /**
     * @param args the command line arguments
     */
   
    public static void main(String[] args){
        IMenu menu = new Menu();
        menu.addItem("Manage products");
        menu.addItem("Manage Warehouse");
        menu.addItem("Report");
        menu.addItem("Store data to files");
        menu.addItem("Close the application");
        int choice;
        ListProduct listProduct = new ListProduct(); 
        ListReceipt listReceipt = new ListReceipt();
        do{
            System.out.println("---------------------------------------------------------------");
            System.out.println("                  Convenience Store".toUpperCase());
            System.out.println("---------------------------------------------------------------");
            choice = menu.getChoice("You want to close application");
            switch(choice){
                case 1:
                    System.out.println("---------------------------------------------------------------");
                    System.out.println("                  Manage products".toUpperCase());
                    System.out.println("---------------------------------------------------------------");

                    IMenu manaProduct = new Menu();
                    manaProduct.addItem("Add a product");
                    manaProduct.addItem("Update product information");
                    manaProduct.addItem("Delete product");
                    manaProduct.addItem("Show all product");
                    manaProduct.addItem("Other. Go back to main menu!");
                    int choiceManaProduct;
                    do{
                        choiceManaProduct = manaProduct.getChoice("You want to go back main menu");
                        System.out.println("---------------------------------------------------------------");
                        switch(choiceManaProduct){
                            case 1:
                                listProduct.addProduct();
                                break;
                            case 2:
                                listProduct.updateProduct();
                                break;
                            case 3:
                                listProduct.deleteProduct();
                                break;
                            case 4:
                                listProduct.showAll();
                                break;
                        }
                    }while(choiceManaProduct >= 1 && choiceManaProduct <= 4);
                    break;
                case 2:
                    System.out.println("---------------------------------------------------------------");
                    System.out.println("                  Manage Warehouse".toUpperCase());
                    System.out.println("---------------------------------------------------------------");

                    IMenu manaWareHouse = new Menu();
                    manaWareHouse.addItem("Create an import receipt");
                    manaWareHouse.addItem("Create an export receipt");
                    manaWareHouse.addItem("Other. Go back to main menu!");
                    int choiceWare;
                    do{
                         choiceWare = manaWareHouse.getChoice("You want to go back main menu");
                         switch(choiceWare){
                             case 1:
                                 listReceipt.createImportReceipt();
                                 break;
                             case 2:
                                 listReceipt.createExportReceipt();
                                 break;
                         }
                     }while(choiceWare >= 1 && choiceWare <= 2);
                    break;
                case 3:
                  
                    System.out.println("---------------------------------------------------------------");
                    System.out.println("                  Report".toUpperCase());
                    System.out.println("---------------------------------------------------------------");

                    IMenu report = new Menu();
                    report.addItem("Products that have expired");
                    report.addItem("The products that the store is selling");
                    report.addItem("Products that are running out of stock (sorted in ascending order)");
                    report.addItem("Import/export receipt of a product");
                    report.addItem("Other. Go back to main menu!");
                    int choiceReport;
                    do{
                        choiceReport = report.getChoice("You want to go back main menu");
                        switch(choiceReport){
                            case 1:
                                listProduct.productExpired();
                                break;
                            case 2:
                                listProduct.productIsSelling();
                                break;
                            case 3:
                                listProduct.productOutOfStock();
                                break;
                            case 4:
                                listReceipt.impExpReceipt();
                                break;
                        }

                    }while(choiceReport>=1 && choiceReport <= 4);

                    
                    break;
                case 4:
                    System.out.println("---------------------------------------------------------------");
                    System.out.println("                  Store data to files".toUpperCase());
                    listProduct.storeDataProduct();
                    listReceipt.store();
                    System.out.println("---------------------------------------------------------------");
                    break;
                case 5:
                     System.out.println("---------------------------------------------------------------");
                     System.out.println("                  Application is closing ...".toUpperCase());
                     System.out.println("---------------------------------------------------------------");
                    choice = 6;
                    break;
            }
        }while(choice >=1 && choice <=5 );
       
    }
   
  
}
