import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-send-email-form',
  templateUrl: './send-email-form.component.html',
  styleUrls: ['./send-email-form.component.scss'],
})
export class SendEmailFormComponent {
  formSignUp: FormGroup;
  formSendEmail: FormGroup;
  selectedIndex: number = 0;

  constructor(private userService: UserService, private fb: FormBuilder) {
    this.formSignUp = this.fb.group({
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
      password: ['', [Validators.required, Validators.minLength(5)]],
    });

    this.formSendEmail = this.fb.group({
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
    });
  }

  signInForm() {
    if (this.formSignUp.invalid) return;

    this.userService.insert(this.formSignUp.value).subscribe({
      complete: () => {
        this.formSignUp.disable(); // desabilita o form após o submmit com sucesso.
        this.formSignUp.setErrors({ inserted: true }); //Invalida o form após o submmit com sucesso
        this.selectedIndex = 1; // Muda para a segunda tab
      },
    });
  }

  sendEmailForm() {
    if (this.formSendEmail.invalid) return;

    this.userService.sendEmail(this.formSendEmail.value).subscribe({
      complete: () => {
        this.formSendEmail.disable();
        this.formSendEmail.setErrors({ inserted: true });
      },
    });
  }
}
