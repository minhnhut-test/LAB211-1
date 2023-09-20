/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataLayer.Product;

import DataLayer.Validation.Validation;
import java.util.Date;

/**
 *
 * @author nhutm
 */
/*
    Code,Name, productionDate, expDate, quantity
*/

public class Product {
    private String code;
    private String name;
    private Date productionDate;
    private Date expDate;
    private int quantity;
    
    Validation validation = new Validation();
    public Product(){};
    
    public Product(String code, String name, Date productionDate, Date expDate, int quantity) {
        this.code = code;
        this.name = name;
        this.productionDate = productionDate;
        this.expDate = expDate;
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        String productionDateString = validation.executeNullDate(getProductionDate());
        String expDateString = validation.executeNullDate(getExpDate());
        return code + ", " + name +", " + productionDateString +", " + expDateString + ", " + quantity ;
    }


    
}
