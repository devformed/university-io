import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from 'Store/index';
import { reserveAndOpenLockerMatParcel } from 'Store/actions/actions';
import { LockermatParcelSizes } from 'Enums/LockerMatParcelSizes';
import { LockerMatPoint } from 'Store/types';

import { OpenRemotelyPopupContentPropsType } from './types/OpenRemotelyPopupContentPropsType';

import './styles/OpenRemotelyPopupContent.scss';


const OpenRemotelyPopupContent = ({ parcel, onClose }: OpenRemotelyPopupContentPropsType) => {
  const dispatch = useDispatch();
  const [selectedSize, setSelectedSize] = useState<LockermatParcelSizes | null>(null);

  const inputValues = useSelector((state: RootState) => state.app.inputValues);


  const handleOpenRemotely = () => {
    if (!selectedSize) return;

    dispatch(
      reserveAndOpenLockerMatParcel({
        lockermatId: parcel.id,
        size: selectedSize,
        from: inputValues.availableFrom || '',
        to: inputValues.availableTo || '',
        latitude: parcel.position.latitude,
        longitude: parcel.position.longitude,
      })
    );
  };

  return (
    <div className="open-remotely-popup">
      <h3 className="open-remotely-popup__title">Otwórz skrytkę zdalnie</h3>
      <p className="open-remotely-popup__address">{parcel.address}</p>
      <div className="open-remotely-popup__sizes">
        {parcel.availableSizes.map((size) => (
          <label key={size}>
            <input
              type="radio"
              name="size"
              value={size}
              checked={selectedSize === size}
              onChange={() => setSelectedSize(size as LockermatParcelSizes)}
            />
            {size}
          </label>
        ))}
      </div>
      <div className="open-remotely-popup__buttons">
        <button className="open-remotely-popup__button" onClick={handleOpenRemotely}>Otwórz</button>
        <button className="open-remotely-popup__button" onClick={onClose}>Zamknij</button>
      </div>
    </div>
  );
};

export default OpenRemotelyPopupContent;