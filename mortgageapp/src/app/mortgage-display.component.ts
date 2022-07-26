import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { mortgage } from './mortgage';
import { mortgageService } from './mortgage.service';
import { amortScheduleHelper } from './app.amort_calculator';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './mortgage-display.component.html',
  styleUrls: ['./app.component.css']
})

export class MortgageComponent implements OnInit {
  public mortgages!: mortgage[]; // don't nec have to specify public, this is assumed, but we can
  public mortgage!: mortgage;
  // added the '!' per https://stackoverflow.com/questions/64874221/property-has-no-initializer-and-is-not-definitely-assigned-in-the-constructor
  // to rememdy error related to Strict Class Initialization wherein 'mortgages' was not definitely instantiated in constructor

  constructor(private mortgageService: mortgageService, private route: ActivatedRoute, private router: Router) {}

  public id!: number;

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
        this.id = params['id'];
      });
    this.getMortgage(this.id);
  }

  public getMortgage(mortgageId:number): void {
    this.mortgageService.getMortgage(mortgageId).subscribe(
      (response: mortgage) => {
        //console.log("got one mortgage");
        response.amort_schedule = amortScheduleHelper.getAmortSchedule(response)
        //console.log(response.amort_schedule);
        this.mortgage = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }


}
