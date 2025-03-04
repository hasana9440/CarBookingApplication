import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { CustomerService } from '../../service/customer.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-my-bookings',
  templateUrl: './my-bookings.component.html',
  styleUrl: './my-bookings.component.css'
})
export class MyBookingsComponent {
  constructor(private message:NzMessageService,private router:Router,private service:CustomerService){
     
  }
  bookedCars!:any;
  getMyBookings(){
    console.log("***********")
    console.log(this.service.getBookingsById());
    console.log("***********")
    this.service.getBookingsById().subscribe((data)=>{
      
      console.log(data);
      this.bookedCars = data;
    })
  }
  ngOnInit(){
    //console.log(this.getMyBookings())
   this.bookedCars = this.getMyBookings();
  }

}
