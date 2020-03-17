import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export interface ITrip {
  id?: string;
  depPlace?: string;
  depDate?: Moment;
  depTime?: number;
  depUtcZone?: string;
  arrPlace?: string;
  arrDate?: Moment;
  arrTime?: number;
  arrUtcZone?: string;
  cabinCat?: string;
  marketingFlightId?: string;
  operatorFlightId?: string;
  marketingAirline?: string;
  operatingAirline?: string;
  transportation?: string;
  bookingClass?: string;
  cabinClass?: string;
  user?: IUser;
}

export class Trip implements ITrip {
  constructor(
    public id?: string,
    public depPlace?: string,
    public depDate?: Moment,
    public depTime?: number,
    public depUtcZone?: string,
    public arrPlace?: string,
    public arrDate?: Moment,
    public arrTime?: number,
    public arrUtcZone?: string,
    public cabinCat?: string,
    public marketingFlightId?: string,
    public operatorFlightId?: string,
    public marketingAirline?: string,
    public operatingAirline?: string,
    public transportation?: string,
    public bookingClass?: string,
    public cabinClass?: string,
    public user?: IUser
  ) {}
}
