import { FpnService } from 'src/app/shared/services/fpn.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FPN } from 'src/app/shared/interfaces/fpn.interface';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.scss']
})
export class PaymentComponent implements OnInit {
  paymentForm: FormGroup = new FormGroup({});
  paymentFormSubmitted = false;
  isLoading = false;

  fpn:FPN = {
    fpnNumber: 0,
    rnNumber: 0
  };

  constructor(
    private formBuilder: FormBuilder,
    public fpnService: FpnService
  ) { }

  ngOnInit(): void {
    this.paymentForm = this.formBuilder.group({
      'email': ['', [Validators.required, Validators.email]]
    });
    this.fpn = this.fpnService.fpnResult as FPN;
  }

  pay() {
    this.paymentFormSubmitted = true;
    if (this.paymentForm.invalid) {
      return;
    }

    this.isLoading = true;
    this.paymentForm.controls['email'].disable();
  }

}
