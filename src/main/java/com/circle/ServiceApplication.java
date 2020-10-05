package com.circle;

import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import com.circle.AccountService;

public class ServiceApplication extends Application<ServiceConfiguration> {
    private final String CREATE_ACC_TABLE_QUERY = "CREATE table if not exists accounts (accnumber serial PRIMARY KEY, name varchar(50) NOT NULL, balance money NOT NULL);";
    public static void main(String[] args) throws Exception {
        new ServiceApplication().run(args);
    }

    @Override
    public void run(ServiceConfiguration config, Environment env) throws Exception {
        // Get a database handle
        final DBIFactory factory = new DBIFactory();
        final DBI dbi = factory.build(env, config.getDatabase(), "interview");

        // Get DB handle and set up accounts DB if it doesn't exist
        Handle handle = dbi.open();

        handle.execute(CREATE_ACC_TABLE_QUERY);

        // Register our sole resource
        env.jersey().register(new TimeResource(dbi));
        // Register new Account Service
        env.jersey().register(new AccountService(dbi));
    }
}
