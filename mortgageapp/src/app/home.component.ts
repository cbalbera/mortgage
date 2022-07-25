import { Component, OnInit } from '@angular/core';
import { mortgageService } from './mortgage.service';
import { NgForm } from '@angular/forms';

@Component({ 
    selector: 'home',
    templateUrl: './home.component.html',
    styleUrls: ['../styles.css'],
    //template: ` <button type="button" (click)="submitData()">Click me!</button> {{clickMessage}}`
})
export class HomeComponent implements OnInit {

    constructor(private mortgageService: mortgageService) {}

    ngOnInit() {
        
    }

    onSubmit(form: NgForm) {
        //console.log(form.value);
        var id_value = Math.floor(Math.random() * Date.now())
        form.controls['id'].setValue(id_value);
        this.mortgageService.addMortgage(form.value);
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