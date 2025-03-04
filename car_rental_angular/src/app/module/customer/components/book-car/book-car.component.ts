import { Component } from '@angular/core';
import { CustomerService } from '../../service/customer.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StorageService } from '../../../../auth/services/storage/storage.service';

@Component({
  selector: 'app-book-car',
  templateUrl: './book-car.component.html',
  styleUrl: './book-car.component.css'
})
export class BookCarComponent {
  id:any;
  car:any;
  processedImage:any;
  validateForm!:FormGroup;
  //dataFormate:"DD-MM-YYY";
  constructor(private fb:FormBuilder,private service:CustomerService,private message:NzMessageService,private router :Router,private activatedRoute:ActivatedRoute){

  }
 
  ngOnInit(){
    this.id= this.activatedRoute.snapshot.params['id'];
    this.getCarById();
    this.validateForm = this.fb.group({
      toDate :[null,Validators.required],
      fromDate :[null,Validators.required],
    })
  }
  getCarById(){
    this.service.getCarById(this.id).subscribe((data)=>{
      console.log(data);
      this.processedImage = 'data:image/jpeg;base64,' + data.returenedFile;
      this.car = data;
      console.log(this.car)
    })
  }
  bookCar(data: any) {
    console.log(data);
  
    // Create the car booking DTO
    let carDto = {
      toDate: data.toDate,
      fromDate: data.fromDate,
      userId: StorageService.getUserId(),
      carId: this.id

    };
    console.log(carDto)
    // Call the service to book the car
    this.service.bookACar(carDto).subscribe((res)=>{
      console.log(res);
      this.router.navigateByUrl("/customer/dashboard");
    })
    
  }
}
