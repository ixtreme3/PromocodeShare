import React from 'react';
import { AnonymousDashboard } from './components/dashboards/AnonymousDashboard';
import { AdminDashboard } from './components/dashboards/AdminDashboard';
import { UserDashboard } from './components/dashboards/UserDashboard';
import { BrowserRouter as Router, Switch, Route, Redirect } from 'react-router-dom';
import Footer from './components/footer';
import { FriendsPage } from './components/pages/FriendsPage';

function App() {
  return (
    <Router>
      <Switch>
        <Route path="/anonymous/dashboard">
          <AnonymousDashboard />
          <Footer />
        </Route>
        <Route path="/admin/dashboard">
          <AdminDashboard />
          <Footer />
        </Route>
        <Route path="/user/dashboard">
          <UserDashboard />
          <Footer />
        </Route>
        <Route path="/user/friends">
          <FriendsPage />
          <Footer />
        </Route>
        <Redirect exact from="/" to="/anonymous/dashboard" />
      </Switch>
    </Router>
  );
}

export default App;
