import { LockerMatPoint, UserReservation } from 'Store/types';

export interface MapViewPropsTypes {
  lockerMatPoints: LockerMatPoint[];
  reservations?: UserReservation[];
  showOnlyReservations?: boolean;
}
