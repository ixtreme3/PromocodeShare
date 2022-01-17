import React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { Box } from '@mui/material';

export const FriendCard: React.FC<any> = (props) => (
  <Card sx={{ display: 'flex', margin: '10px', ':hover': { outline: '1px solid #4287f5' }, height: '145px' }}>
    <Box sx={{ display: 'flex', flexDirection: 'column' }}>
      <CardMedia
        component="img"
        sx={{ width: '150px', objectFit: 'contain', flex: 1 }}
        image={props.input.picture}
        alt="Failed to load picture"
      />
    </Box>
    <CardContent sx={{ minWidth: '175px', borderLeft: 1, borderColor: 'grey.200' }}>
      <Typography sx={{ marginBottom: '0px' }} gutterBottom variant="h6">
        {props.input.name}
      </Typography>
      <Typography sx={{ marginTop: '5px' }} variant="body2" color="text.secondary">
        Количество промокодов:
        {props.input.promocodeCount}
      </Typography>
      <Typography sx={{ marginTop: '5px' }} variant="body2" color="text.secondary">
        О себе:
      </Typography>
      <Typography variant="body2" color="text.secondary">
        {props.input.description}
      </Typography>
    </CardContent>
  </Card>
);
