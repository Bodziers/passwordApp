import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {PasswordService} from "../../service/password.service";
import {Password} from "../../model/password";

@Component({
  selector: 'app-selection-form',
  templateUrl: './selection-form.component.html',
  styleUrls: ['./selection-form.component.scss']
})
export class SelectionFormComponent implements OnInit {

  settings: FormGroup;
  loading = false;
  passwords: Password[] = [];
  //validationForm: FormGroup;
  noOfDigits = new FormControl();

  constructor(fb: FormBuilder, private passService: PasswordService) {
    this.settings = fb.group({
      lettersCheck: false,
      digitsCheck: false,
      specialSignsCheck: false,
      allCheck: false,
      noOfDigits: ['', [Validators.required, Validators.max(32)]]
    });
  }

  private allSelected(): void {
    this.settings.controls['lettersCheck'].patchValue(true);
    this.settings.controls['digitsCheck'].patchValue(true);
    this.settings.controls['specialSignsCheck'].patchValue(true);
  }

  private allDeselected() {
    this.settings.controls['lettersCheck'].patchValue(false);
    this.settings.controls['digitsCheck'].patchValue(false);
    this.settings.controls['specialSignsCheck'].patchValue(false);
  }

  ngOnInit(): void {
    this.settings.get("allCheck")?.valueChanges.subscribe(selectedValue => {
      if (selectedValue === true) {
        this.allSelected();
      } else {
        this.allDeselected();
      }
    });
  }

  onSubmit() {
    console.log(this.settings.value);
    this.reqPasswords();
  }

  reqPasswords() {
    this.passService.getPasswords(
      this.settings.controls['noOfDigits'].value,
      this.settings.controls['lettersCheck'].value,
      this.settings.controls['digitsCheck'].value,
      this.settings.controls['specialSignsCheck'].value).subscribe(data => {
      this.passwords = data;
      this.passService.updateData(this.passwords);
    });
  }

}
