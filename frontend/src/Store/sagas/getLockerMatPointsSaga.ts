import { debounce, put, call, select } from 'redux-saga/effects';
import { GET_LOCKERMAT_POINTS } from 'Store/consts';
import { setLockerMatPoints } from 'Store/actions/actions';
import { showPopup } from 'Store/actions/actions';
import { PopupContentType } from 'Enums/PopupContentType';
import { apiFetch } from 'Store/helpers/apiFetch';
import { LockerMatPoint } from 'Store/types';
import { GetLockerMatPointsAction } from 'Store/actions/actionTypes';

const loggerPrefix = '[GetLockerMatPointsSaga]';

function* getLockerMatPoints(action: GetLockerMatPointsAction): Generator<any, void, any> {
  try {
    console.log(`${loggerPrefix} start. Fetching for LockerMatPoints.`);

    const inputValues = yield select((state) => state.app.inputValues);

    const requestBody = {
      pageNumber: 0,
      pageSize: 20,
      data: {
        fulltext: inputValues.fulltext || '',
        availableFrom: inputValues.availableFrom,
        availableTo: inputValues.availableTo,
        position: {
          latitude: Number(inputValues.latitude),
          longitude: Number(inputValues.longitude)
        },
        sizes: inputValues.sizes || []
      }
    };

    const { data }: { data: LockerMatPoint[] } = yield call(apiFetch, 'lockermats', 'POST', requestBody);
    console.log(`${loggerPrefix} LockerMatPoints obtained. Saving data to store.`);
    yield put(setLockerMatPoints(data));
  } catch (error) {
    console.error(`${loggerPrefix} error: ${error}`);
    yield put(showPopup(PopupContentType.Failed));
  }
}

export default function* getLockerMatPointsSaga() {
  yield debounce(500, GET_LOCKERMAT_POINTS, getLockerMatPoints);
}
