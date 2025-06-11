import {
    GET_LOCKERMAT_POINTS,
    SET_INPUT_VALUES,
    SET_LOCKERMAT_POINTS,
    CHANGE_VIEW,
 } from 'Store/consts';
import {
    LockerMatPoint,
    InputValues,
} from 'Store/types';
import { Views } from 'Enums/Views';

export interface SetInputValuesAction {
  type: typeof SET_INPUT_VALUES;
  data: Partial<InputValues>;
}

export interface SetLockerMatPointsAction {
  type: typeof SET_LOCKERMAT_POINTS;
  data: LockerMatPoint[];
}

export interface GetLockerMatPointsAction {
  type: typeof GET_LOCKERMAT_POINTS;
}

export interface ChangeViewAction {
  type: typeof CHANGE_VIEW;
  data: Views;
}

export type AppActionTypes =
  | GetLockerMatPointsAction
  | SetInputValuesAction
  | SetLockerMatPointsAction
  | ChangeViewAction;

