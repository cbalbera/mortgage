import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { mortgage } from './mortgage';
import { environment } from 'src/environments/environment';

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
    public addMortgage(mortgage: mortgage): Observable<mortgage> {
        return this.http.post<mortgage>(`${this.apiServerURL}/add`, mortgage)
    }

    // PUT request
    public updateMortgage(mortgage: mortgage): Observable<mortgage> {
        return this.http.put<mortgage>(`${this.apiServerURL}/update`, mortgage)
    }

    // DELETE request
    public deleteMortgage(mortgageId: number): Observable<void> {
        return this.http.delete<void>(`${this.apiServerURL}/delete/${mortgageId}`)
    }
}