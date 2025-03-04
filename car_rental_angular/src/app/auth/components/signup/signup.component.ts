import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {
 
  constructor(private service:AuthService,
    private message:NzMessageService,
    private router:Router,
  ){}
  // isSpinning:boolean =false;
  // signupform!:FormGroup;
  // constructor(private fb:FormBuilder){

  // }
  // ngOnInit(){
  //   this.signupform = this.fb.group({
  //     name:[null,[Validators.required]],
  //     email:[null,[Validators.required,Validators.email]],
  //     password:[null,[Validators.required]],
  //     cpassword:[null,[Validators.required]],
  //   })
  // }
  // passwordPattern = '^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};:\'",<>,./?]).{8,}$';
  // register(){
  //   console.log(this.signupform.value)
  // }
  user = {
    name: '',
    email: '',
    password: '',
    cpassword: ''  // Confirm password field
  };

  //passwordPattern = '^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};:\'",<>,./?]).{8,}$';
  passwordPattern = '^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};:\'",<>,./?]).{8,}$';


  onSubmit() {
    if (this.user.password !== this.user.cpassword) {
      console.log('Passwords do not match!');
      return;
    }

    // Handle form submission
    console.log(this.user);
    this.service.register(this.user).subscribe((res)=>{
      console.log(res);
      if(res.id !=null){
        this.message.success("signed Up successfully");
        this.router.navigateByUrl("/login")
        
      }
      else{
        this.message.error("sigup failed");
      }
    });
  }
}
