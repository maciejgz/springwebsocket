import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Token} from "../model/token";

@Injectable()
export class Oauth2Service implements OnInit {


  private _token: String = '';
  private _authenticated : boolean = false;
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
        let tokenResponse : Token = Token.fromJSON(data);
        if(tokenResponse.access_token) {
          this._authenticated = true;
          this._token = tokenResponse.access_token;
          console.log('token obtained=' + this._token);
        }
      }
    );
  }



  get authenticated(): boolean {
    return this._authenticated;
  }

  get token(): String {
    return this._token;
  }



}
