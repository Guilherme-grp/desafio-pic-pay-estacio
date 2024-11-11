package io.tacsio.service.dto;

public class TransactionValitadorResponse {
    public String message;

    public TransactionValitadorResponse() {
    }

    public boolean authorized() {
        return "Autorizado".equalsIgnoreCase(message);
    }
}
