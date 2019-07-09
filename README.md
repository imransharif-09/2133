# Account Transfer API
Transfer money from one account to another. 
Initially there are two accounts loaded at startup. (Account Numbers : XX123, YY456).
You can add further accounts. 
List all the accounts. 
Get balance of any account.
You can also perform balance transfer from one account to another.

Intially multi currency is not supported for this API considering same currency will be used for from and to accounts.
Multi currency support will be added later.

# Run the project
You can run the project using the command below. Just go the the main folder and run it. 
This command is for MAC. For Windows you have to use # gradlew run
 - ./gradlew run


# Following are the end points:
# List Accounts - GET
Fetch all the accounts from database
http://localhost:8080/list-accounts

# Create Account - POST
Create account. You have to provide account number and initial balance.
http://localhost:8080/create-account
 - accountNumber
 - initialBalance

# Transfer Money - POST
Performs actual transfer from one account to another.
http://localhost:8080/transfer-money
 - fromAccountNumber
 - toAccountNumber
 - transferAmount

# Get Balance - GET
Get Balance for an account.
http://localhost:8080/get-balance?accountNumber=<Provide Account Number>

Thanks
