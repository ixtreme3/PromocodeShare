import React from 'react';
import { CouponCard } from './CouponCard';

let inputList = [
  {
    picture: 'https://www.mightycall.ru/wp-content/uploads/2014/09/dodo-pitstsa.jpg',
    seller: 'Додо Пицца',
    promoCodeName: 'Скидка на пиццу в Новосибирске',
    promoCodeDescription: `
      Предложение действует при покупке мясных пицц в городе Новосибирске,
      участвующих в акции (кроме товаров-исключений). При оформлении покупки
      необходимо обязательно авторизоваться или зарегистрироваться с помощью 
      номера телефона в Бонусной программе. Размер дополнительных бонусов, 
      начисляемых за покупку, указан в карточке каждого акционного товара. 
      Акционные Бонусные рубли активируются через 14 дней.
    `,
    promoCode: 'KODI991',
  },
  {
    picture: 'https://media-cdn.tripadvisor.com/media/photo-s/1a/fe/be/14/papa-john-s-azerbaijan.jpg',
    seller: 'Папа Джонс',
    promoCodeName: 'Скидка на пиццу студентам',
    promoCodeDescription: `
      Воспользуйтесь промокодом и получите 20% скидки при заказе на сумму от
      1500 рублей и 25% скидки при заказе от 2000 рублей. Предложение
      действует только на ассортимент раздела "Студенческая пицца".
      Скидка не суммируется с любыми другими специальными предложениями или
      скидками, действующими в период данной акции.
    `,
    promoCode: 'promocodi15',
  },
  {
    picture: 'https://www.sostav.ru/articles/rus/2013/06.02/news/images/m1.jpg',
    seller: 'МВидео',
    promoCodeName: 'Скидка на бытовую технику LG',
    promoCodeDescription: `
      Скидки распространяются на бытовую технику от бренда LG. 
      Цены указаны с учетом скидок. Данная акция не суммируется с другими 
      скидками и предложениями.
    `,
    promoCode: 'MVideo2021',
  },
];

const outerDivStyles = {
  display: 'flex',
  justifyContent: 'center',
};

const innerDivStyles = {
  paddingTop: '10px',
  flexBasis: '60%',
};

export const CouponListWidget: React.FC = () => (
  <div style={outerDivStyles}>
    <div style={innerDivStyles}>
      <CouponCard input={inputList[0]} />
      <CouponCard input={inputList[1]} />
      <CouponCard input={inputList[2]} />
    </div>
  </div>
);
