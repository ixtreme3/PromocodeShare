export const api = {
  fetchCompanies(setCompanyList: (value: ((prevState: string[]) => string[]) | string[]) => void) {
    fetch('../data/companies.json')
      .then((response) => response.json())
      .then((data) => setCompanyList(data))
      .catch((err) => {
        console.log('Error reading data: ' + err);
      });
  },

  fetchCoupons(setCoupons: (value: ((prevState: {}[]) => {}[]) | {}[]) => void) {
    fetch('../data/coupons.json')
      .then((response) => response.json())
      .then((data) => setCoupons(data))
      .catch((err) => {
        console.log('Error reading data: ' + err);
      });
  },

  fetchFriends(setFriends: (value: ((prevState: {}[]) => {}[]) | {}[]) => void) {
    fetch('../data/friends.json')
      .then((response) => response.json())
      .then((data) => setFriends(data))
      .catch((err) => {
        console.log('Error reading data: ' + err);
      });
  },
};
