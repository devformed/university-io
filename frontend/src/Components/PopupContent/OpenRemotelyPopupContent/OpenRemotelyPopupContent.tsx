import React from 'react';
import { OpenRemotelyPopupContentPropsType } from './types/OpenRemotelyPopupContentPropsType';


const OpenRemotelyPopupContent = ({ parcel, onClose }: OpenRemotelyPopupContentPropsType) => {
 return (
    <div>
      <div>Otwarcie zdalne skrytki dla: {parcel.address}</div>
      <button onClick={onClose}>Zamknij</button>
    </div>
  );
};

export default OpenRemotelyPopupContent;
