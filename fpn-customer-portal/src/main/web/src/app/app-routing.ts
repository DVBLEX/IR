import {Routes} from '@angular/router';
import { SearchComponent } from './components/search/search.component';
import { ResultComponent } from './components/result/result.component';
import {CustomerPageComponent} from "./components/customer-page/customer-page.component";
import { PaymentComponent } from './components/payment/payment.component';
import { AppealComponent } from './components/appeal/appeal.component';
import { FpnGuard } from './shared/services/fpn.guard';

export const routes: Routes = [
    {path: '', redirectTo: '/customer-portal/search', pathMatch: 'full'},
    {path: 'customer-portal', redirectTo: '/customer-portal/search', pathMatch: 'full'},
    {path: 'customer-portal', component: CustomerPageComponent, children: [
        {path: 'search', component: SearchComponent},
        {path: 'result', component: ResultComponent, canActivate: [FpnGuard]},
        {path: 'payment', component: PaymentComponent, canActivate: [FpnGuard]},
        {path: 'appeal', component: AppealComponent, canActivate: [FpnGuard]}
    ]}
];
