import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";

@Injectable()
export class Oauth2Service implements OnInit {

  token: String;
  baseURL: String = "http://localhost:8080"

  constructor(private http: HttpClient) {
  }

  ngOnInit() {

  }


  getToken(username: string, password: string) {
    var apiUsername = 'COS2';
    var apiPass = 'secret';
    this.http.post(this.baseURL + '/oauth/token', "", {
      headers: new HttpHeaders().set('Authorization', 'Basic ' + btoa(apiUsername + ':' + apiPass)),
      params: new HttpParams().set('grant_type', 'password').set('username', username).set('password', password),
    }).subscribe(data => {

        console.log(data);


      }
    );
  }

}
