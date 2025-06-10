import React from 'react';
import { Views } from 'Enums/Views';
import { NavbarPropsTypes } from './types/NavbarPropsTypes';

const Navbar = ({ onNavigate }: NavbarPropsTypes) => {
  return (
    <nav className="navbar">
      <button className="navbar__button" onClick={() => onNavigate(Views.WELCOME)}>Strona główna</button>
      <button className="navbar__button" onClick={() => onNavigate(Views.POINTS)}>Punkty</button>
      <button className="navbar__button" onClick={() => onNavigate(Views.PACKAGES)}>Moje Przesyłki</button>
    </nav>
  );
};

export default Navbar;