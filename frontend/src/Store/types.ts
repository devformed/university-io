import { LockermatParcelSizes } from 'Enums/LockerMatParcelSizes';
import { Views } from 'Enums/Views';
import { SassNumber } from 'sass';

export interface RootState {
  app: {
    view: Views;
    inputValues: Record<string, string>;
    lockerMatList: LockerMatPoint[];
    userReservations: UserReservation[];
  };
}

export interface LockerMatPoint {
  id: number;
  address: string;
  position: {
    longitude: number;
    latitude: number;
  }
  sizes: string[];
}

export interface InputValues {
  fulltext?: string;
  availableFrom?: string;
  availableTo?: string;
  latitude?: number;
  longitude?: number;
  sizes?: LockermatParcelSizes[];
}

export interface UserReservation {
  id: number;
  cellId: number;
  lockermatId: number;
  lockermatAddress: string;
  from: string;
  to: string;
}
