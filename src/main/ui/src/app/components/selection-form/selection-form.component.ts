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
  noOfWords = new FormControl();

  constructor(fb: FormBuilder, private passService: PasswordService) {
    this.settings = fb.group({
      lettersCheck: false,
      digitsCheck: false,
      specialSignsCheck: false,
      wordsCheck: false,
      allCheck: false,
      noOfWords: ['', [Validators.required, Validators.min(1),Validators.max(4)]],
      noOfDigits: ['', [Validators.required, Validators.min(1), Validators.max(32)]]
    });
  }

  private allSelected(): void {
    this.settings.controls['lettersCheck'].patchValue(true);
    this.settings.controls['digitsCheck'].patchValue(true);
    this.settings.controls['specialSignsCheck'].patchValue(true);
    this.settings.controls['wordsCheck'].patchValue(true);
  }

  private allDeselected() {
    this.settings.controls['lettersCheck'].patchValue(false);
    this.settings.controls['digitsCheck'].patchValue(false);
    this.settings.controls['wordsCheck'].patchValue(false);
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
    this.reqPasswords();
  }

  reqPasswords() {
    this.passService.getPasswords(
      this.settings.controls['noOfDigits'].value,
      this.settings.controls['lettersCheck'].value,
      this.settings.controls['digitsCheck'].value,
      this.settings.controls['wordsCheck'].value,
      this.settings.controls['noOfWords'].value,
      this.settings.controls['specialSignsCheck'].value).subscribe(data => {
      this.passwords = data;
      this.passService.updateData(this.passwords);
    });
  }

}
