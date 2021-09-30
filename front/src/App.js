import React, { useEffect, useState } from "react";

function Clock() {
  const [date, setDate] = useState(new Date());

  function refreshClock() {
    setDate(new Date());
  }

  useEffect(() => {
    const timerId = setInterval(refreshClock, 1000);
    return function cleanup() {
      clearInterval(timerId);
    };
  }, []);

  return <span>{date.toLocaleTimeString()}</span>;
}

function App() {
  return (
    <div className="App">
      <span>Hello world! It's </span>
      <Clock />
    </div>
  );
}

export default App;
