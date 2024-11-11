package io.tacsio.api.dto;

import io.tacsio.model.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.math.BigDecimal;

public class PaymentForm {
    @Positive(message = "Payment value must be greater than 0.")
    public BigDecimal value;
    @NotNull
    public Long payer;
    @NotNull
    public Long payee;

    public PaymentForm() {
    }

    public User toPayer() {
        return User.findById(this.payer);
    }

    public User toPayee() {
        return User.findById(this.payee);
    }
}
