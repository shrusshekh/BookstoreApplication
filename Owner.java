/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

/**
 *
 * @author sshekhad
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
class Owner extends User {
   private static Owner instance = null;
   private Owner(String username, String password) {
       super(username, password);
   }

   public static Owner getInstance() {
       if (instance == null) {
           instance = new Owner("admin", "admin"); //Initialize admin user here.
       }
       return instance;
   }

}
