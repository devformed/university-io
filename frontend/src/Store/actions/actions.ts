import {
  GET_LOCKERMAT_POINTS,
  SET_INPUT_VALUES,
  SET_LOCKERMAT_POINTS,
  CHANGE_VIEW,
  SHOW_POPUP,
  HIDE_POPUP,
  SET_SELECTED_LOCKERMAT_PARCEL,
} from 'Store/consts';
import { 
  SetInputValuesAction, 
  SetLockerMatPointsAction, 
  GetLockerMatPointsAction, 
  ChangeViewAction, 
  ShowPopupAction, 
  SetSelectedLockerMatParcelAction 
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

export const setSelectedLockerMatParcel = (data: SetSelectedLockerMatParcelAction['data']) => ({
  type: SET_SELECTED_LOCKERMAT_PARCEL,
  data,
});