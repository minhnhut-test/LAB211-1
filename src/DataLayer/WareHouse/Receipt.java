
package DataLayer.WareHouse;

import java.util.Date;
import DataLayer.Product.Product;
import java.util.ArrayList;

/*
 Code, Time, information product;
 Getter// Setter
 method: printProductsReceipt() => support for toString
         showReceipt() => show information of receipt
*/

public  abstract class Receipt {
    private String code;
    private Date time;
    private ArrayList<Product> products;
    
    
    public Receipt() {
    }
    
    public Receipt(String code, Date time, ArrayList<Product> products) {
        this.code = code;
        this.time = time;
        this.products = products;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public ArrayList<Product> getProduct() {
        return products;
    }
    
    public void setProduct(ArrayList<Product> products) {
        this.products = products;
    }
    
    public void printProductsReceipt(){
        products.forEach((product) -> {
            System.out.print(product.toString());
        });
    }
    public void showReceipt(){
        System.out.println(code +", " + time);
        printProductsReceipt();
    }
}
 