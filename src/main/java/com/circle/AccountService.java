package com.circle;

import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.Query;

// import com.circle.dao.impl.AccountDAOImpl;
import com.circle.model.Account;
import com.circle.model.AccountMapper;
import com.circle.model.InsertMapper;
import com.circle.model.InsertResult;
import com.circle.model.TransferRequest;
import com.fasterxml.jackson.annotation.JsonFormat;



@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/account")
public class AccountService {
    private final DBI dbi;
    private final Handle handle;
    private final String CREATE_ACC_QUERY = "INSERT INTO accounts (name, balance) VALUES (:name,:balance) returning *";
    private final String GET_ACC_QUERY = "SELECT * FROM accounts WHERE accnumber=:accNumber";
    private final String UPDATE_BAL_QUERY_SENDER = "UPDATE accounts SET balance = :amnt WHERE accnumber = :accNumber returning *";
    private final String UPDATE_BAL_QUERY_RECEIVER  = "UPDATE accounts SET balance = :amnt WHERE accnumber = :accNumber returning *";

    // private final AccountDAO accountDAO;

    public AccountService(DBI dbi) {
        this.dbi = dbi;
        handle = dbi.open();
        // accountDAO = new AccountDAOImpl();
    }

    @Path("/{accNumber}")
    @GET
    public Response getAccount(@PathParam("accNumber") long accNumber) {
        // TODO Use Logger instead of console output
        System.out.println("LOG: GET Account received. Number - " + accNumber);

        Account account = handle.createQuery(GET_ACC_QUERY)
            .bind("accNumber", accNumber)
            .map(new AccountMapper())
            .first();

        if(account == null) {
            System.out.println("Account not found for " + accNumber);
            return Response
                .ok()
                .type(MediaType.APPLICATION_JSON)
                .entity("Account not found")
                .build();
        } else {
            // Return success response
            return Response
                .ok()
                .type(MediaType.APPLICATION_JSON)
                .entity(account)
                .build();
        }
    }

    @Path("/create")
    @POST
    public Response createAccount(Account account) {
        System.out.println("Create Account called.");
        System.out.println("Account Name: " + account.getName());
        System.out.println("Account Bal: " + account.getBalance());

        Account accountResp = handle.createQuery(CREATE_ACC_QUERY)
            .bind("name", account.getName())
            .bind("balance", account.getBalance())
            .map(new AccountMapper())
            .first();
        
        return Response
                .ok()
                .type(MediaType.APPLICATION_JSON)
                .entity(accountResp)
                .build();
    }

    @Path("/transfer")
    @POST
    public Response makeTransaction(TransferRequest request){

        System.out.println("You are about to make a transaction");

        //Get Sender Balance to check if they have enough funds

        Account fromAccount = handle.createQuery(GET_ACC_QUERY)
            .bind("accNumber", request.getFromAccountNumber())
            .map(new AccountMapper())
            .first();

        Account toAccount = handle.createQuery(GET_ACC_QUERY)
            .bind("accNumber", request.getToAccountNumber())
            .map(new AccountMapper())
            .first();

        if(fromAccount == null || toAccount == null) {
            // TODO Tweak error message
            System.out.println("Account not found for either the Sender or Reciever");
        } else if(request.getAmount().compareTo(BigDecimal.ZERO) > 0){
            // Check if sender has Sufficient Balance
            if(fromAccount.getBalance().compareTo(request.getAmount()) >= 0){
                //Commit Transaction
                //Sender
                BigDecimal fromBal = fromAccount.getBalance().subtract(request.getAmount());
                fromAccount = handle.createQuery(UPDATE_BAL_QUERY_SENDER)
                    .bind("amnt", fromBal)
                    .bind("accNumber", fromAccount.getAccountNumber())
                    .map(new AccountMapper())
                    .first();
                
                //Receiver
                BigDecimal toBal = toAccount.getBalance().add(request.getAmount());
                toAccount = handle.createQuery(UPDATE_BAL_QUERY_RECEIVER)
                    .bind("amnt", toBal)
                    .bind("accNumber", toAccount.getAccountNumber())
                    .map(new AccountMapper())
                    .first();


                // Assert transaction success
                if (fromAccount.getBalance().compareTo(fromBal) == 0 && toAccount.getBalance().compareTo(toBal) == 0) {

                    System.out.println("Transcation completed successfully.");
                    return Response
                        .ok()
                        .type(MediaType.APPLICATION_JSON)
                        .entity("Transcation completed successfully.")
                        .build();
                }
            }
        }
        // Return failure response

        System.out.println("Transcation failed.");

        return Response
            .ok()
            .type(MediaType.APPLICATION_JSON)
            .entity("Transaction failed.")
            .build();
    }
}
