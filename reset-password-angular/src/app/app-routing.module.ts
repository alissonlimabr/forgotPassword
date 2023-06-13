import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SendEmailFormComponent } from './components/send-email-form/send-email-form.component';
import { ResetPasswordFormComponent } from './components/reset-password-form/reset-password-form.component';

const routes: Routes = [
  { path: '', component: SendEmailFormComponent },
  {
    path: 'reset/:email/:token',
    component: ResetPasswordFormComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
