import React from 'react';
import LockerMatForm from 'Components/LockerMatForm/LockerMatForm';
import LockerMatList from 'Components/LockerMatList/LockerMatList';
import MapView from 'Components/MapView/MapView';
import { PointsPropsTypes } from 'Components/Points/types/PointsPropsTypes';

import './styles/Points.scss';

const Points = ({ valuesObj, onChange, list }: PointsPropsTypes) => {
  return (
    <div className="points">
      <div className="points__top">
        <div className="points__search">
          <LockerMatForm valuesObj={valuesObj} onChange={onChange} />
        </div>
        <div className="points__map">
          <MapView lockerMatPoints={list} />
        </div>
      </div>
      <div className="points__separator" />
      <div className="points__bottom">
        <LockerMatList lockerMatList={list} />
      </div>
    </div>
  );
};

export default Points;