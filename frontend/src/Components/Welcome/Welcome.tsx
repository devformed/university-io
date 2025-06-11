import React from 'react';
import './styles/Welcome.scss';

const Welcome = () => {
  return (
    <div className="welcome">
      <h2 className="welcome__title">Witaj w Aplikacji Bagażomat!</h2>
      <p className="welcome__text">Przechowaj i wyślij swój bagaż wygodnie i bezpiecznie!</p>
    </div>
  );
};

export default Welcome;