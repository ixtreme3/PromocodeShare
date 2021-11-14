import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import { useEffect, useState } from 'react';
import MenuItem from '@mui/material/MenuItem';
import { api } from '../api/api';
import { v4 as uuidv4 } from 'uuid';

const emptyFormState = { seller: '', couponName: '', couponDescription: '', couponCode: '', image: '' };

export const NewCouponForm: React.FC = () => {
  const [open, setOpen] = useState(false);

  const [form, setForm] = useState(emptyFormState);

  const [companyList, setCompanyList] = useState(['']);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    setForm(emptyFormState);
  };

  const handleFormChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setForm((prevForm) => ({ ...prevForm, [event.target.name]: event.target.value }));
  };

  useEffect(() => api.fetchCompanies(setCompanyList), []);

  return (
    <div>
      <Button color="inherit" onClick={handleClickOpen}>
        Create coupon
      </Button>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle sx={{ textAlign: 'center' }}>New coupon</DialogTitle>
        <DialogContent>
          <DialogContentText sx={{ marginBottom: '20px' }}>
            To create new coupon, please fill in all the fields of the form:
          </DialogContentText>
          <TextField
            required
            select
            value={form.seller}
            onChange={handleFormChange}
            name="seller"
            label="Seller"
            fullWidth
          >
            {companyList.map((company) => (
              <MenuItem key={uuidv4()} value={company}>
                {company}
              </MenuItem>
            ))}
          </TextField>
          <TextField
            required
            value={form.couponName}
            onChange={handleFormChange}
            name="couponName"
            label="Coupon name"
            type="text"
            margin="dense"
            fullWidth
            variant="outlined"
          />
          <TextField
            required
            value={form.couponDescription}
            onChange={handleFormChange}
            name="couponDescription"
            label="Coupon description"
            type="text"
            margin="dense"
            multiline
            fullWidth
            variant="outlined"
          />
          <TextField
            required
            value={form.couponCode}
            onChange={handleFormChange}
            name="couponCode"
            label="Coupon code"
            type="text"
            margin="dense"
            fullWidth
            variant="outlined"
          />
          <TextField
            name="image"
            onChange={handleFormChange}
            value={form.image}
            label="URL of an image"
            type="url"
            margin="dense"
            fullWidth
            variant="outlined"
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button
            onClick={() => {
              console.log(form);
              handleClose();
            }}
          >
            Create
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};
