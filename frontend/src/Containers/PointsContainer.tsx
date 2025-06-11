import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from 'Store/types';
import Points from 'Components/Points/Points';
import { getLockerMatPoints, setInputValues } from 'Store/actions/actions';
import { InputValues } from 'Store/types';

const PointsContainer = () => {
  const dispatch = useDispatch();
  const inputValues = useSelector((state: RootState) => state.app.inputValues);
  const lockerMatList = useSelector((state: RootState) => state.app.lockerMatList);

  const handleChange = (data: Partial<InputValues>) => {
    dispatch(setInputValues(data));
    dispatch(getLockerMatPoints());
  };

  return (
    <Points
      onChange={handleChange}
      valuesObj={inputValues}
      list={lockerMatList}
    />
  );
};

export default PointsContainer;
