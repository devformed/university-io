import {
  GET_LOCKERMAT_POINTS,
  SET_INPUT_VALUES,
  SET_LOCKERMAT_POINTS,
  CHANGE_VIEW,
} from 'Store/consts';
import { Views } from 'Enums/Views';
import { LockerMatPoint, InputValues  } from 'Store/types';


export const getLockerMatPoints = (): { type: typeof GET_LOCKERMAT_POINTS } => ({
  type: GET_LOCKERMAT_POINTS
});

export const setLockerMatPoints = (data: LockerMatPoint[]) => ({
  type: SET_LOCKERMAT_POINTS,
  data
});

export const setInputValues = (data: Partial<InputValues>) => ({
  type: SET_INPUT_VALUES,
  data
});

export const changeView = (data: Views) => ({
  type: CHANGE_VIEW,
  data
});

