import React from 'react';
import { ConfirmPopupContentPropsTypes } from './types/ConfirmPopupContentPropsTypes';

import './styles/ConfirmPopupContent.scss';

const ConfirmPopupContent = ({ onClose }: ConfirmPopupContentPropsTypes) => {
  return (
     <div className="confirm-popup">
      <h3 className="confirm-popup__title">Potwierdzenie</h3>
      <p className="confirm-popup__message">Operacja wykonana pomyślnie.</p>
      <button className="confirm-popup__button" onClick={onClose}>Zamknij</button>
    </div>
  );
};

export default ConfirmPopupContent;