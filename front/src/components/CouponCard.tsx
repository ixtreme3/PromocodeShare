import React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { Box } from '@mui/material';

export const CouponCard: React.FC<any> = (props) => (
  <Card sx={{ display: 'flex', margin: '10px', ':hover': { outline: '1px solid #4287f5' } }}>
    <Box sx={{ display: 'flex', flexDirection: 'column' }}>
      <CardMedia
        component="img"
        sx={{ width: '175px', objectFit: 'contain', flex: 1 }}
        image={props.input.picture}
        alt="Failed to load picture"
      />
      <Typography variant="body2" color="text.secondary" align={'center'}>
        Продавец:
      </Typography>
      <Typography variant="body2" color="text.secondary" align={'center'}>
        {props.input.seller}
      </Typography>
    </Box>
    <CardContent sx={{ minWidth: '175px', borderLeft: 1, borderColor: 'grey.200' }}>
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
