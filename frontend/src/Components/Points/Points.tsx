import React from 'react';
import LockerMatForm from 'Components/LockerMatForm/LockerMatForm';
import LockerMatList from 'Components/LockerMatList/LockerMatList';
import { PointsPropsTypes } from 'Components/Points/types/PointsPropsTypes';

import './styles/Points.scss';

const Points = ({ valuesObj, onChange, list }: PointsPropsTypes) => {
  return (
    <div className="points">
      <LockerMatForm valuesObj={valuesObj} onChange={onChange} />
       <div className="points__separator" />
      <LockerMatList lockerMatList={list} />
    </div>
  );
};

export default Points;