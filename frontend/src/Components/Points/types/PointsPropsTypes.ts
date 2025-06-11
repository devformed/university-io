import { LockerMatPoint, InputValues } from 'Store/types';

export interface PointsPropsTypes {
    valuesObj: InputValues;
    onChange: (data: Record<string, any>) => void;
    list: LockerMatPoint[];
}