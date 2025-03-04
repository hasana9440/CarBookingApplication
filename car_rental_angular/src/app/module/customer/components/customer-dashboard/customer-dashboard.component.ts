import { Component } from '@angular/core';
import { CustomerService } from '../../service/customer.service';

@Component({
  selector: 'app-customer-dashboard',
  templateUrl: './customer-dashboard.component.html',
  styleUrl: './customer-dashboard.component.css'
})
export class CustomerDashboardComponent {
  constructor(private service:CustomerService){

  }
  
  cars:any = [];
  ngOnInit(){
    this.getAllCars();
   // console.log(this.getAllCars());
  }
  getAllCars() {
    this.service.getAllCars().subscribe(
      (data) => {
        //console.log("Received Data:", data); // Debugging
  
        if (Array.isArray(data)) {
          this.cars = data.map((element) => {
            //console.log("Returned Image Data:", element.returenedFile); // Check if image exists
            return {
              ...element,
              processedImg: element.returenedFile 
                ? `data:image/jpeg;base64,${element.returenedFile}` 
                : 'assets/default-car.jpg' // Use default if no image
            };
          });
        } else {
          console.error("Error: API did not return an array", data);
        }
      },
      (error) => {
        console.error("API Error:", error);
      }
    );
  }
}
