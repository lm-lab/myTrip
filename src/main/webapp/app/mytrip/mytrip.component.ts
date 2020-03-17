import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ITrip, Trip } from 'app/shared/model/trip.model';
import { MyTripService } from './mytrip.service';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';


@Component({
  selector: 'jhi-mytrip',
  templateUrl: './mytrip.component.html',
  styleUrls: ['mytrip.scss']
})
export class MytripComponent implements OnInit {
  events: string[] = [];
  opened!: boolean;
  isSaving = false;
  users: IUser[] = [];

  trips: ITrip[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  editForm = this.fb.group({
    id: [],
    depPlace: [null, [Validators.required]],
    depDate: [null, [Validators.required]],
    depTime: [null, [Validators.required]],
    depUtcZone: [null, [Validators.required]],
    arrPlace: [null, [Validators.required]],
    arrDate: [null, [Validators.required]],
    arrTime: [null, [Validators.required]],
    arrUtcZone: [null, [Validators.required]],
    cabinCat: [],
    marketingFlightId: [],
    operatorFlightId: [],
    marketingAirline: [],
    operatingAirline: [],
    transportation: [],
    bookingClass: [],
    cabinClass: [null, [Validators.required]]
  });

  constructor(
    private accountService: AccountService, 
    private loginModalService: LoginModalService,
    protected userService: UserService,
    protected myTripService: MyTripService,
    protected eventManager: JhiEventManager,
    protected activatedRoute: ActivatedRoute,
    protected parseLinks: JhiParseLinks,
    private fb: FormBuilder) {
    this.trips = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
    }

      loadAll(): void {
    this.myTripService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITrip[]>) => this.paginateTrips(res.body, res.headers));
  }

   reset(): void {
    this.page = 0;
    this.trips = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }
  
    sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTrips(data: ITrip[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.trips.push(data[i]);
      }
    }
  }

  resetForm(): void {
    this.editForm.reset();
  }

  save(): void {
    this.isSaving = true;
    const trip = this.createFromForm();
      this.subscribeToSaveResponse(this.myTripService.create(trip));
  }

  private createFromForm(): ITrip {
    return {
      ...new Trip(),
      id: this.editForm.get(['id'])!.value,
      depPlace: this.editForm.get(['depPlace'])!.value,
      depDate: this.editForm.get(['depDate'])!.value ? moment(this.editForm.get(['depDate'])!.value, DATE_TIME_FORMAT) : undefined,
      depTime: this.editForm.get(['depTime'])!.value,
      depUtcZone: this.editForm.get(['depUtcZone'])!.value,
      arrPlace: this.editForm.get(['arrPlace'])!.value,
      arrDate: this.editForm.get(['arrDate'])!.value ? moment(this.editForm.get(['arrDate'])!.value, DATE_TIME_FORMAT) : undefined,
      arrTime: this.editForm.get(['arrTime'])!.value,
      arrUtcZone: this.editForm.get(['arrUtcZone'])!.value,
      cabinCat: this.editForm.get(['cabinCat'])!.value,
      marketingFlightId: this.editForm.get(['marketingFlightId'])!.value,
      operatorFlightId: this.editForm.get(['operatorFlightId'])!.value,
      marketingAirline: this.editForm.get(['marketingAirline'])!.value,
      operatingAirline: this.editForm.get(['operatingAirline'])!.value,
      transportation: this.editForm.get(['transportation'])!.value,
      bookingClass: this.editForm.get(['bookingClass'])!.value,
      cabinClass: this.editForm.get(['cabinClass'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrip>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.loadAll();
    this.resetForm();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.loginModalService.open();
  }

}
