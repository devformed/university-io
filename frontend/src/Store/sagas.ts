import { debounce, put, call, all } from 'redux-saga/effects';
import { GET_LOCKERMAT_POINTS } from './consts';
import { setLockerMatPoints } from './actions/actions';

function* fetchLockerMatPointsSaga(action: any): Generator<any, void, any> {
  try {
    const response = yield call(fetch, 'http://localhost:8080/lockermats', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        pageNumber: 0,
        pageSize: 20,
        data: {
          fulltext: action.payload.fulltext || '',
          availableFrom: action.payload.availableFrom,
          availableTo: action.payload.availableTo,
          position: {
            latitude: Number(action.payload.latitude),
            longitude: Number(action.payload.longitude)
          },
          sizes: action.payload.sizes || []
        }
      })
    });

    const data = yield response.json();
    yield put(setLockerMatPoints(data));
  } catch (error) {
    console.error('Fetch error:', error);
  }
}

function* watchLockerMatSearch() {
  yield debounce(500, GET_LOCKERMAT_POINTS, fetchLockerMatPointsSaga);
}

export default function* rootSaga() {
  yield all([watchLockerMatSearch()]);
}