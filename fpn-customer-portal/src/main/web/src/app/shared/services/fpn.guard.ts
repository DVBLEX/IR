import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { FpnService } from './fpn.service';

@Injectable({
  providedIn: 'root'
})
export class FpnGuard implements CanActivate {
  constructor(
    private fpnService: FpnService,
    private router: Router
  ) { };

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    
    if (this.fpnService.isSearching) {
      return true;
    } else {
      this.router.navigateByUrl('/customer-portal/search');
      return false;
    }

  }

}
