import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITrip } from 'app/shared/model/trip.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TripService } from './trip.service';
import { TripDeleteDialogComponent } from './trip-delete-dialog.component';

@Component({
  selector: 'jhi-trip',
  templateUrl: './trip.component.html'
})
export class TripComponent implements OnInit, OnDestroy {
  trips: ITrip[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected tripService: TripService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
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
    this.tripService
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
    this.registerChangeInTrips();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITrip): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTrips(): void {
    this.eventSubscriber = this.eventManager.subscribe('tripListModification', () => this.reset());
  }

  delete(trip: ITrip): void {
    const modalRef = this.modalService.open(TripDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.trip = trip;
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
}
