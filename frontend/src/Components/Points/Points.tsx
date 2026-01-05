import React from 'react';
import LockerMatForm from 'Components/LockerMatForm/LockerMatForm';
import LockerMatList from 'Components/LockerMatList/LockerMatList';
import MapView from 'Components/MapView/MapView';
import { PointsPropsTypes } from 'Components/Points/types/PointsPropsTypes';

import './styles/Points.scss';

const Points = ({ valuesObj, onChange, list }: PointsPropsTypes) => {
  return (
    <div className="points">
      <div className="points__left">
        <LockerMatForm valuesObj={valuesObj} onChange={onChange} />
        <div className="points__separator" />
        <LockerMatList lockerMatList={list} />
      </div>
      <div className="points__right">
        <MapView lockerMatPoints={list} />
      </div>
    </div>
  );
};

export default Points;