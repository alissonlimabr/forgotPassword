import { Directive } from '@angular/core';
import {
  AbstractControl,
  NG_ASYNC_VALIDATORS,
  ValidationErrors,
} from '@angular/forms';
import { EMPTY, Observable, catchError } from 'rxjs';
import { UserService } from '../services/user.service';

@Directive({
  selector: '[appCheckEmail]',
  providers: [
    {
      provide: NG_ASYNC_VALIDATORS,
      useExisting: CheckEmailDirective,
      multi: true,
    },
  ],
})
export class CheckEmailDirective {
  constructor(private userService: UserService) {}

  validate(
    c: AbstractControl
  ): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> {
    // Caso o usuário insira algum e-mail para consulta
    // retorna um método que conecta ao endpoint de verificação de e-mail e passa como argumento, o valor do campo
    // onde é chamado.
    return this.userService.checkEmail(c.value).pipe(
      // O endpoint retorna bad-request, caso o e-mail já exista.
      // Portanto, é feito o tratamento e caso o erro seja bad-request,
      // a diretiva não é satisfeita e invalida o campo onde é chamada.
      catchError(async (err) =>
        err.status === 409 ? { appCheckEmail: true } : EMPTY
      )
    );
  }
}
