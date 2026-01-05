import React, { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useAuth } from 'contexts/AuthContext';
import { changeView } from 'Store/actions/actions';
import { RootState } from 'Store/types';
import { setRefreshTokenFunction } from 'Store/helpers/apiFetch';
import Navbar from 'Components/Navbar/Navbar';
import Welcome from 'Components/Welcome/Welcome';
import Login from 'Components/Login/Login';
import PointsContainer from 'Containers/PointsContainer';
import MyPackagesContainer from 'Containers/MyPackagesContainer';
import PopupContainer from 'Containers/PopupContainer';
import { Views } from 'Enums/Views';

import './App.scss';

const App = () => {
  const dispatch = useDispatch();
  const auth = useAuth();
  const currentView = useSelector((state: RootState) => state.app.view);

  // Set refresh token function for apiFetch
  useEffect(() => {
    if (auth.refreshToken) {
      setRefreshTokenFunction(auth.refreshToken);
    }
  }, [auth.refreshToken]);

  // Reset view to Welcome when user logs in
  useEffect(() => {
    if (auth.isAuthenticated) {
      dispatch(changeView(Views.WELCOME));
    }
  }, [auth.isAuthenticated, dispatch]);

  if (auth.isLoading) {
    return (
      <div className="app">
        <div className="loading-container">Ładowanie...</div>
      </div>
    );
  }

  if (!auth.isAuthenticated) {
    return <Login />;
  }

  const renderView = () => {
    switch (currentView) {
      case Views.WELCOME:
        return <Welcome />;
      case Views.POINTS:
        return <PointsContainer />;
      case Views.PACKAGES:
        return <MyPackagesContainer />;
      default:
        return <Welcome />;
    }
  };

  return (
    <div className='app'>
      <Navbar onNavigate={(view: Views) => dispatch(changeView(view))} />
      <main>{renderView()}</main>
      <PopupContainer />
    </div>
  );
};

export default App;