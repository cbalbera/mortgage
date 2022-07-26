import { Component, OnInit } from '@angular/core';
import { mortgageService } from './mortgage.service';
import { NgForm } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { environment } from 'src/environments/environment';

@Component({ 
    selector: 'home',
    templateUrl: './home.component.html',
    styleUrls: ['../styles.css'],
    //template: ` <button type="button" (click)="submitData()">Click me!</button> {{clickMessage}}`
})
export class HomeComponent implements OnInit {

    private apiServerURL = environment.apiBaseUrl;

    constructor(private mortgageService: mortgageService, private route: ActivatedRoute, private router: Router) {}

    ngOnInit() {
        
    }

    onSubmit(form: NgForm) {
        // push data to backend for upload to database
        let id_value = Math.floor(Math.random() * Date.now())
        form.controls['id'].setValue(id_value.toString());
        this.mortgageService.addMortgage(form.value);

        // route to mortgage output display
        this.router.navigate(['/show'],{queryParams:{id:id_value}});
    }
    
    //log(item: any) {console.log(item); } // for viewing ngModel class data in browser console

}

/*
export interface http_putpost_mortgage{

    id: number;
    principal: number;
    down_pmt: number;
    annual_rate: number;
    amort_years: number;
    loan_term: number;    

}
*/