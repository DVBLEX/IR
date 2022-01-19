import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})

export class SettingsService {

  fontFamily$: BehaviorSubject<string> = new BehaviorSubject<string>('');
  fontSizeBigger$: BehaviorSubject<number> = new BehaviorSubject<number>(0);
  fontSizeSmaller$: BehaviorSubject<number> = new BehaviorSubject<number>(0);
  letterSpacing$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  defaultState$: BehaviorSubject<null> = new BehaviorSubject<null>(null);

}
