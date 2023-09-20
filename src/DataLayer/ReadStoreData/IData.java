/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataLayer.ReadStoreData;

import DataLayer.Product.Product;
import DataLayer.WareHouse.Receipt;
import java.util.ArrayList;

/**
 *
 * @author nhutm
 */
public interface IData {
    public void readDataProduct(String url, ArrayList<Product> list);
    public void storeDataProduct(String url,ArrayList<Product> list);
    public void readDataReceipt(String url, ArrayList<Receipt> list);
    public void storeDataReceipt(String url,ArrayList<Receipt> list);
}
