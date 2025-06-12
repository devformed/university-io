import { call, put, takeEvery } from 'redux-saga/effects';
import { OPEN_REMOTELY_LOCKERMAT_PARCEL } from 'Store/consts';
import { apiFetch } from 'Store/helpers/apiFetch';
import { showPopup } from 'Store/actions/actions';
import { PopupContentType } from 'Enums/PopupContentType';
import { OpenRemotelyLockerMatParcelAction } from 'Store/actions/actionTypes';

const loggerPrefix = '[OpenRemotelyParcelSaga]';

function* openRemotelyParcel(action: OpenRemotelyLockerMatParcelAction): Generator<any, void, any> {
  try {
    console.log(`${loggerPrefix} start. Fetching for reservationId.`);
    const reservationId: string = yield call(
      apiFetch,
      'lockermats/parcels/reservations/reserve',
      'PUT',
      {
        lockermatId: action.data.lockermatId,
        size: action.data.size,
        from: action.data.from,
        to: action.data.to,
      }
    );
    console.log(`${loggerPrefix} reservationId obtained. Making request to open remotely.`);
    yield call(
      apiFetch,
      `lockermats/parcels/reservations/open-remotely?reservationId=${reservationId}`,
      'PUT',
      {
        latitude: action.data.latitude,
        longitude: action.data.longitude,
      }
    );
    console.log(`${loggerPrefix} Remote open request successful.`);
    yield put(showPopup(PopupContentType.Confirm));
  } catch (error) {
    console.error(`${loggerPrefix} error: ${error}`);
    yield put(showPopup(PopupContentType.Failed));
  }
}

export default function* openRemotelyParcelSaga() {
  yield takeEvery(OPEN_REMOTELY_LOCKERMAT_PARCEL, openRemotelyParcel);
}
