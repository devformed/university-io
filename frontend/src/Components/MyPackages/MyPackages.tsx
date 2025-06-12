import React from 'react';
import { UserReservation } from 'Store/types';
import { MyPackagesPropsTypes } from './types/MyPackagesPropsTypes';

import './styles/MyPackages.scss';


const MyPackages = ({ reservations }: MyPackagesPropsTypes) => {
  return (
    <div className="my-packages">
      <h3 className="my-packages__title">Twoje rezerwacje</h3>
      {reservations.map((res) => (
        <div key={res.id} className="my-packages__item">
          <p>ID Rezerwacji: {res.id}</p>
          <p>ID Skrytki: {res.parcelId}</p>
          <p>Od: {new Date(res.from).toLocaleString()}</p>
          <p>Do: {new Date(res.to).toLocaleString()}</p>
        </div>
      ))}
    </div>
  );
};

export default MyPackages;
