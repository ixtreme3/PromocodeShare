import React, { useEffect, useState } from 'react';
import { CouponCard } from './CouponCard';

const outerDivStyles = {
  display: 'flex',
  justifyContent: 'center',
};

const innerDivStyles = {
  paddingTop: '10px',
  flexBasis: '60%',
};

export const CouponListWidget: React.FC = () => {
  const [coupons, setCoupons] = useState([{}]);

  useEffect(() => {
    fetch('http://localhost:3000/data/coupons.json')
      .then((response) => response.json())
      .then((data) => setCoupons(data))
      .catch((err) => {
        console.log('Error reading data: ' + err);
      });
  }, []);

  return (
    <div style={outerDivStyles}>
      <div style={innerDivStyles}>
        {coupons.map((coupon) => (
          <CouponCard input={coupon} />
        ))}
      </div>
    </div>
  );
};
