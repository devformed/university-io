import { InputValues } from 'Store/types';

export interface LockerMatFormPropsTypes {
  valuesObj: InputValues;
  onChange: (data: Record<string, any>) => void;
}
