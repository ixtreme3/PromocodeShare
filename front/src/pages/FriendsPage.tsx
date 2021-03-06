import React from 'react';
import { Box } from '@mui/material';
import { AppBar, Button, IconButton, Toolbar, Typography } from '@material-ui/core';
import MenuIcon from '@material-ui/icons/Menu';
import { FriendListWidget } from '../components/organizms/FriendListWidget';

export const FriendsPage: React.FC = () => (
  <Box sx={{ flexGrow: 1 }}>
    <AppBar position="static">
      <Toolbar>
        <IconButton size="large" edge="start" color="inherit" aria-label="menu" sx={{ mr: 2 }}>
          <MenuIcon />
        </IconButton>
        <Typography variant="h6" component="div" sx={{ fontWeight: 'bold' }}>
          Promocode Share
        </Typography>
        <Typography variant="h6" component="div" sx={{ marginLeft: '10px', flexGrow: 1 }}>
          Friends
        </Typography>
        <Button color="inherit">Login</Button>
      </Toolbar>
    </AppBar>
    <FriendListWidget />
  </Box>
);
