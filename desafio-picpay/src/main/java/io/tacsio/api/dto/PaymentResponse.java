package io.tacsio.api.dto;

import java.time.LocalDateTime;

import io.tacsio.model.Transaction;

public class PaymentResponse {

	public final String id;
	public final LocalDateTime timestamp;

	public PaymentResponse(Transaction transaction) {
		this.id = transaction.getId().toString();
		this.timestamp = transaction.getTimestamp();
	}

}
