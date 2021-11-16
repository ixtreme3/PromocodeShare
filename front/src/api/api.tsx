export const api = {
  fetchCompanies(setCompanyList: (value: ((prevState: string[]) => string[]) | string[]) => void) {
    fetch('http://localhost:3000/data/companies.json')
      .then((response) => response.json())
      .then((data) => setCompanyList(data))
      .catch((err) => {
        console.log('Error reading data: ' + err);
      });
  },

  fetchCoupons(setCoupons: (value: ((prevState: {}[]) => {}[]) | {}[]) => void) {
    fetch('http://localhost:3000/data/coupons.json')
      .then((response) => response.json())
      .then((data) => setCoupons(data))
      .catch((err) => {
        console.log('Error reading data: ' + err);
      });
  },
};
