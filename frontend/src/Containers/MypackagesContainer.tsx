import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { getAllUserReservations, getLockerMatPoints, setInputValues } from 'Store/actions/actions';
import { RootState } from 'Store/types';
import MyPackages from 'Components/MyPackages/MyPackages';

const MyPackagesContainer = () => {
  const dispatch = useDispatch();
  const reservations = useSelector((state: RootState) => state.app.userReservations);
  const lockerMatPoints = useSelector((state: RootState) => state.app.lockerMatList);

  useEffect(() => {
    // Wyczyść filtry aby pobrać wszystkie punkty
    dispatch(setInputValues({
      fulltext: '',
      sizes: [],
      availableFrom: '',
      availableTo: ''
    }));
    dispatch(getLockerMatPoints());
    dispatch(getAllUserReservations());
  }, []);

  return <MyPackages reservations={reservations} lockerMatPoints={lockerMatPoints} />;
};

export default MyPackagesContainer;
