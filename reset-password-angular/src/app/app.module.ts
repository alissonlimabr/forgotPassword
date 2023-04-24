import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ResetPasswordFormComponent } from './components/reset-password-form/reset-password-form.component';
import { SendEmailFormComponent } from './components/send-email-form/send-email-form.component';
import { CheckEmailDirective } from './directives/check-email.directive';
import { MaterialModule } from './material/material.module';

@NgModule({
  declarations: [
    AppComponent,
    ResetPasswordFormComponent,
    SendEmailFormComponent,
    CheckEmailDirective,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
