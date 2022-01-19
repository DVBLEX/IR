import {Component, HostBinding, OnInit} from '@angular/core';
import { FPN } from 'src/app/shared/interfaces/fpn.interface';
import { FpnService } from 'src/app/shared/services/fpn.service';

@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.scss']
})
export class ResultComponent implements OnInit {

  isExist = false;
  fpn: FPN = {
    fpnNumber: 0,
    rnNumber: 0
  };

  constructor(private fpnService: FpnService) { }

  ngOnInit(): void {
    this.fpn = this.fpnService.fpnResult as FPN;
    this.isExist = this.fpnService.isExist;
  }
}
