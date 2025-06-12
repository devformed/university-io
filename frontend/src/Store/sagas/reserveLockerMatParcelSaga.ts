import { call, put, takeEvery } from 'redux-saga/effects';
import { apiFetch } from 'Store/helpers/apiFetch';
import { RESERVE_LOCKERMAT_PARCEL, SHOW_POPUP } from 'Store/consts';
import { ReserveLockerMatParcelAction } from 'Store/actions/actionTypes';
import { PopupContentType } from 'Enums/PopupContentType';

function* reserveLockerMatParcel(action: ReserveLockerMatParcelAction): Generator<any, void, any> {
  try {
    yield call(apiFetch, 'lockermats/parcels/reservations/reserve', 'PUT', action.data);
    yield put({ type: SHOW_POPUP, data: PopupContentType.Confirm });
  } catch (error) {
    console.error('Reservation error:', error);
  }
}

export default function* reserveLockerMatParcelSaga() {
  yield takeEvery(RESERVE_LOCKERMAT_PARCEL, reserveLockerMatParcel);
}