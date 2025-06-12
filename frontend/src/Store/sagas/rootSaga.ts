import { all } from 'redux-saga/effects';
import getLockerMatPointsSaga from 'Store/sagas/getLockerMatPointsSaga';
import reserveLockerMatParcelSaga from 'Store/sagas/reserveLockerMatParcelSaga';
import openRemotelyParcelSaga from 'Store/sagas/openRemotelyParcelSaga';
import getAllUserReservationsSaga from 'Store/sagas/getAllUserReservationsSaga';

export default function* rootSaga() {
  yield all([
    getLockerMatPointsSaga(),
    reserveLockerMatParcelSaga(),
    openRemotelyParcelSaga(),
    getAllUserReservationsSaga(),
  ]);
}