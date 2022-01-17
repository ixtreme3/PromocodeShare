import React, { useEffect } from "react";
import { AnonymousDashboard } from './pages/AnonymousDashboard';
import { AdminDashboard } from './pages/AdminDashboard';
import { UserDashboard } from './pages/UserDashboard';
import { BrowserRouter as Router, Switch, Route, Redirect } from 'react-router-dom';
import Footer from './components/atoms/footer';
import { FriendsPage } from './pages/FriendsPage';

function App() {
  useEffect(() => {
    document.title = "Promocode Share"
  }, [])

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
