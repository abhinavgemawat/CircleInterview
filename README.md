## Checking your environment

Make sure you have the following prerequisites installed:
  - [Java 8 JDK & JRE](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html)
  - [Maven](https://maven.apache.org/install.html)
  - [Docker](https://docs.docker.com/install/)

Make sure you do not have anything running on ports 5432 or 8080.  Once you have done so, start the database with:
```bash
$ ./start_db.sh
```

Then, either use your IDE to build and run the program using main class `ServiceApplication.java`, or build and run it in a new terminal window:
```bash
$ mvn install
$ java -jar "./target/platform-pair-envcheck-0.0.1-SNAPSHOT.jar" server configuration.yml
```

Once you do that, the program will create a new table named "accounts" in the PSQL databse "interview".

You can create a new user by running the following curl command in a new terminal window:
```
$ curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"name":"xyz","balance":abc}' \
  http://localhost:8080/account/create
```

You can get the account details of any account by running the following curl command and replacing the [Account Number] for whichever account you want to see the details for:
```
$ curl --header "Content-type: application/json" \
  --request GET \
  http://localhost:8080/account/[Account Number]   
```

To make any transactions, you need to replace [SENDER ACCOUNT NUMBER] with the sender account number, [RECEIVER ACCOUNT NUMBER] with the receiver account number and [AMOUNT TO BE TRANSFERRED] with the amount to be transferred:
```
$ curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"fromAccountNumber":[SENDER ACCOUNT NUMBER],"toAccountNumber":[RECEIVER ACCOUNT NUMBER],"amount":[AMOUNT TO BE TRANSFERRED]}' \
  http://localhost:8080/account/transfer   
```




