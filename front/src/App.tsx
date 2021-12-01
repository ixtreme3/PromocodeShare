import React, { useEffect } from "react";
import { AnonymousDashboard } from './components/AnonymousDashboard';
import { AdminDashboard } from './components/AdminDashboard';
import { UserDashboard } from './components/UserDashboard';
import { BrowserRouter as Router, Switch, Route, Redirect } from 'react-router-dom';
import Footer from './components/footer'

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
        <Redirect exact from="/" to="/anonymous/dashboard" />
      </Switch>
    </Router>
  );
}

export default App;
