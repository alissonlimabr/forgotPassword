import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

function passwordMatchValidator(
  control: FormGroup
): { [key: string]: any } | null {
  // 1. Obtém os campos de senha e confirmação de senha do FormGroup
  const password = control.get('password');
  const confirmPassword = control.get('confirmPassword');

  // 2. Verifica se ambos os campos têm valores e se eles não são iguais
  const passwordsDoNotMatch =
    password && confirmPassword && password.value !== confirmPassword.value;

  // 3. Se as senhas não coincidirem, define o erro 'passwordsDoNotMatch' para ambos os campos e retorne o objeto de erro
  if (passwordsDoNotMatch) {
    password.setErrors({ passwordsDoNotMatch: true });
    confirmPassword.setErrors({ passwordsDoNotMatch: true });
    return { passwordsDoNotMatch: true };
  }

  // 4. Se as senhas coincidirem ou ambos os campos estiverem vazios, remove todos os erros e retorna null
  password?.setErrors(null);
  confirmPassword?.setErrors(null);
  return null;
}

@Component({
  selector: 'app-reset-password-form',
  templateUrl: './reset-password-form.component.html',
  styleUrls: ['./reset-password-form.component.scss'],
})
export class ResetPasswordFormComponent {
  form: FormGroup;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group(
      {
        email: [
          '',
          [
            Validators.required,
            Validators.email,
            Validators.pattern(
              /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/
            ),
          ],
        ],
        password: ['', Validators.required],
        confirmPassword: ['', Validators.required],
      },
      { validators: passwordMatchValidator }
    );
  }

  resetPasswordForm() {
    if (this.form.valid) {
    }
  }
}
