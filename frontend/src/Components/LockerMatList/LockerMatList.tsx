import React from 'react';
import { useDispatch } from 'react-redux';
import { showPopup } from 'Store/actions/actions';
import { PopupContentType } from 'Enums/PopupContentType';
import './styles/LockerMatList.scss';
import { LockerMatListPropsType } from './types/LockerMatPropsType';


const LockerMatList = ({ lockerMatList }: LockerMatListPropsType) => {
  const dispatch = useDispatch();

  return (
    <div className="locker-mat-list">
      {lockerMatList.map((point) => (
        <div className="locker-mat-list__item" key={point.id}>
          <div>{point.address}</div>
          <div>{point.availableSizes.join(', ')}</div>
          <div className="locker-mat-list__actions">
            <button className="locker-mat-list__button" onClick={() => dispatch(showPopup(PopupContentType.Reserve))}>
              Rezerwuj
            </button>
            <button className="locker-mat-list__button" onClick={() => dispatch(showPopup(PopupContentType.OpenRemotely))}>
              Otwórz zdalnie
            </button>
          </div>
        </div>
      ))}
    </div>
  );
};

export default LockerMatList;
