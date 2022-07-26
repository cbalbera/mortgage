import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { mortgage } from './mortgage';
import { environment } from 'src/environments/environment';
//import { http_putpost_mortgage } from './home.component';

@Injectable({
    providedIn: 'root'
})
export class mortgageService {
    private apiServerURL = environment.apiBaseUrl;

    constructor(private http: HttpClient) { }

    // GET request for all mortgages
    public getMortgages(): Observable<mortgage[]> {
        return this.http.get<mortgage[]>(`${this.apiServerURL}/all`)
    }

    // GET request for one mortgage - TODO update
    
    public getMortgage(mortgageId: number): Observable<mortgage> {
        return this.http.get<mortgage>(`${this.apiServerURL}/result/${mortgageId}`)
    }

    // POST request
    public addMortgage(mortgage: string): void {
        console.log(mortgage);
        console.log(`${this.apiServerURL}/add`);
        this.http.post<JSON>(`${this.apiServerURL}/add`, mortgage).subscribe()
        // to have this return an Observable<JSON> return the below line, up to (not including) .toPromise()
        /*
        this.http.post<JSON>(`${this.apiServerURL}/add`, mortgage).toPromise().then(mortgage => {
            console.log(mortgage);
        })
        */
    }

    // PUT request
    public updateMortgage(mortgage: JSON): Observable<JSON> {
        return this.http.put<JSON>(`${this.apiServerURL}/update`, mortgage)
    }

    // DELETE request
    public deleteMortgage(mortgageId: number): Observable<void> {
        return this.http.delete<void>(`${this.apiServerURL}/delete/${mortgageId}`)
    }
}