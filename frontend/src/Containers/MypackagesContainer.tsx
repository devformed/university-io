import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { getAllUserReservations } from 'Store/actions/actions';
import { RootState } from 'Store/types';
import MyPackages from 'Components/MyPackages/MyPackages';

const MyPackagesContainer = () => {
  const dispatch = useDispatch();
  const reservations = useSelector((state: RootState) => state.app.userReservations);

  useEffect(() => {
    dispatch(getAllUserReservations());
  }, []);

  return <MyPackages reservations={reservations} />;
};

export default MyPackagesContainer;
