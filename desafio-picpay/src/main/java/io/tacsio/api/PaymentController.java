package io.tacsio.api;

import java.math.BigDecimal;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.tacsio.api.dto.PaymentForm;
import io.tacsio.api.dto.PaymentResponse;
import io.tacsio.model.Transaction;
import io.tacsio.model.User;

@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentController {

    @POST
    @Transactional
    public Response payment(@Valid PaymentForm form) {
        User payer = form.toPayer();
        User payee = form.toPayee();
        BigDecimal value = form.value;

        Transaction transaction = payer.pay(value, payee);

        return Response.ok(new PaymentResponse(transaction)).build();
    }
}
