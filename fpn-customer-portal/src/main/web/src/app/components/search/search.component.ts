import { Router } from '@angular/router';
import { FPN } from 'src/app/shared/interfaces/fpn.interface';
import { searchFPN } from './../../shared/interfaces/fpn.interface';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { FpnService } from 'src/app/shared/services/fpn.service';

@Component({
  selector: 'app-search-page',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {

  searchForm: FormGroup = new FormGroup({});
  searchFormSubmitted = false;
  isLoading = false;
  numberValidatorRegexp = '^\\d*$';
  isShowHint = false;
  subscription: Subscription = new Subscription();
  errorMessage = ''; 

  constructor(
      private formBuilder: FormBuilder,
      private router: Router,
      private fpnService: FpnService
  ) { }

  ngOnInit(): void {
    this.searchForm = this.formBuilder.group({
      'fpn': [null, [Validators.required, Validators.pattern(this.numberValidatorRegexp)]],
      'rn': [null, [Validators.required, Validators.pattern(this.numberValidatorRegexp)]]
    });
  };

  searchFPN(): void {
    this.searchFormSubmitted = true;
    if (this.searchForm.invalid) {
      return;
    }
    if(this.errorMessage){
      this.errorMessage = '';
    }

    this.isLoading = true;
    this.searchForm.controls['fpn'].disable();
    this.searchForm.controls['rn'].disable();

    const credentials: searchFPN = {
      fpnNumber: this.searchForm.controls['fpn'].value,
      rnNumber: this.searchForm.controls['rn'].value
    }

    this.fpnService.fpnResult$ = this.fpnService.searchFpn(credentials);

    this.subscription.add(this.fpnService.fpnResult$.subscribe(
      (response) => {
        this.fpnService.fpnResult = response;
        this.fpnService.isSearching = true;
        this.fpnService.isExist = true;
        this.router.navigate(['/customer-portal/result']);
      }, 
      (error) => {
        if (error.status === 404) {
          this.fpnService.fpnResult = credentials as FPN;
          this.fpnService.isSearching = true;
          this.fpnService.isExist = false;
          this.router.navigate(['/customer-portal/result']);
        } else {
          this.errorMessage = error.error.responseText;
          this.isLoading = false;
          this.searchForm.controls['fpn'].enable();
          this.searchForm.controls['rn'].enable();
        }
      }));
  };

  toggleHint(action:boolean): void{
    this.isShowHint = action;
  };

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  };
}
