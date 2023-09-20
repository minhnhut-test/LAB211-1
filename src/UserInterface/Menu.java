/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author nhutm
 */
public class Menu extends ArrayList<String> implements IMenu{
    ArrayList<String> list = new ArrayList<String>();
    Scanner sc = new Scanner(System.in);
    
    public Menu(){
        super();
    }
    @Override
    public void addItem(String s) {
        list.add(s);
    }
    static int choice;
    @Override
    public int getChoice(String str) {
       String exit;
       
       for(int i =0 ; i <list.size(); i++){
            System.out.println("\t\t"+(i+1) + ". " +list.get(i));
       }
      
      try{
           System.out.print("\t\tChoose (1-"+list.size()+"): ");
           choice = Integer.parseInt(sc.nextLine());
           if(choice > 0 && choice <= list.size() ){
                return choice;
           }else{
                 System.out.println(str+" ? Y/N");
                   exit = sc.nextLine();
                   /// check dk
                if(exit.equals("Y") || exit.equals("y")){
                  return choice;
                }else{
                throw new Exception();
            } 
           }
    
      }catch (Exception e){
        System.out.println("Your choice is error!");
        getChoice(str);
    }
      return choice;
  } 
    
}
