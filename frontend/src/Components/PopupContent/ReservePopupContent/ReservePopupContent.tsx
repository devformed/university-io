import React from 'react';

import { ReservePopupContentPropsType } from './types/ReservePopupContentPropsType';

const ReservePopupContent = ({ parcel, onClose }: ReservePopupContentPropsType) => {
   return (
    <div>
      <div>Rezerwacja skrytki dla: {parcel.address}</div>
      <button onClick={onClose}>Zamknij</button>
    </div>
  );
};

export default ReservePopupContent;