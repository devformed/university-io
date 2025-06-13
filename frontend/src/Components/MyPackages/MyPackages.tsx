import React from 'react';
import { useDispatch } from 'react-redux';
import { UserReservation } from 'Store/types';
import { MyPackagesPropsTypes } from './types/MyPackagesPropsTypes';
import { cancelUserReservation } from 'Store/actions/actions';

import './styles/MyPackages.scss';

const MyPackages = ({ reservations }: MyPackagesPropsTypes) => {
  const dispatch = useDispatch();

  const handleCancel = (reservationId: string) => {
    dispatch(cancelUserReservation({ reservationId }));
  };

  return (
    <div className="my-packages">
      <h3 className="my-packages__title">Twoje rezerwacje</h3>
      {reservations.map((res) => (
        <div key={res.id} className="my-packages__item">
          <p>
            <span className="my-packages__label">ID Rezerwacji:</span>
            <span className="my-packages__value">{res.id}</span>
          </p>
          <p>
            <span className="my-packages__label">ID Skrytki:</span>
            <span className="my-packages__value">{res.parcelId}</span>
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
  );
};

export default MyPackages;
