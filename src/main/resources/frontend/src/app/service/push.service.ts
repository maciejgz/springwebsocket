import { Injectable, OnInit } from '@angular/core';
import {Client, Frame, Message} from "stompjs";

import * as stompjs from 'stompjs';
import * as SockJS from "sockjs-client";
import {Subject} from "rxjs/Subject";

@Injectable()
export class PushService implements OnInit {

	private messageSource = new Subject<string>();
	 messageReceived$ = this.messageSource.asObservable();
	stompClient: Client;

  constructor() {
    //TODO add token retriving service
	const socket = new SockJS('http://localhost:8080/gs-guide-websocket?access_token=dc178870-2c6b-43a2-ab8b-2f2586ca2e7d') as WebSocket;
	    this.stompClient = stompjs.over(socket);
		 this.stompClient.connect('', '', (frame: Frame) => {
      console.log('CONNECT CONNECT');
      this.stompClient.subscribe('/topic/greetings', (message: Message) => {
        this.onMessage(message);
      });
    });
  }

  private onMessage(message: Message) {
    console.log('Received greeting:', message.body);
    let json = JSON.parse(message.body);
    this.messageSource.next(json['content']);
  }


  ngOnInit() {

  }



}
