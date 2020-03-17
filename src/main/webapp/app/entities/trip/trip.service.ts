import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITrip } from 'app/shared/model/trip.model';

type EntityResponseType = HttpResponse<ITrip>;
type EntityArrayResponseType = HttpResponse<ITrip[]>;

@Injectable({ providedIn: 'root' })
export class TripService {
  public resourceUrl = SERVER_API_URL + 'api/trips';

  constructor(protected http: HttpClient) {}

  create(trip: ITrip): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(trip);
    return this.http
      .post<ITrip>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(trip: ITrip): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(trip);
    return this.http
      .put<ITrip>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ITrip>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITrip[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(trip: ITrip): ITrip {
    const copy: ITrip = Object.assign({}, trip, {
      depDate: trip.depDate && trip.depDate.isValid() ? trip.depDate.toJSON() : undefined,
      arrDate: trip.arrDate && trip.arrDate.isValid() ? trip.arrDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.depDate = res.body.depDate ? moment(res.body.depDate) : undefined;
      res.body.arrDate = res.body.arrDate ? moment(res.body.arrDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((trip: ITrip) => {
        trip.depDate = trip.depDate ? moment(trip.depDate) : undefined;
        trip.arrDate = trip.arrDate ? moment(trip.arrDate) : undefined;
      });
    }
    return res;
  }
}
