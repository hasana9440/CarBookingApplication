import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { StorageService } from '../../services/storage/storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  user = {
    email: '',
    password: ''
  };
  constructor(private http:HttpClient,private router:Router,private service:AuthService, private message:NzMessageService){

  }
  onSubmit() {
    // Handle form submission
    // This is where you would send the form data to a backend API
    //console.log('Login Submitted!', this.user);
    this.service.login(this.user).subscribe((data)=>{
      console.log(data.userrole+ " has logged in");
      // this.message.success("Logged In successfully");
      if(data.id !=null){
        const user={
          id:data.id,
          role :data.userrole
        }
        
        StorageService.saveUser(user);
        StorageService.saveToken(data.jwt);
        if(StorageService.isAdminLoggedIn()){
          this.router.navigateByUrl("admin/dashboard");
          this.message.success("Admin Logged In successfully");
          //window.location.reload();


        }
        else if(StorageService.isCustomerLoggedIn()){
          this.router.navigateByUrl("customer/dashboard");
          this.message.success("Customer Logged In successfully");
          //window.location.reload();

        }
        else{
          this.message.error("Bad Credentials",{nzDuration: 5000});
        }
        
      }
    });
  }
}
