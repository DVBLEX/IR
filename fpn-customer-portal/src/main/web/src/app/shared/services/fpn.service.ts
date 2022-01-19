import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {searchFPN, FPN} from '../interfaces/fpn.interface';
import {environment} from "../../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class FpnService {
    // private apiUrl = environment.apiUrl;
    private apiUrl = environment.apiUrl;

    fpnResult$ = new Observable<FPN>();
    fpnResult: FPN | null = null;
    isSearching = false;
    isExist = false;

    constructor(private http: HttpClient) {
    };

    searchFpn(credentials: searchFPN): Observable<FPN> {
        return this.http.post<FPN>(`${this.apiUrl}customer/fpn-search`, credentials);
    };

}
