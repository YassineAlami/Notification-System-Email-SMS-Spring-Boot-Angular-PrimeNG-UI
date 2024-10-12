import {FormBuilder, FormGroup, Validators} from "@angular/forms";

export class SampleFormComponent {
  // ...
  public form: FormGroup;
  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      timePicker: ['', Validators.required]
    });
  }
}
