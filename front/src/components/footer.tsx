import { AppBar, Container, Toolbar, Typography } from "@material-ui/core";

export default function Footer() {
  return (
    <AppBar position="static" color="primary">
      <Container maxWidth="md">
        <Toolbar>
          <Typography variant="body1" color="inherit">
            здесь будет footer текст
          </Typography>
        </Toolbar>
      </Container>
    </AppBar>
  )
}
