import React, { useRef, useEffect } from 'react'

export default function Nav() {
  const navRef = useRef(null);

  useEffect(() => {
    navRef.current.className = "showNav"
  }, [])

  return (
    <nav className="navigation" ref={navRef}>
      <form action="">
        <input type="text" placeholder="Search" /><button className="search"><i className="fas fa-search"></i></button>
      </form>
      <ul>
        <li>최신 포스트</li>
        <li>인기 포스트</li>
      </ul>
    </nav>
  );
}