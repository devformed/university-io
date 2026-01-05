import React, { useMemo } from 'react';
import { useDispatch } from 'react-redux';
import { MyPackagesPropsTypes } from './types/MyPackagesPropsTypes';
import { cancelUserReservation } from 'Store/actions/actions';
import MapView from 'Components/MapView/MapView';
import { LockerMatPoint } from 'Store/types';

import './styles/MyPackages.scss';

const MyPackages = ({ reservations, lockerMatPoints }: MyPackagesPropsTypes) => {
  const dispatch = useDispatch();

  const handleCancel = (reservationId: number) => {
    dispatch(cancelUserReservation({ reservationId }));
  };

  console.log('MyPackages - reservations:', reservations);
  console.log('MyPackages - lockerMatPoints:', lockerMatPoints);

  return (
    <div className="my-packages">
      <div className="my-packages__content">
        <div className="my-packages__list">
          <h3 className="my-packages__title">Twoje rezerwacje</h3>
          {reservations.map((res) => (
            <div key={`${res.id}`} className="my-packages__item">
              <p>
                <span className="my-packages__label">Numer rezerwacji (ID):</span>
                <span className="my-packages__value">{`${res.id}`}</span>
              </p>
              <p>
                <span className="my-packages__label">Adres skrytki:</span>
                <span className="my-packages__value">{res.lockermatAddress}</span>
              </p>
              <p>
                <span className="my-packages__label">Od:</span>
                <span className="my-packages__value">{new Date(res.from).toLocaleString()}</span>
              </p>
              <p>
                <span className="my-packages__label">Do:</span>
                <span className="my-packages__value">{new Date(res.to).toLocaleString()}</span>
              </p>
              <button
                className="my-packages__button"
                onClick={() => handleCancel(res.id)}
              >
                Usuń rezerwację
              </button>
            </div>
          ))}
        </div>
        <div className="my-packages__map">
          <MapView lockerMatPoints={lockerMatPoints} reservations={reservations} />
        </div>
      </div>
    </div>
  );
};

export default MyPackages;
