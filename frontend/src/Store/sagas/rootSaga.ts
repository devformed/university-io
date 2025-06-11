import { all } from 'redux-saga/effects';
import getLockerMatPointsSaga from 'Store/sagas/getLockerMatPointsSaga';

export default function* rootSaga() {
  yield all([
    getLockerMatPointsSaga()
  ]);
}