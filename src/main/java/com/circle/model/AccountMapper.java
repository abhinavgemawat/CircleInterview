package com.circle.model;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper implements ResultSetMapper<Account> {

    @Override
    public Account map(int arg0, ResultSet arg1, StatementContext context) throws SQLException {
        return new Account(arg1.getInt("accnumber"), arg1.getString("name"), arg1.getBigDecimal("balance"));
    }
    
}
