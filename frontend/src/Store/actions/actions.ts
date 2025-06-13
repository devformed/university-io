import {
  GET_LOCKERMAT_POINTS,
  SET_INPUT_VALUES,
  SET_LOCKERMAT_POINTS,
  CHANGE_VIEW,
  SHOW_POPUP,
  HIDE_POPUP,
  SET_SELECTED_LOCKERMAT_PARCEL,
  RESERVE_LOCKERMAT_PARCEL,
  OPEN_REMOTELY_LOCKERMAT_PARCEL,
  GET_ALL_USER_RESERVATIONS,
  SET_ALL_USER_RESERVATIONS,
  CANCEL_USER_RESERVATION,
} from 'Store/consts';

import { 
  SetInputValuesAction, 
  SetLockerMatPointsAction, 
  GetLockerMatPointsAction, 
  ChangeViewAction, 
  ShowPopupAction, 
  SetSelectedLockerMatParcelAction ,
  ReserveLockerMatParcelAction,
  OpenRemotelyLockerMatParcelAction,
  GetAllUserReservationsAction,
  SetAllUserReservationsAction,
  CancelUserReservationAction,
} from 'Store/actions/actionTypes';


export const getLockerMatPoints = (): { type: GetLockerMatPointsAction['type'] } => ({
  type: GET_LOCKERMAT_POINTS
});

export const setLockerMatPoints = (data: SetLockerMatPointsAction['data']) => ({
  type: SET_LOCKERMAT_POINTS,
  data
});

export const setInputValues = (data: SetInputValuesAction['data']) => ({
  type: SET_INPUT_VALUES,
  data
});

export const changeView = (data: ChangeViewAction['data']) => ({
  type: CHANGE_VIEW,
  data
});

export const showPopup = (data: ShowPopupAction['data']) => ({
  type: SHOW_POPUP,
  data
});

export const hidePopup = () => ({
  type: HIDE_POPUP,
});

export const reserveLockerMatParcel = (data: ReserveLockerMatParcelAction['data']) => ({
  type: RESERVE_LOCKERMAT_PARCEL,
  data,
});

export const setSelectedLockerMatParcel = (data: SetSelectedLockerMatParcelAction['data']) => ({
  type: SET_SELECTED_LOCKERMAT_PARCEL,
  data,
});

export const reserveAndOpenLockerMatParcel = (data: OpenRemotelyLockerMatParcelAction['data']) => ({
  type: OPEN_REMOTELY_LOCKERMAT_PARCEL,
  data,
});

export const getAllUserReservations = ():  { type: GetAllUserReservationsAction['type'] } => ({
  type: GET_ALL_USER_RESERVATIONS,
});

export const setAllUserReservations = (data: SetAllUserReservationsAction['data']) => ({
  type: SET_ALL_USER_RESERVATIONS,
  data
});

export const cancelUserReservation = (data: CancelUserReservationAction['data']) => ({
  type: CANCEL_USER_RESERVATION,
  data,
});