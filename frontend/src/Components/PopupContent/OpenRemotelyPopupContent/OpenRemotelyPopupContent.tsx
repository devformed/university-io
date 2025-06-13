import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { reserveAndOpenLockerMatParcel } from 'Store/actions/actions';
import { LockermatParcelSizes } from 'Enums/LockerMatParcelSizes';
import { OpenRemotelyPopupContentPropsType } from './types/OpenRemotelyPopupContentPropsType';
import './styles/OpenRemotelyPopupContent.scss';

const OpenRemotelyPopupContent = ({ parcel, onClose }: OpenRemotelyPopupContentPropsType) => {
  const dispatch = useDispatch();
  const [selectedSize, setSelectedSize] = useState<string | null>(null);
  const [from, setFrom] = useState('');
  const [to, setTo] = useState('');

  const handleOpenRemotely = () => {
    if (!selectedSize || !from || !to) return;

    dispatch(
      reserveAndOpenLockerMatParcel({
        lockermatId: parcel.id,
        size: selectedSize,
        from,
        to,
        latitude: parcel.position.latitude,
        longitude: parcel.position.longitude,
      })
    );
  };

  return (
    <div className="open-remotely-popup">
      <h3 className="open-remotely-popup__title">Otwórz skrytkę zdalnie</h3>
      <p className="open-remotely-popup__address">Adres: <span className="open-remotely-popup__value">{parcel.address}</span></p>

      <label className="open-remotely-popup__label">Wybierz rozmiar:</label>
      <div className="open-remotely-popup__options">
        {parcel.sizes?.map((size) => (
          <label key={size} className="open-remotely-popup__option">
            <input
              type="radio"
              name="size"
              value={size}
              checked={selectedSize === size}
              onChange={() => setSelectedSize(size)}
            />
            <span className="open-remotely-popup__value">{size}</span>
          </label>
        ))}
      </div>

      <label className="open-remotely-popup__label" htmlFor="from-time">Data i godzina od:</label>
      <input
        id="from-time"
        type="datetime-local"
        className="open-remotely-popup__input--datetime"
        value={from}
        onChange={(e) => setFrom(e.target.value)}
      />

      <label className="open-remotely-popup__label" htmlFor="to-time">Data i godzina do:</label>
      <input
        id="to-time"
        type="datetime-local"
        className="open-remotely-popup__input--datetime"
        value={to}
        onChange={(e) => setTo(e.target.value)}
      />

      <div className="open-remotely-popup__actions">
        <button
          className="open-remotely-popup__button"
          onClick={handleOpenRemotely}
          disabled={!selectedSize || !from || !to}
        >
          Otwórz
        </button>
        <button
          className="open-remotely-popup__button open-remotely-popup__button--cancel"
          onClick={onClose}
        >
          Zamknij
        </button>
      </div>
    </div>
  );
};

export default OpenRemotelyPopupContent;
