import { call, put, takeEvery } from 'redux-saga/effects';
import { CANCEL_USER_RESERVATION } from 'Store/consts';
import { CancelUserReservationAction } from 'Store/actions/actionTypes';
import {
  showPopup,
  deleteUserReservation,
} from 'Store/actions/actions';
import { PopupContentType } from 'Enums/PopupContentType';
import { apiFetch } from 'Store/helpers/apiFetch';

const loggerPrefix = '[CancelUserReservationSaga]';

function* cancelUserReservation(action: CancelUserReservationAction): Generator<any, void, any> {
  try {
    console.log(`${loggerPrefix} Attempting to cancel reservation: ${action.data.reservationId}`);
    
    yield call(
      apiFetch,
      `lockermats/parcels/reservations/cancel?reservationId=${action.data.reservationId}`,
      'PUT',
      null
    );

    console.log(`${loggerPrefix} Reservation canceled successfully. Deleting reservation from store.`);
    yield put(deleteUserReservation(action.data.reservationId));
    yield put(showPopup(PopupContentType.Confirm));
  } catch (error) {
    console.error(`${loggerPrefix} Error: ${error}`);
    yield put(showPopup(PopupContentType.Failed));
  }
}

export default function* cancelUserReservationSaga() {
  yield takeEvery(CANCEL_USER_RESERVATION, cancelUserReservation);
}
