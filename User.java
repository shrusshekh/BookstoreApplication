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

public abstract class User {

   protected String username;
   protected String password;

   public User (String username, String password) {

      this.username = username;
      this.password = password;
   }

   public String getUsername()
   {
   return username;
   }

   public String getPassword()
   {
   return password;
   }

   public void autheticate(String username, String password)
   {
      //if the password and username is admin then knows its the owner
      if (username.equals("admin") && password.equals("admin"))
      {
         Owner.getInstance(); //Use Singleton instance
      }
         //if the password and username are right, but not admin, then it is a customer
      else if (username.equals(this.username) && password.equals(this.password))
      {
         new Customer(username, password); //creating a new customer
      }
   }


}