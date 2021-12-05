import React, { useEffect, useState } from 'react';
import { api } from '../../api/api';
import { v4 as uuidv4 } from 'uuid';
import { FriendCard } from '../molecules/FriendCard';
import { Grid } from '@mui/material';

const outerDivStyles = {
  display: 'flex',
  justifyContent: 'center',
};

const innerDivStyles = {
  paddingTop: '10px',
  flexBasis: '60%',
};

export const FriendListWidget: React.FC = () => {
  const [friends, setFriends] = useState([{}]);

  useEffect(() => api.fetchFriends(setFriends), []);

  return (
    <div style={outerDivStyles}>
      <div style={innerDivStyles}>
        <Grid container spacing={0.5}>
          {friends.map((friend) => (
            <Grid key={uuidv4()} item md={6}>
              <FriendCard key={uuidv4()} input={friend} />
            </Grid>
          ))}
        </Grid>
      </div>
    </div>
  );
};
