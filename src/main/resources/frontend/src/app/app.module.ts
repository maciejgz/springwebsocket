import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';

import { PushService } from './service/push.service';
import {Oauth2Service} from "./service/oauth2.service";
import {HttpClientModule} from "@angular/common/http";


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [PushService, Oauth2Service],
  bootstrap: [AppComponent]
})
export class AppModule { }
