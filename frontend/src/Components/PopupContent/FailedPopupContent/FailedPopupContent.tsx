import React from 'react';
import { FailedPopupContentPropsTypes } from './types/FailedPopupContentPropsTypes';

import './styles/FailedPopupContent.scss';


const FailedPopupContent = ({ onClose }: FailedPopupContentPropsTypes) => {
  return (
    <div className="failed-popup">
      <h3 className="failed-popup__title">Błąd</h3>
      <p className="failed-popup__message">Wystąpił błąd. Spróbuj ponownie lub poinformuj administratora o problemie.</p>
      <button className="failed-popup__button" onClick={onClose}>Zamknij</button>
    </div>
  );
};

export default FailedPopupContent;