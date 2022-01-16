import * as React from 'react';
import { AppBar, Button, IconButton, Toolbar, Typography } from '@material-ui/core';
import MenuIcon from '@material-ui/icons/Menu';
import { CouponListWidget } from './CouponListWidget';
import { Box } from '@mui/material';
import { NewCouponForm } from './NewCouponForm';

export const AnonymousDashboard: React.FC = () => (
  <Box sx={{ flexGrow: 1 }}>
    <AppBar position="static">
      <Toolbar>
        <IconButton size="large" edge="start" color="inherit" aria-label="menu" sx={{ mr: 2 }}>
          <MenuIcon />
        </IconButton>
        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
          Promocode Share
        </Typography>
        <NewCouponForm />
        <Button color="inherit">Login</Button>
      </Toolbar>
    </AppBar>

    <CouponListWidget />
  </Box>
);
