import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { reserveLockerMatParcel } from 'Store/actions/actions';
import { RootState } from 'Store/index';
import { LockermatParcelSizes } from 'Enums/LockerMatParcelSizes';
import { LockerMatPoint } from 'Store/types';
import { ReservePopupContentPropsType } from './types/ReservePopupContentPropsType';

import './styles/ReservePopupContent.scss';


const ReservePopupContent = ({ parcel, onClose }: ReservePopupContentPropsType) => {
  const dispatch = useDispatch();
  const [selectedSize, setSelectedSize] = useState<LockermatParcelSizes | null>(null);

  const inputValues = useSelector((state: RootState) => state.app.inputValues);

  const handleReserve = () => {
    if (!selectedSize) return;

    dispatch(
      reserveLockerMatParcel({
        lockermatId: parcel.id,
        size: selectedSize,
        from: inputValues.availableFrom || '',
        to: inputValues.availableTo || '',
      })
    );
  };

  return (
    <div className="reserve-popup">
      <h3 className="reserve-popup__title">Rezerwacja skrytki</h3>
      <p className="reserve-popup__address">Adres: {parcel.address}</p>
      <div className="reserve-popup__sizes">
        <p className="reserve-popup__label">Wybierz rozmiar:</p>
        {parcel.availableSizes.map((size) => (
          <label key={size} className="reserve-popup__option">
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
      <div className="reserve-popup__actions">
        <button className="reserve-popup__button" onClick={handleReserve}>Rezerwuj</button>
        <button className="reserve-popup__button reserve-popup__button--cancel" onClick={onClose}>Anuluj</button>
      </div>
    </div>
  );
};

export default ReservePopupContent;