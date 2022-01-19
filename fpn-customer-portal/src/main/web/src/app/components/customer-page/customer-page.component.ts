import {Component, HostBinding, OnDestroy, OnInit} from '@angular/core';
import {SettingsService} from "../../shared/services/settings.service";
import {Subscription} from "rxjs";

@Component({
    selector: 'app-customer-page',
    templateUrl: './customer-page.component.html',
    styleUrls: ['./customer-page.component.scss']
})
export class CustomerPageComponent implements OnInit, OnDestroy {

    private subscription: Subscription = new Subscription();

    fontSizePercent = 100;

    @HostBinding('style.font-family') private mainFontFamily = '';
    @HostBinding('style.font-size') private mainFontSize = this.fontSizePercent + '%';
    @HostBinding('class.readable') private isReadable = false;

    constructor(
        private settingsService: SettingsService
    ) {
    }

    ngOnInit() {
        this.subscription.add(this.settingsService.fontFamily$.subscribe(
            (value) => this.mainFontFamily = value,
            (error) => this.mainFontFamily = ''
        ));

        this.subscription.add(this.settingsService.fontSizeBigger$.subscribe(
            (value) => {
                if (this.fontSizePercent < 150) {
                    this.fontSizePercent = this.fontSizePercent + value;
                    this.mainFontSize = this.fontSizePercent + '%';
                }
            },
            (error) => {
                this.fontSizePercent = 100;
                this.mainFontSize = this.fontSizePercent + '%';
            }
        ));

        this.subscription.add(this.settingsService.fontSizeSmaller$.subscribe(
            (value) => {
                if (this.fontSizePercent > 50) {
                    this.fontSizePercent = this.fontSizePercent - value;
                    this.mainFontSize = this.fontSizePercent + '%';
                }
            },
            (error) => {
                this.fontSizePercent = 100;
                this.mainFontSize = this.fontSizePercent + '%';
            }
        ));

        this.subscription.add(this.settingsService.letterSpacing$.subscribe(
            (indicator) => this.isReadable = indicator,
            (error) => this.isReadable = false
        ));

        this.subscription.add(this.settingsService.defaultState$.subscribe(
            (response) => {
                this.mainFontFamily = '';
                this.isReadable = false;
                this.fontSizePercent = 100;
                this.mainFontSize = this.fontSizePercent + '%';
            },
            (error) => this.isReadable = false
        ));
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
