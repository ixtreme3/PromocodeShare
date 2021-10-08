import React from 'react';
import styled from 'styled-components';
import ButtonAppBar from './components/buttonAppBar';
import { StyledEngineProvider } from '@material-ui/styled-engine-sc';
import { Button } from '@material-ui/core';

const StyledButtonAppBar = styled(ButtonAppBar)`
  // doesn't work. but should it?
  color: black;
  background-color: white;
  height: 80px;
`;

const StyledButton = styled(Button)`
  //color: black; // doesn't work at all

  && {
    // works fine
    color: black;
  }
`;

function App() {
  return (
    <StyledEngineProvider injectFirst>
      <div className="App">
        <StyledButtonAppBar />
      </div>

      <StyledButton>Click on me</StyledButton>
    </StyledEngineProvider>
  );
}

export default App;
