import React from 'react';
import { useDispatch } from 'react-redux';
import { showPopup, setSelectedLockerMatParcel } from 'Store/actions/actions';
import { PopupContentType } from 'Enums/PopupContentType';
import { LockerMatPoint } from 'Store/types';
import { LockerMatListPropsType } from './types/LockerMatPropsType';

import './styles/LockerMatList.scss';

const LockerMatList = ({ lockerMatList }: LockerMatListPropsType) => {
  const dispatch = useDispatch();

  const handleOpenPopup = (type: PopupContentType, point: LockerMatPoint) => {
    dispatch(setSelectedLockerMatParcel(point));
    dispatch(showPopup(type));
  };

  return (
    <div className="locker-mat-list">
      {lockerMatList.map((point) => (
        <div className="locker-mat-list__item" key={point.id}>
          <p>Punkt: <span className="locker-mat-list__address-value">{point.address}</span></p>
          <p>Dostępne rozmiary skrytek: <span className="locker-mat-list__sizes-value">{point.sizes?.join(', ')}</span></p>
          <div className="locker-mat-list__actions">
            <button
              className="locker-mat-list__button"
              onClick={() => handleOpenPopup(PopupContentType.Reserve, point)}
            >
              Rezerwuj
            </button>
            <button
              className="locker-mat-list__button"
              onClick={() => handleOpenPopup(PopupContentType.OpenRemotely, point)}
            >
              Otwórz zdalnie
            </button>
          </div>
        </div>
      ))}
    </div>
  );
};

export default LockerMatList;
