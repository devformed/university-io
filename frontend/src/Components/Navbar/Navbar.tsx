import React from 'react';
import { Views } from 'Enums/Views';
import { NavbarPropsTypes } from './types/NavbarPropsTypes';
import DefaultAvatar from 'Assets/Images/DefaultAvatar.png';

import './styles/Navbar.scss';

const Navbar = ({ onNavigate }: NavbarPropsTypes) => {
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
      </div>
    </nav>
  );
};

export default Navbar;
