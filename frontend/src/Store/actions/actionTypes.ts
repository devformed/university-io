import {
    GET_LOCKERMAT_POINTS,
    SET_INPUT_VALUES,
    SET_LOCKERMAT_POINTS,
    CHANGE_VIEW,
    SHOW_POPUP,
    HIDE_POPUP,
    SET_SELECTED_LOCKERMAT_PARCEL,
    RESERVE_LOCKERMAT_PARCEL,
 } from 'Store/consts';
import {
    LockerMatPoint,
    InputValues,
} from 'Store/types';
import { Views } from 'Enums/Views';
import { PopupContentType } from 'Enums/PopupContentType';

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

export interface ShowPopupAction {
  type: typeof SHOW_POPUP;
  data: PopupContentType;
}

export interface HidePopupAction {
  type: typeof HIDE_POPUP;
}

export interface SetSelectedLockerMatParcelAction {
  type: typeof SET_SELECTED_LOCKERMAT_PARCEL;
  data: LockerMatPoint | null;
}

export interface ReserveLockerMatParcelAction {
  type: typeof RESERVE_LOCKERMAT_PARCEL;
  data: {
    lockermatId: string;
    size: string;
    from: string;
    to: string;
  };
}


export type AppActionTypes =
  | GetLockerMatPointsAction
  | SetInputValuesAction
  | SetLockerMatPointsAction
  | ChangeViewAction
  | ShowPopupAction
  | HidePopupAction
  | SetSelectedLockerMatParcelAction
  | ReserveLockerMatParcelAction;

