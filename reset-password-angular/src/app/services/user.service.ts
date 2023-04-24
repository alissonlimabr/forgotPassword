import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { User } from '../models/user';
import { environment } from 'src/environments/environment.prod';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) {}

  // Url da api
  apiUrl: string = environment.apiUrl;

  // Cadastro de usuário
  insert(objeto: User): Observable<User> {
    return this.http.post<User>(this.apiUrl + '/user/sign-up', objeto);
  }

  // Conexão com endpoint para reset/update de senha do usuário. Passa o token e a nova senha para verificações e update pelo back-end.
  update(token: string, newPassword: { password: string }): Observable<void> {
    // Define o cabeçalho do tipo json.
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    // conexão com a url da api
    const url = `${this.apiUrl}/password-reset/reset/${token}`;
    return this.http.post<void>(url, newPassword, { headers });
  }

  sendEmail(email: User): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
    });

    return this.http.post(
      this.apiUrl + '/password-reset/forgot?email=' + email.email,
      {
        headers: headers,
      }
    );
  }

  // requisita para o back-end um e-mail. Caso haja e-mail cadastrado, retorna bad request.
  checkEmail(email: string) {
    return this.http.get<any>(this.apiUrl + '/user/check-email/' + email);
  }
}
