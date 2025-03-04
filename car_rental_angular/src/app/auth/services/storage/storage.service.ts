import { Injectable } from '@angular/core';
const TOKEN = "token";
const USER = "user";
@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }
  static saveToken(token:string):void{
    window.localStorage.removeItem(TOKEN);
    window.localStorage.setItem(TOKEN,token);
    //console.log("token saved");
  }
  static saveUser(user:any):void{
    window.localStorage.removeItem(USER);
    window.localStorage.setItem(USER,JSON.stringify(user));

    
  }
  // static getUserId() {
  //   let user = this.getUser(); // Get the user object
  //   if (user != null) {
  //     return user.id; // Return the user ID
  //   } else {
  //     return ''; // Return an empty string if no user is found
  //   }
  // }
  static getUserId(): string {
    const userString = this.getUser(); // Get the user as a string
    if (userString) {
      try {
        const user = JSON.parse(userString); // Parse the string into an object
        return user.id; // Return the user ID
      } catch (error) {
        console.error("Error parsing user data:", error);
        return ''; // Return an empty string if parsing fails
      }
    } else {
      return ''; // Return an empty string if no user is found
    }
  }
  
  static getToken(){
    return window.localStorage.getItem(TOKEN);
  }


  static getUser(){
    return window.localStorage.getItem(USER);
  }
 

  static getUserRole(){
    const userString = this.getUser(); // Retrieves user (likely as a string)

    if (!userString) {
      //  console.log("No user found");
        return null; // Or return an empty string "" if needed
    }

    try {
        const user = JSON.parse(userString); // ✅ Convert string to object
        //console.log("get role method:", user.role);
        return user.role; 
    } catch (error) {
        console.error("Error parsing user data:", error);
        return null;
    }
  }
  static isAdminLoggedIn():boolean{
    if(this.getToken == null)return false;
    const role :string = this.getUserRole();
    return role == "ADMIN";
  }
  static isCustomerLoggedIn():boolean{
    if(this.getToken == null)return false;
    const role :string = this.getUserRole();
    return role == "CUSTOMER";
  }
  static logout():void{
    window.localStorage.removeItem(TOKEN);
    window.localStorage.removeItem(USER);
  }
}
