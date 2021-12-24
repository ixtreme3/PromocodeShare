import React, { useEffect, useState } from 'react';
import { api } from '../../api/api';
import { v4 as uuidv4 } from 'uuid';
import { FriendCard } from '../molecules/FriendCard';
import { Grid } from '@mui/material';
import { BeatLoader } from 'react-spinners';
import InfiniteScroll from 'react-infinite-scroll-component';

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
  const [hasMore, setHasMore] = useState(true);

  useEffect(() => api.fetchFriends(setFriends), []);

  const fetchMoreData = () => {
    if (friends.length >= 50) {
      setHasMore(false);
      return;
    }
    setTimeout(() => {
      setFriends((prevFriends) => [...prevFriends, ...prevFriends]);
    }, 1500);
  };

  return (
    <div style={outerDivStyles}>
      <div style={innerDivStyles}>
        <InfiniteScroll
          dataLength={friends.length}
          next={fetchMoreData}
          hasMore={hasMore}
          loader={
            <div style={{ textAlign: 'center', marginTop: '10px' }}>
              <BeatLoader color={'#1976d2'} />
            </div>
          }
          endMessage={
            <p style={{ textAlign: 'center' }}>
              <b>That's it for now!</b>
            </p>
          }
        >
          <Grid container spacing={0.5}>
            {friends.map((friend) => (
              <Grid key={uuidv4()} item md={6}>
                <FriendCard key={uuidv4()} input={friend} />
              </Grid>
            ))}
          </Grid>
        </InfiniteScroll>
      </div>
    </div>
  );
};
