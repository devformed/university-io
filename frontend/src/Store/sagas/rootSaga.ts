import { all } from 'redux-saga/effects';
import getLockerMatPointsSaga from 'Store/sagas/getLockerMatPointsSaga';
import reserveLockerMatParcelSaga from 'Store/sagas/reserveLockerMatParcelSaga';

export default function* rootSaga() {
  yield all([
    getLockerMatPointsSaga(),
    reserveLockerMatParcelSaga(),
  ]);
}