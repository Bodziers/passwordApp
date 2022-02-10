import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable, ReplaySubject} from "rxjs";
import {map} from "rxjs/operators";
import {Password} from "../model/password";

@Injectable({
  providedIn: 'root'
})
export class PasswordService {
  private passwordUrl: string;
  constructor(private http: HttpClient) {
    this.passwordUrl = 'http://localhost:8080/api/password';
  }

  getPasswords(chars:number,
               letters: boolean,
               digits: boolean,
               words: boolean,
               numberofwords: number,
               specialsigns: boolean) :Observable<Password[]> {


    let params = new HttpParams()
      .set('chars', chars)
      .set('letters', letters)
      .set('digits', digits)
      .set('words', words)
      .set('numberofwords', numberofwords)
      .set('specialsigns', specialsigns);
    return this.http.get<Password[]>(this.passwordUrl, {params}).pipe(map(
      response => response));
  }

  private dataObs$ = new ReplaySubject<any>(1);

  getData() {
    return this.dataObs$.asObservable();
  }

  updateData(passwords: Password[]) {
    this.dataObs$.next(passwords);
  }
}
