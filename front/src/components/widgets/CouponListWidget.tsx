import React, { useEffect, useState } from 'react';
import { CouponCard } from '../cards/CouponCard';
import { api } from '../../api/api';
import { v4 as uuidv4 } from 'uuid';

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

  useEffect(() => api.fetchCoupons(setCoupons), []);

  return (
    <div style={outerDivStyles}>
      <div style={innerDivStyles}>
        {coupons.map((coupon) => (
          <CouponCard key={uuidv4()} input={coupon} />
        ))}
      </div>
    </div>
  );
};
