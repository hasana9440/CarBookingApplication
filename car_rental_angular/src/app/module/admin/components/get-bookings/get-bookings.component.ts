import { Component } from '@angular/core';
import { AdminService } from '../../service/admin.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-get-bookings',
  templateUrl: './get-bookings.component.html',
  styleUrl: './get-bookings.component.css'
})
export class GetBookingsComponent {
  constructor(private service:AdminService,private message:NzMessageService,private router:Router,private http:HttpClient){

  }
  bookedCars:any;
  ngOnInit(){
    this.getbooking();
  }
  getbooking(){
    this.service.getCarBookings().subscribe((data)=>{
      console.log(data);
      this.bookedCars = data;
    })
  }
  changeBookingStatus(id:number,status:string){
    console.log(id);
    console.log(status);
    this.service.changeBookingStatus(id,status).subscribe((data)=>{
      console.log("after status changed");
      console.log(data);
      this.getbooking();
      this.message.success("Booking Status Changed Successfully !")
    });
  }
}
