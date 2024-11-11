package io.tacsio.service;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.tacsio.service.dto.TransactionValitadorResponse;

@ApplicationScoped
@RegisterRestClient(configKey = "transaction-validator")
public interface TransactionValidatorService {

    @GET
    TransactionValitadorResponse validate();
}
