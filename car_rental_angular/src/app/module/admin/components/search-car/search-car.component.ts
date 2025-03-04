import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AdminService } from '../../service/admin.service';

@Component({
  selector: 'app-search-car',
  templateUrl: './search-car.component.html',
  styleUrl: './search-car.component.css'
})
export class SearchCarComponent {
  validateForm!:FormGroup;
  listOfBrands: string[] = ['Toyota', 'Honda', 'Ford', 'BMW', 'Mercedes','Suzuki'];
  listOfTransmission: string[] = ['Manual', 'Automatic'];
  listOfColor: string[] = ['Red', 'Blue', 'Black', 'White', 'Gray'];
  listOfType: string[] = ['Sedan', 'SUV', 'Truck', 'Coupe'];

  constructor(private fb:FormBuilder,private router:Router,private service:AdminService){
    this.validateForm = this.fb.group({
      brand:[null],
      type:[null],
      color:[null],
      transmission:[null],
    })
  }
  cars: any[] = [];

  searchCar() {
    console.log("Search Criteria:", this.validateForm.value);
  
    this.service.searchCar(this.validateForm.value).subscribe(
      (data) => {
        console.log("API Response Type:", typeof data, "Data:", data);
        
        // Ensure data is not null and is an array
        if (data && Array.isArray(data)) {
          this.cars = data.map((element) => {
            console.log("Processing Car:", element);
  
            return {
              ...element,
              processedImg: element.returnedFile  // Fixed Typo Here
                ? `data:image/jpeg;base64,${element.returnedFile}`  // Fixed Typo Here
                : 'assets/default-car.jpg'
            };
          });
  
          console.log("Processed Cars:", this.cars);
        } else {
          console.error("Error: API did not return an array", data);
          this.cars = []; // Reset cars array in case of error
        }
      },
      (error) => {
        console.error("API Error:", error);
      }
    );
  }
  
}
