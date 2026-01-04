import { call, put, takeEvery } from 'redux-saga/effects';
import { apiFetch } from 'Store/helpers/apiFetch';
import { showPopup, setAllUserReservations } from 'Store/actions/actions';
import { GET_ALL_USER_RESERVATIONS } from 'Store/consts';
import { GetAllUserReservationsAction } from 'Store/actions/actionTypes';
import { PopupContentType } from 'Enums/PopupContentType';

const loggerPrefix = '[GetAllUserReservationsSaga]';

function* getAllUserReservations(action: GetAllUserReservationsAction): Generator<any, void, any> {
  try {
    console.log(`${loggerPrefix} start. Fetching for all user reservations.`);
    const data = yield call(apiFetch, 'lockermats/reservations', 'GET', null);
    console.log(`${loggerPrefix} start. User reservations obtained - saving data in store.`);
    yield put(setAllUserReservations(data));
  } catch (error) {
     console.error(`${loggerPrefix} error: ${error}`);
        yield put(showPopup(PopupContentType.Failed));
  }
}

export default function* getAllUserReservationsSaga() {
  yield takeEvery(GET_ALL_USER_RESERVATIONS, getAllUserReservations);
}