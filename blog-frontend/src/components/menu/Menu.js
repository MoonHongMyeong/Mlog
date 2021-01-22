import React, { useState } from 'react'
import Nav from './Nav';

export default function Menu() {
  const [isMenuOpened, setMenuOpened] = useState(false);

  const toggleMenu = () => {
    return setMenuOpened(!isMenuOpened);
  }

  return (
    <>
      {isMenuOpened ? <><button className="NavBtn" onClick={toggleMenu}>b</button><Nav /></>
        : <button className="NavBtn" onClick={toggleMenu}>b</button>
      }
    </>
  );
}