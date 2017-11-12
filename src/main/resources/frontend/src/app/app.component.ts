import {Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {PushService} from "./service/push.service";
import {Subscription} from "rxjs/Subscription";
import {Oauth2Service} from "./service/oauth2.service";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {

  title = 'message receiver';
  messages: Array<string> = [];
  messageSub: Subscription;

  @ViewChild('usernameInput') usernameNameInput : ElementRef;
  @ViewChild('passwordInput') passwordInput : ElementRef;

  constructor(private pushService: PushService, private oauth2Service: Oauth2Service) {
  }

  ngOnInit(): void {
    this.messageSub = this.pushService.messageReceived$.subscribe(message => this.messages.push(message));
  }

  ngOnDestroy(): void {
    if (this.messageSub) {
      this.messageSub.unsubscribe();
    }
  }

  getToken() {
    this.oauth2Service.getToken(this.usernameNameInput.nativeElement.value, this.passwordInput.nativeElement.value);
  }

}
