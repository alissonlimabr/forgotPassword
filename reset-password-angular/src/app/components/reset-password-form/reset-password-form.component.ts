import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';

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
export class ResetPasswordFormComponent implements OnInit {
  form: FormGroup;
  email: string | null = null;
  token: string | null = null;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService,
    private userService: UserService
  ) {
    this.form = this.fb.group(
      {
        password: ['', Validators.required],
        confirmPassword: ['', Validators.required],
      },
      { validators: passwordMatchValidator }
    );
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      if (params != null) {
        this.email = params.get('email');
        this.token = params.get('token');
      }
    });
  }

  resetPasswordForm() {
    if (this.form.valid && this.token != null && this.email != null) {
      const userData: User = {
        email: this.email,
        password: this.form.value.password,
      };

      this.userService.update(this.token, userData).subscribe({
        complete: () => {
          this.toastr.success('Sua senha foi alterada!', 'Sucesso');
          this.form.reset;
          this.router.navigate(['']);
        },
        error: (error: HttpErrorResponse) => {
          if (error.status === 400) {
            this.toastr.error('Token inválido ou expirado!', 'Erro');
          }
        },
      });
    }
  }
}
