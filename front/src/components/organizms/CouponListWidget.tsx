import React, { useEffect, useState } from 'react';
import { CouponCard } from '../molecules/CouponCard';
import { v4 as uuidv4 } from 'uuid';
import InfiniteScroll from 'react-infinite-scroll-component';
import { BeatLoader } from 'react-spinners';
import { AxiosRestApplicationClient } from '../../api/PromocodeShareBackend';

const outerDivStyles = {
  display: 'flex',
  justifyContent: 'center',
};

const innerDivStyles = {
  paddingTop: '10px',
  flexBasis: '60%',
};

const api: AxiosRestApplicationClient = new AxiosRestApplicationClient('http://localhost:8080/');

export const CouponListWidget: React.FC = () => {
  const [coupons, setCoupons] = useState([{}]);
  const [page, setPage] = useState(1);

  useEffect(() => {
    api
      .findAllPaged({ page: 1, rowsPerPage: 10 })
      .then((response) => setCoupons((prevCoupons) => [...prevCoupons, ...response.data]));
  }, [page]);

  return (
    <div style={outerDivStyles}>
      <div style={innerDivStyles}>
        <InfiniteScroll
          dataLength={coupons.length}
          next={() => setPage((prevPage) => prevPage + 1)}
          hasMore={true}
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
          {coupons.map((coupon) => <CouponCard key={uuidv4()} input={coupon} />).slice(1)}
        </InfiniteScroll>
      </div>
    </div>
  );
};
