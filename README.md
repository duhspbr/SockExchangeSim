# Stock Exchange App Simulator

APP (maybe a game later?) Developed to test some functions within the Recyclerview and Android Room.

## Objective

Simulate a stock exchange app that makes fictitious and random stock purchases.

## App structure
- Developed in Java;
- Database MYSql;
- Designed in MVVM;

## Inside features
- Room;
- Sort items in Recyclerview;
- ViewModels;
- Random stocks generator;


## How it works
The system generates with each click of the update button, a new list containing the shares of the companies.
Each item on this list (Recyclerview) contains:
- Company's image;
- Current value of the share;
- Action code;
- High percentage;

There are two databases:
- One containing the initial values of each share, image (or logo of each company), share value and percentage;
- Another containing the values after the purchase;
 
Random values are generated on a scale of +-5.00%, and directly affect the values of each share.

After the moment of purchase, the value is fixed and stored within the purchasing database (cart_purshased). Posteriorly,
I intend to insert the sale and capital accumulation feature in the portfolio.


