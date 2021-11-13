package ilia.nemankov.controller;

import fish.payara.ejb.rest.client.RemoteEJBContextFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class ContextProviderImpl implements ContextProvider {

    public Context getContext() throws NamingException {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, RemoteEJBContextFactory.class.getName());
        props.put(Context.PROVIDER_URL, System.getenv("LAB1_PROVIDER_URL"));

        return new InitialContext(props);
    }

}
