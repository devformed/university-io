import { LockermatParcelSizes } from 'Enums/LockerMatParcelSizes';
import { Views } from 'Enums/Views';

export interface RootState {
  app: {
    view: Views;
    inputValues: Record<string, string>;
    lockerMatList: LockerMatPoint[];
  };
}

export interface LockerMatPoint {
  id: string;
  address: string;
  position: {
    longitude: number;
    latitude: number;
  }
  availableSizes: string[];
}

export interface InputValues {
  fulltext?: string;
  availableFrom?: string;
  availableTo?: string;
  latitude?: number;
  longitude?: number;
  sizes?: LockermatParcelSizes[];
}