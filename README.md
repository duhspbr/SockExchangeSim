# Stock Exchange App Simulator

APP (maybe a game later?) Developed to test some functions within the Recyclerview and Android Room.

## Objective

Simulate a stock exchange app that makes fictitious and random stock purchases.

## App structure
- Developed in Java;
- Database MYSql;
- Designed in MVVM;
- Relative Layout + CardView + ScrollView and Vertifical ScrollView;

## Inside features
- Room;
- Sort (best to worst) items in Recyclerview;
- ViewModels;
- Random stocks generator;
- Max and Min values (Vertical ScrollView).


## How it works
The system generates with each click of the update button, a new list containing the shares of the companies.
Each item on this list (Recyclerview) contains:
- Company's image;
- Current value of the share;
- Stock code;
- Percentage (Random range +-5.0);

![sortgif](https://user-images.githubusercontent.com/42584849/114437502-04585280-9b9d-11eb-8fdb-f51721929f86.gif)


There are two databases:
- One containing the initial values of each share, image (or logo of each company), share value and percentage;
- Another containing the values after the purchase;
 
Random values are generated on a scale of +-5.00%, and directly affect the values of each share.

After the moment of purchase, the value is fixed and stored within the purchasing database (cart_purshased). Posteriorly,
I intend to insert the sale and capital accumulation feature in the portfolio.


Sorting gif:
![gif1](https://user-images.githubusercontent.com/42584849/114437539-10dcab00-9b9d-11eb-99cf-fd79e35056ff.gif)

Buy
Popup:
![gifbuy](https://user-images.githubusercontent.com/42584849/114438567-4df56d00-9b9e-11eb-850a-e6ce767581d0.gif)


Any suggest, let me know! :)


