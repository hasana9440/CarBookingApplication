import { Component } from '@angular/core';
import { StorageService } from './auth/services/storage/storage.service';
import { Router } from '@angular/router';
import { NzMessageComponent, NzMessageModule, NzMessageService } from 'ng-zorro-antd/message';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'car_rental_angular';
  isCustomerLoggedIn:boolean = StorageService.isCustomerLoggedIn();
  isAdminLoggedIn:boolean=StorageService.isAdminLoggedIn();
  constructor(private router:Router,private message:NzMessageService){

  }
  ngOnInit()
 {
    this.router.events.subscribe(event=>{
      if(event.constructor.name === "NavigationEnd"){
        this.isAdminLoggedIn = StorageService.isAdminLoggedIn();
        this.isCustomerLoggedIn = StorageService.isCustomerLoggedIn();
      }
    })
  }
  logout(){
    StorageService.logout();
    this.router.navigateByUrl("/login");
    this.message.success("logged Out successfully")
  }
    // window.location.reload();

   
    
}
