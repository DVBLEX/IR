import {Component, Input, OnInit} from '@angular/core';
import {SettingsService} from "../../shared/services/settings.service";

@Component({
    selector: 'app-settings',
    templateUrl: './settings.component.html',
    styleUrls: ['./settings.component.scss']
})

export class SettingsComponent implements OnInit {

    isOpened = false;

    constructor(
        private settingsService: SettingsService
    ) {
    }

    ngOnInit(): void {
    }

    openSettings() {
        this.isOpened = !this.isOpened;
    }

    changeFontFamily(value: string) {
        this.settingsService.fontFamily$.next(value);
    }

    changeFontSizeBigger(value: number) {
        this.settingsService.fontSizeBigger$.next(value);
    }

    changeFontSizeSmaller(value: number) {
        this.settingsService.fontSizeSmaller$.next(value);
    }

    changeLetterSpacing(indicator: boolean) {
        this.settingsService.letterSpacing$.next(indicator);
    }

    reset() {
        this.settingsService.defaultState$.next(null);

    }

}
