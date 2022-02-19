import { HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RegistrationService } from 'src/app/services/registration.service';

@Component({
  selector: 'app-registration-page',
  templateUrl: './registration-page.component.html',
  styleUrls: ['./registration-page.component.css']
})
export class RegistrationPageComponent implements OnInit {

  formData: FormGroup;

  registrationStatus: string = "";
  registrationIsSuccessful: boolean = true;

  constructor(private fb: FormBuilder, private registrationService: RegistrationService) {
    this.formData = fb.group({
      firstName: [null, Validators.required],
      lastName: [null, Validators.required],
      username: [null, [Validators.required, Validators.minLength(12), Validators.pattern('[a-zA-Z0-9_-]*')]],
      password: [null, [Validators.required, Validators.minLength(15)]],
      password2: [null, Validators.required],
      email: [null, [Validators.required, Validators.email]]
    });
  }

  checkUsernameValid(): boolean{
    const username = this.formData.get('username');

    if(!username?.value){
      return true;
    }

    if(username?.invalid){
      return false;
    }

    if(username.invalid){
      return false;
    }

    return true;
  }

  checkEmailValid(): boolean{
    const mail = this.formData.get('email');

    if(!mail?.value){
      return true;
    }

    if(mail.invalid){
      return false;
    }

    return true;
  }

  isPassword1Valid(): boolean{
    const pass1 = this.formData.get('password');

    if(!pass1?.value){
      return true;
    }
    if(pass1.invalid){
      return false;
    }

    const pass1Value: string = pass1.value;
    // check for minimum strength
    let hasSmallLetter: boolean = false;
    let hasCapitalLetter: boolean = false;
    let hasNumbers: boolean = false;

    const smallLettersRegex = /[a-z]/;
    const capitalLettersRegex = /[A-Z]/;
    const numbersRegex = /[0-9]/;

    for(let i = 0; i < pass1Value.length; i++){
      if(pass1Value.charAt(i).match(smallLettersRegex)){
        hasSmallLetter = true;
      }
      if(pass1Value.charAt(i).match(capitalLettersRegex)){
        hasCapitalLetter = true;
      }
      if(pass1Value.charAt(i).match(numbersRegex)){
        hasNumbers = true;
      }
    }

    if(hasSmallLetter && hasCapitalLetter && hasNumbers){
      return true;
    }

    return false;
  }

  isPassword2Valid(): boolean{
    const pass1 = this.formData.get('password');
    const pass2 = this.formData.get('password2');

    if(!pass2?.value){
      return true;
    }
    if(pass1?.value != pass2?.value){
      return false;
    }
    return true;
  }

  ngOnInit(): void {
  }

  register(): void{
    let userData = this.formData.value;

    delete userData.password2;

    const params: HttpParams = new HttpParams({
      fromObject: userData
    });

    this.registrationService.register(params).subscribe({
      error: () => {
        this.registrationStatus = "Registracija neuspješna";
        this.registrationIsSuccessful = false;
      },
      complete: () => {
        this.registrationStatus = "Registracija uspješna";
        this.registrationIsSuccessful = true;
      }
    });
  }
}
