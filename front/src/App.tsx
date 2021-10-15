import React from 'react';
import { AnonymousDashboard } from './components/AnonymousDashboard';
import { AdminDashboard } from './components/AdminDashboard';
import { UserDashboard } from './components/UserDashboard';
import { BrowserRouter as Router, Switch, Route, Redirect } from 'react-router-dom';

function App() {
  return (
    <Router>
      <Switch>
        <Route path="/anonymous/dashboard">
          <AnonymousDashboard />
        </Route>
        <Route path="/admin/dashboard">
          <AdminDashboard />
        </Route>
        <Route path="/user/dashboard">
          <UserDashboard />
        </Route>
        <Redirect exact from="/" to="/anonymous/dashboard" />
      </Switch>
    </Router>
  );
}

export default App;
