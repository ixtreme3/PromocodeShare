import React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';

export const CouponCard: React.FC<any> = (props) => (
  <Card sx={{ display: 'flex', margin: '10px', ':hover': { outline: '2px dashed #4287f5' } }}>
    <CardMedia
      component="img"
      sx={{ width: '175px', objectFit: 'contain' }}
      image={props.input.picture}
      alt="Failed to load picture"
    />
    <CardContent sx={{ minWidth: '175px' }}>
      <Typography gutterBottom variant="h5">
        {props.input.promoCodeName}
      </Typography>
      <Typography variant="body2" color="text.secondary">
        {props.input.promoCodeDescription}
      </Typography>
      <Typography sx={{ marginTop: '15px' }} variant="body2" color="text.primary">
        Чтобы воспользоваться предложением скопируйте промокод:
      </Typography>
      <Typography sx={{ marginTop: '5px' }} variant="h5">
        {props.input.promoCode}
      </Typography>
    </CardContent>
  </Card>
);
