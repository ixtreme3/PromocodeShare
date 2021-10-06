import React from 'react';
import styled from 'styled-components';
import ButtonAppBar from './components/buttonAppBar';
import { StyledEngineProvider } from '@material-ui/styled-engine-sc';

const StyledButtonAppBar = styled(ButtonAppBar)`
  color: black;
  background-color: white;
  height: 80px;
`;

function App() {
  return (
    <StyledEngineProvider injectFirst>
      <div className="App">
        <StyledButtonAppBar />
      </div>
    </StyledEngineProvider>
  );
}

export default App;
