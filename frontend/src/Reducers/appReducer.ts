import {
  SET_LOCKERMAT_POINTS,
  SET_INPUT_VALUES,
  CHANGE_VIEW,
} from 'Store/consts';
import { Views } from 'Enums/Views';
import { AppActionTypes } from 'Store/actions/actionTypes';
import { LockerMatPoint, InputValues } from 'Store/types';

export interface AppState {
  view: Views;
  inputValues: InputValues;
  lockerMatList: LockerMatPoint[];
}

const initialInputValues: InputValues = { 
  fulltext: '',
  availableFrom: '',
  availableTo: '',
  latitude: 52.379189,
  longitude: 4.899431,
  sizes: [],
}

const initialState: AppState = {
  view: Views.WELCOME,
  inputValues: {},
  lockerMatList: []
};

const appReducer = (state: AppState = initialState, action: AppActionTypes) => {
  switch (action.type) {
    case SET_INPUT_VALUES:
      return {
        ...state,
        inputValues: {
          ...state.inputValues,
          ...action.data,
        }
      };
    case SET_LOCKERMAT_POINTS:
      return {
        ...state,
        lockerMatList: action.data,
      };
    case CHANGE_VIEW:
        return {
        ...state,
        view: action.data,
        };
    default:
      return state;
  }
};

export default appReducer;