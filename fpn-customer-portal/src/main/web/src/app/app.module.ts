import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {RouterModule} from "@angular/router";

import {routes} from './app-routing';
import {AppComponent} from './app.component';
import {CustomerPageComponent} from './components/customer-page/customer-page.component';
import { SearchComponent } from './components/search/search.component';
import {HeaderComponent} from './components/header/header.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import { ResultComponent } from './components/result/result.component';
import { FooterComponent } from './components/footer/footer.component';
import { PaymentComponent } from './components/payment/payment.component';
import { SettingsComponent } from './components/settings/settings.component';
import { AppealComponent } from './components/appeal/appeal.component';
import { EmptyPipe } from './shared/pipes/empty.pipe';


export function createTranslateLoader(http: HttpClient) {
    return new TranslateHttpLoader(http, "./assets/i18n/", ".json");
}

@NgModule({
    declarations: [
        AppComponent,
        CustomerPageComponent,
        SearchComponent,
        HeaderComponent,
        FooterComponent,
        ResultComponent,
        PaymentComponent,
        SettingsComponent,
        AppealComponent,
        EmptyPipe
    ],
    imports: [
        BrowserModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        NgbModule,
        RouterModule.forRoot(routes),
        TranslateModule.forRoot({
            loader: {
                provide: TranslateLoader,
                useFactory: createTranslateLoader,
                deps: [HttpClient]
            }
        })
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
