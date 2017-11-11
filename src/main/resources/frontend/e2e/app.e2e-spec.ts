import { WebsocketClientPage } from './app.po';

describe('websocket-client App', () => {
  let page: WebsocketClientPage;

  beforeEach(() => {
    page = new WebsocketClientPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  });
});
