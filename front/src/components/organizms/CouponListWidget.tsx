import React, { useEffect, useState } from 'react';
import { CouponCard } from '../molecules/CouponCard';
import { api } from '../../api/api';
import { v4 as uuidv4 } from 'uuid';
import InfiniteScroll from 'react-infinite-scroll-component';
import { BeatLoader } from 'react-spinners';

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
  const [hasMore, setHasMore] = useState(true);

  useEffect(() => api.fetchCoupons(setCoupons), []);

  const fetchMoreData = () => {
    if (coupons.length >= 50) {
      setHasMore(false);
      return;
    }
    setTimeout(() => {
      setCoupons((prevCoupons) => [...prevCoupons, ...prevCoupons]);
    }, 1500);
  };

  return (
    <div style={outerDivStyles}>
      <div style={innerDivStyles}>
        <InfiniteScroll
          dataLength={coupons.length}
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
          {coupons.map((coupon) => (
            <CouponCard key={uuidv4()} input={coupon} />
          ))}
        </InfiniteScroll>
      </div>
    </div>
  );
};
