## List of APIs used are as follows 
1. Flutterwave GET ALL banks API.
2. Flutterwave Validate Account number API.
3. Flutterwave Create SubAccounts API.
4. Flutterwave Android SDK.

### Explanation
Jumga store is a built from scratch mini multi-vendor e-commerce Android application built within the 3weeks of Flutterwave challenge.
The application allows merchants to create an account, upload products and those products will appear in the home screen of their dashboard
Along side the home page of the app (User View).
When a merchant creates an account the create sub account api is called to sign up user both in the database as a merchant and in my flutterwave dashboard as a SubAccount.
A SubAccountID is return from the api upon creation, and this is added along side the merchant details and saved to the database, each merchant is assigned a SubAccountID upon creation of merchant account.
I had to make api calls because the rave-android sdk has limitations, I do not want to hardcode the merchant ID and updating my code/app each time a merchant signs up in the platform.

After successful creation of account the user is prompted by a dialog box to pay some fees for shop approval.
After payment the user is not able to login until the admin approves his shop on jumga as seen below.

![image](https://drive.google.com/uc?export=view&id=1183RNPLjy8idXdHISHzOyf1x_oKMuYro)

![image](https://drive.google.com/uc?export=view&id=11FxNGRs_iFoBMWT3dET-roeZlQs4bE3L)

When shop is approved and a seller/merchant upload goods to the application, each goods is uploaded with the merchant ID of the merchant who uploaded it.so at check out I loop through the cart items and check which merchant is present in the cart get the SubAccount IDs of those merchants, instantiate the ravePay SubAccount to an Arraylist 
Pass the the 3 parameters which is ID, splitType(flat) splitValue(itemPrice × 0.1) and pass is to rave pay standard implementation class name (subAccount) from the Android sdk.

Jumga dispatch rider is hardcoded since jumga has only one delivery company he does business with all I did is add the price of delivery, the split value and added it to the ArrayList I created earlier. Somthing like list.add(deliveryAgent); list.add(SubAccounts);





