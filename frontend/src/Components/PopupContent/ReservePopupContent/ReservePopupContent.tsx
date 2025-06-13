import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { reserveLockerMatParcel } from 'Store/actions/actions';
import { LockermatParcelSizes } from 'Enums/LockerMatParcelSizes';
import { ReservePopupContentPropsType } from './types/ReservePopupContentPropsType';

import './styles/ReservePopupContent.scss';

const ReservePopupContent = ({ parcel, onClose }: ReservePopupContentPropsType) => {
  const dispatch = useDispatch();
  const [selectedSize, setSelectedSize] = useState<LockermatParcelSizes | null>(null);
  const [from, setFrom] = useState<string>('');
  const [to, setTo] = useState<string>('');

  const handleReserve = () => {
    if (!selectedSize || !from || !to) return;

    dispatch(
      reserveLockerMatParcel({
        lockermatId: parcel.id,
        size: selectedSize,
        from,
        to,
      })
    );
  };

  return (
    <div className="reserve-popup">
      <h3 className="reserve-popup__title">Rezerwacja skrytki</h3>
      <p className="reserve-popup__address">Adres: <span className="reserve-popup__value">{parcel.address}</span></p>

      <div className="reserve-popup__sizes">
        <p className="reserve-popup__label">Wybierz rozmiar:</p>
        <div className="reserve-popup__options">
          {parcel.sizes?.map((size) => (
            <label key={size} className="reserve-popup__option">
              <input
                type="radio"
                name="size"
                value={size}
                checked={selectedSize === size}
                onChange={() => setSelectedSize(size as LockermatParcelSizes)}
              />
              <span className="reserve-popup__value">{size}</span>
            </label>
          ))}
        </div>
      </div>

      <div className="reserve-popup__datetime">
          <label className="reserve-popup__label" htmlFor="from">Data i godzina od:</label>
        <input
          type="datetime-local"
          id="from"
          className="reserve-popup__input reserve-popup__input--datetime"
          value={from}
          onChange={(e) => setFrom(e.target.value)}
        />

        <label className="reserve-popup__label" htmlFor="to">Data i godzina do:</label>
        <input
          type="datetime-local"
          id="to"
          className="reserve-popup__input reserve-popup__input--datetime"
          value={to}
          onChange={(e) => setTo(e.target.value)}
        />
      </div>

      <div className="reserve-popup__actions">
        <button
          className="reserve-popup__button"
          onClick={handleReserve}
          disabled={!selectedSize || !from || !to}
        >
          Rezerwuj
        </button>
        <button className="reserve-popup__button reserve-popup__button--cancel" onClick={onClose}>Anuluj</button>
      </div>
    </div>
  );
};

export default ReservePopupContent;
