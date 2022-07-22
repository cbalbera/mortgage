import { Component, OnInit } from '@angular/core';

@Component({ 
    selector: 'home',
    templateUrl: './home.component.html',
    styleUrls: ['../styles.css'],
    //template: ` <button type="button" (click)="submitData()">Click me!</button> {{clickMessage}}`
})
export class HomeComponent implements OnInit {
    public id!: number;
    public principal!: number;
    public down_pmt!: number;
    public annual_rate!: number;
    public amort_years!: number;
    public loan_term!: number;

    constructor() {}

    ngOnInit() {
        //this.getMortgages();
      }

    /*
    public submitData(event: click): void {

    }
    */
}
