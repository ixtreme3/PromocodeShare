import React, { useEffect, useState } from 'react';
import { CouponCard } from '../molecules/CouponCard';
import { api } from '../../api/api';
import { v4 as uuidv4 } from 'uuid';
import InfiniteScroll from 'react-infinite-scroll-component';
import { BeatLoader } from 'react-spinners';
// Из всего в PromocodeShareBackend.ts решил заимпортить вот это
// import { RestApplicationClient } from "../../api/PromocodeShareBackend";

const outerDivStyles = {
  display: 'flex',
  justifyContent: 'center',
};

const innerDivStyles = {
  paddingTop: '10px',
  flexBasis: '60%',
};

// Попытался исправить ошибку, про которую написал ниже.
// Нужно создать инстанс класса чтобы ушла ошибка? Если да, то что тут указать в аргументах конструктора?
// const api : RestApplicationClient<any> = new RestApplicationClient<any>();

export const CouponListWidget: React.FC = () => {
  const [coupons, setCoupons] = useState([{}]);
  const [hasMore, setHasMore] = useState(true);
  // const [page, setPage] = useState(1);

  useEffect(() => api.fetchCoupons(setCoupons), []);

  const fetchMoreData = () => {
    // setPage(prevPage => prevPage + 1);
    if (coupons.length >= 50) {
      setHasMore(false);
      return;
    }
    // findAllPaged подчеркивается красным, ошибка TS2339: Property 'findAllPaged' does not exist on type 'typeof RestApplicationClient'.
    // api.findAllPaged({ page: page, rowsPerPage: 10 })
    //   .then((response: { data: {}; }) => setCoupons(prevCoupons => [...prevCoupons, response.data]));
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
