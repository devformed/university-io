import React from 'react';
import { Views } from 'Enums/Views';
import { useAuth } from 'contexts/AuthContext';
import { NavbarPropsTypes } from './types/NavbarPropsTypes';
import DefaultAvatar from 'Assets/Images/DefaultAvatar.png';
import BrandLogoSmall from 'Assets/Images/BagazomatSmall.png';

import './styles/Navbar.scss';

const Navbar = ({ onNavigate }: NavbarPropsTypes) => {
  const { logout } = useAuth();

  const handleLogout = () => {
    logout();
  };

  return (
    <nav className="navbar">
        <div className="navbar__left">
        <img
          src={DefaultAvatar}
          alt="avatar"
          className="navbar__avatar"
        />
        <span className="navbar__username">Tomasz</span>
      </div>
      <div className="navbar__right">
        <button className="navbar__button" onClick={() => onNavigate(Views.WELCOME)}>Strona główna</button>
        <button className="navbar__button" onClick={() => onNavigate(Views.POINTS)}>Punkty</button>
        <button className="navbar__button" onClick={() => onNavigate(Views.PACKAGES)}>Moje Przesyłki</button>
        <button className="navbar__button navbar__button--logout" onClick={handleLogout}>Wyloguj</button>
        <img
          src={BrandLogoSmall}
          alt="Brand Logo Small"
          className="navbar__logo-small"
        />
      </div>
    </nav>
  );
};

export default Navbar;
