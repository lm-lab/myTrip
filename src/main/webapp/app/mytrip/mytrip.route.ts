import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router, Route } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITrip, Trip } from 'app/shared/model/trip.model';
import { MyTripService } from './mytrip.service';
import { MytripComponent } from './mytrip.component';

// @Injectable({ providedIn: 'root' })
export class MyTripResolve implements Resolve<ITrip> {
  constructor(private service: MyTripService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITrip> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((trip: HttpResponse<Trip>) => {
          if (trip.body) {
            return of(trip.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Trip());
  }
}

// export const tripsRoute: Route = {
//   {
//     path: 'new',
//     component: MytripComponent,
//     resolve: {
//       trip: MyTripResolve
//     },
//     data: {
//       authorities: [Authority.USER],
//       pageTitle: 'myTripApp.trip.home.title'
//     },
//     canActivate: [UserRouteAccessService]
//   }
// };

export const tripsRoute: Route = {
  path: '',
  component: MytripComponent,
  data: {
    authorities: [Authority.USER],
    pageTitle: 'home.title'
  },
   canActivate: [UserRouteAccessService]
};