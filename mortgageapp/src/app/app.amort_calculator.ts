import { mortgage } from "./mortgage"

export class amortScheduleHelper {
    static getAmortSchedule(mortgage: mortgage): string[][] {
        console.log(mortgage)
        var output = []
        var amort_months = mortgage.amort_years * 12
        var starting_principal = mortgage.principal - mortgage.down_pmt
        var monthly_interest_rate = mortgage.annual_rate / 12
        var interest_component = (1+monthly_interest_rate)**(mortgage.amort_years*12)
        var monthly_pmt = (mortgage.principal - mortgage.down_pmt)*(monthly_interest_rate * interest_component) / (interest_component - 1)
        var pattern = '/(-?\d+)(\d{3})/'
        for (let i = 0; i < amort_months; i++) {
            var monthly_output = []
            monthly_output[0] = (new Date().getFullYear()+Math.floor(i/12)).toString()
            monthly_output[1] = (i+1).toString()
            monthly_output[2] = "$"+(starting_principal.toFixed(2).toLocaleString())
            var monthly_interest = starting_principal * monthly_interest_rate;
            var monthly_principal = monthly_pmt - monthly_interest
            starting_principal -= monthly_principal

            if (i == (mortgage.loan_term)*12-1) {
                monthly_principal += starting_principal
                starting_principal = 0;
            }
            monthly_output[3] = "$"+(monthly_interest.toFixed(2).toLocaleString())
            monthly_output[4] = "$"+(monthly_principal.toFixed(2).toLocaleString())
            //monthly_output[2] = starting_principal
            output[i] = monthly_output
        }
        //console.log(output)
        return output
    }
        
}