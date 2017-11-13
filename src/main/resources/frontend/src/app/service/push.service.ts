import {Injectable, OnInit} from '@angular/core';
import * as stompjs from "stompjs";
import {Client, Frame, Message} from "stompjs";
import * as SockJS from "sockjs-client";
import {Subject} from "rxjs/Subject";
import {Oauth2Service} from "./oauth2.service";

@Injectable()
export class PushService implements OnInit {

  private messageSource = new Subject<string>();
  messageReceived$ = this.messageSource.asObservable();
  stompClient: Client;
  private _connected : boolean = false;

  constructor(private oauthService : Oauth2Service ) {

  }

  private onMessage(message: Message) {
    console.log('Received greeting:', message.body);
    let json = JSON.parse(message.body);
    this.messageSource.next(json['content']);
  }

  connectToPushServer() {
    const socket = new SockJS('http://localhost:8080/gs-guide-websocket?access_token=' + this.oauthService.token) as WebSocket;
    this.stompClient = stompjs.over(socket);
    this.stompClient.connect('', '', (frame: Frame) => {
      console.log('CONNECT CONNECT');
      this.stompClient.subscribe('/topic/greetings', (message: Message) => {
        this.onMessage(message);
      });
    });
    this._connected=true;
  }


  ngOnInit() {

  }

  get connected(): boolean {
    return this._connected;
  }

}
