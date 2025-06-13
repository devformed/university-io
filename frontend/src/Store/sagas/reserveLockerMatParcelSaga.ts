import { call, put, takeEvery } from 'redux-saga/effects';
import { apiFetch } from 'Store/helpers/apiFetch';
import { RESERVE_LOCKERMAT_PARCEL, SHOW_POPUP } from 'Store/consts';
import { showPopup } from 'Store/actions/actions';
import { ReserveLockerMatParcelAction } from 'Store/actions/actionTypes';
import { PopupContentType } from 'Enums/PopupContentType';

const loggerPrefix = '[ReserveLockerMatParcelSaga]';


function* reserveLockerMatParcel(action: ReserveLockerMatParcelAction): Generator<any, void, any> {
  try {
    console.log(`${loggerPrefix} start. Making reservation.`);
    const { data } = action;

    const requestBody = {
      lockermatId: data.lockermatId,
      size: data.size,
      from: new Date(data.from).toISOString(),
      to: new Date(data.to).toISOString(),
    }

    yield call(apiFetch, 'lockermats/parcels/reservations/reserve', 'PUT', requestBody);
    console.log(`${loggerPrefix} reservation succeed. Showing popup with confirmation.`);
    yield put({ type: SHOW_POPUP, data: PopupContentType.Confirm });
  } catch (error) {
    console.error(`${loggerPrefix} error: ${error}`);
    yield put(showPopup(PopupContentType.Failed));
  }
}

export default function* reserveLockerMatParcelSaga() {
  yield takeEvery(RESERVE_LOCKERMAT_PARCEL, reserveLockerMatParcel);
}