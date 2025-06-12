import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { changeView } from 'Store/actions/actions';
import { RootState } from 'Store/types';
import Navbar from 'Components/Navbar/Navbar';
import Welcome from 'Components/Welcome/Welcome';
import PointsContainer from 'Containers/PointsContainer';
import MyPackagesContainer from 'Containers/MyPackagesContainer'; // todo: Implement MyPackagesContainer
import PopupContainer from 'Containers/PopupContainer';
import { Views } from 'Enums/Views';

const App = () => {
  const dispatch = useDispatch();
  const currentView = useSelector((state: RootState) => state.app.view);

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
    <div>
      <Navbar onNavigate={(view: Views) => dispatch(changeView(view))} />
      <main>{renderView()}</main>
      <PopupContainer />
    </div>
  );
};

export default App;