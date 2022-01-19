import { Component, OnInit } from '@angular/core';
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  selectedLanguage = 'English';

  constructor(public translate: TranslateService) {
    const browserLang: string = translate.getBrowserLang();
    translate.use(browserLang.match(/en|es|pt|de/) ? browserLang : "en");
  }

  ngOnInit(): void {
  }

  changeLanguage(language: string) {
    this.translate.use(language);
    language !== 'en' ? this.selectedLanguage = 'Gaeilge' : this.selectedLanguage = 'English';
  }

}
