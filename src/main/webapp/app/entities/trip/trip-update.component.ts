import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITrip, Trip } from 'app/shared/model/trip.model';
import { TripService } from './trip.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-trip-update',
  templateUrl: './trip-update.component.html'
})
export class TripUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

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
    protected tripService: TripService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trip }) => {
      if (!trip.id) {
        const today = moment().startOf('day');
        trip.depDate = today;
        trip.arrDate = today;
      }

      this.updateForm(trip);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(trip: ITrip): void {
    this.editForm.patchValue({
      id: trip.id,
      depPlace: trip.depPlace,
      depDate: trip.depDate ? trip.depDate.format(DATE_TIME_FORMAT) : null,
      depTime: trip.depTime,
      depUtcZone: trip.depUtcZone,
      arrPlace: trip.arrPlace,
      arrDate: trip.arrDate ? trip.arrDate.format(DATE_TIME_FORMAT) : null,
      arrTime: trip.arrTime,
      arrUtcZone: trip.arrUtcZone,
      cabinCat: trip.cabinCat,
      marketingFlightId: trip.marketingFlightId,
      operatorFlightId: trip.operatorFlightId,
      marketingAirline: trip.marketingAirline,
      operatingAirline: trip.operatingAirline,
      transportation: trip.transportation,
      bookingClass: trip.bookingClass,
      cabinClass: trip.cabinClass
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const trip = this.createFromForm();
 
    if (trip.id !== undefined) {
      this.subscribeToSaveResponse(this.tripService.update(trip));
    } else {
      this.subscribeToSaveResponse(this.tripService.create(trip));
    }
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
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
