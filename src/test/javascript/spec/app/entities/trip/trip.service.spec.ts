import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TripService } from 'app/entities/trip/trip.service';
import { ITrip, Trip } from 'app/shared/model/trip.model';

describe('Service Tests', () => {
  describe('Trip Service', () => {
    let injector: TestBed;
    let service: TripService;
    let httpMock: HttpTestingController;
    let elemDefault: ITrip;
    let expectedResult: ITrip | ITrip[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TripService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Trip(
        'ID',
        'AAAAAAA',
        currentDate,
        0,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            depDate: currentDate.format(DATE_TIME_FORMAT),
            arrDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find('123').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Trip', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            depDate: currentDate.format(DATE_TIME_FORMAT),
            arrDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            depDate: currentDate,
            arrDate: currentDate
          },
          returnedFromService
        );

        service.create(new Trip()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Trip', () => {
        const returnedFromService = Object.assign(
          {
            depPlace: 'BBBBBB',
            depDate: currentDate.format(DATE_TIME_FORMAT),
            depTime: 'BBBBBB',
            depUtcZone: 'BBBBBB',
            arrPlace: 'BBBBBB',
            arrDate: currentDate.format(DATE_TIME_FORMAT),
            arrTime: 'BBBBBB',
            arrUtcZone: 'BBBBBB',
            cabinCat: 'BBBBBB',
            marketingFlightId: 'BBBBBB',
            operatorFlightId: 'BBBBBB',
            marketingAirline: 'BBBBBB',
            operatingAirline: 'BBBBBB',
            transportation: 'BBBBBB',
            bookingClass: 'BBBBBB',
            cabinClass: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            depDate: currentDate,
            arrDate: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Trip', () => {
        const returnedFromService = Object.assign(
          {
            depPlace: 'BBBBBB',
            depDate: currentDate.format(DATE_TIME_FORMAT),
            depTime: 'BBBBBB',
            depUtcZone: 'BBBBBB',
            arrPlace: 'BBBBBB',
            arrDate: currentDate.format(DATE_TIME_FORMAT),
            arrTime: 'BBBBBB',
            arrUtcZone: 'BBBBBB',
            cabinCat: 'BBBBBB',
            marketingFlightId: 'BBBBBB',
            operatorFlightId: 'BBBBBB',
            marketingAirline: 'BBBBBB',
            operatingAirline: 'BBBBBB',
            transportation: 'BBBBBB',
            bookingClass: 'BBBBBB',
            cabinClass: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            depDate: currentDate,
            arrDate: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Trip', () => {
        service.delete('123').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
