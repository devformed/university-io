import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from 'Store/index';
import { PopupContentType } from 'Enums/PopupContentType';
import ReservePopupContent from 'Components/PopupContent/ReservePopupContent/ReservePopupContent';
import OpenRemotelyPopupContent from 'Components/PopupContent/OpenRemotelyPopupContent/OpenRemotelyPopupContent';
import ConfirmPopupContent from 'Components/PopupContent/ConfirmPopupContent/ConfirmPopupContent';
import { hidePopup, setSelectedLockerMatParcel } from 'Store/actions/actions';

import './styles/PopupContainer.scss';

const PopupContainer = () => {
  const dispatch = useDispatch();
  const popupContent = useSelector((state: RootState) => state.app.popupContent);
  const selectedLockerMatParcel = useSelector((state: RootState) => state.app.selectedLockerMatParcel);

  if (!popupContent) return null;

  const handleClose = () => {
    dispatch(hidePopup());
    dispatch(setSelectedLockerMatParcel(null));
  };

 const renderContent = () => {
    switch (popupContent) {
      case PopupContentType.Reserve:
        return selectedLockerMatParcel ? <ReservePopupContent parcel={selectedLockerMatParcel} onClose={handleClose} /> : null;
      case PopupContentType.OpenRemotely:
        return selectedLockerMatParcel ? <OpenRemotelyPopupContent parcel={selectedLockerMatParcel} onClose={handleClose} /> : null;
      case PopupContentType.Confirm:
        return <ConfirmPopupContent onClose={handleClose} />;
      default:
        return null;
    }
  };

  return (
    <div className="popup-container">
      <div className="popup-container__overlay">
        <div className="popup-container__content">
          {renderContent()}
        </div>
      </div>
    </div>
  );
};

export default PopupContainer;