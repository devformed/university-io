import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from 'Store/index';
import { PopupContentType } from 'Enums/PopupContentType';
import ReservePopupContent from 'Components/PopupContent/ReservePopupContent/ReservePopupContent';
import OpenRemotelyPopupContent from 'Components/PopupContent/OpenRemotelyPopupContent/OpenRemotelyPopupContent';
import { hidePopup, setSelectedLockerMatParcel } from 'Store/actions/actions';

import './styles/PopupContainer.scss';

const PopupContainer = () => {
  const dispatch = useDispatch();
  const popupContent = useSelector((state: RootState) => state.app.popupContent);
  const selectedLockerMatParcel = useSelector((state: RootState) => state.app.selectedLockerMatParcel);

  if (!popupContent || !selectedLockerMatParcel) return null;

  const handleClose = () => {
    dispatch(hidePopup());
    dispatch(setSelectedLockerMatParcel(null));
  };

  const renderContent = () => {
    switch (popupContent) {
      case PopupContentType.Reserve:
        return <ReservePopupContent parcel={selectedLockerMatParcel} onClose={handleClose} />;
      case PopupContentType.OpenRemotely:
        return <OpenRemotelyPopupContent parcel={selectedLockerMatParcel} onClose={handleClose} />;
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