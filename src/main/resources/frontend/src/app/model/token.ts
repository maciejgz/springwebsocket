export class Token {

  access_token: string;
  token_type: string;
  refresh_token: string;
  expires_in: number;
  scope: string;


  public static fromJSON(json: any): Token {
    if (typeof json === 'string') {
      return JSON.parse(json, Token.reviver);
    } else if (json !== undefined && json !== null) {
      let person = Object.create(Token.prototype);
      return Object.assign(person, json);
    } else {
      return json;
    }
  }

  public static reviver(key: string, value: any): any {
    return key === '' ? Token.fromJSON(value) : value;
  }


}
