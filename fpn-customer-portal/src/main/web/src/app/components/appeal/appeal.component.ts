import { FpnService } from 'src/app/shared/services/fpn.service';
import { Subscription } from 'rxjs';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FPN } from 'src/app/shared/interfaces/fpn.interface';
import { ValidateFiles } from 'src/app/shared/validators/files.validator';

@Component({
  selector: 'app-appeal',
  templateUrl: './appeal.component.html',
  styleUrls: ['./appeal.component.scss']
})
export class AppealComponent implements OnInit {
  appealForm: FormGroup = new FormGroup({});
  appealFormSubmitted = false;
  isLoading = false;
  subscription: Subscription = new Subscription();
  
  selectedFiles: File[] = [];

  fpn: FPN = {
    fpnNumber: 0,
    rnNumber: 0
  };

  constructor(
    private formBuilder: FormBuilder,
    public fpnService: FpnService
  ) { }

  ngOnInit(): void {
    this.appealForm = this.formBuilder.group({
      'message': ['', Validators.required],
      'files': [[], ValidateFiles()],
      'confirm': [false, Validators.requiredTrue]
    });

    this.fpn = this.fpnService.fpnResult as FPN;
  };

  submitAppeal(){
    this.appealFormSubmitted = true;
    if(this.selectedFiles.length == 0 && this.appealForm.controls['files'].errors){
      this.appealForm.controls['files'].setErrors(null);
    }
    if (this.appealForm.invalid) {
      return;
    }

    this.isLoading = true;
    this.appealForm.controls['message'].disable();
    this.appealForm.controls['files'].disable();
    this.appealForm.controls['confirm'].disable();
  };

  fileOnChange(ev:any){
    const files = [...ev.target.files];
    this.selectedFiles.length = 0;
    
    this.appealForm.controls['files'].setValue(files, {
      emitModelToViewChange: false
    });
    this.selectedFiles = this.appealForm.controls['files'].valid ? files : [];    
  };

  removeSelectedFile(i:number){
    this.selectedFiles.splice(i, 1);
  };

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  };

}
