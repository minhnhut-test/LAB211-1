/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataLayer.Product;
/**
 *
 * @author nhutm
 */
public interface IListProduct {
    public void addProduct();
    public void updateProduct();
    public void deleteProduct();
    public void showAll();
    public void productExpired();
    public void productIsSelling();
    public void productOutOfStock();
}
