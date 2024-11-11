package io.tacsio.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import javax.enterprise.inject.spi.CDI;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.transaction.Transactional;
import javax.validation.constraints.Positive;
import javax.ws.rs.NotAuthorizedException;

import com.google.common.base.Preconditions;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.hibernate.annotations.CreationTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.tacsio.service.NotificationService;
import io.tacsio.service.TransactionValidatorService;
import io.tacsio.service.dto.NotificationResponse;
import io.tacsio.service.dto.TransactionValitadorResponse;

@Entity
@Table(name = "picpay_transaction")
public class Transaction extends PanacheEntityBase {

    @Id
    private UUID id;

    @PrePersist
    void generateId() {
        this.id = UUID.randomUUID();
    }

    @ManyToOne
    private User payer;

    @ManyToOne
    private User payee;

    @Positive
    private BigDecimal value;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime timestamp;

    protected Transaction() {
    }

    public Transaction(User payer, User payee, @Positive BigDecimal value) {
        Preconditions.checkArgument(payer.canPay(), "This user can not pay, only receive payments.");
        Preconditions.checkArgument(value.compareTo(BigDecimal.ZERO) > 0, "Payment value must be greater than 0.");

        this.payer = payer;
        this.payee = payee;
        this.value = value;
        this.timestamp = LocalDateTime.now();
    }

    @Transactional
    public void confirm() {
        // injected beans
        TransactionValidatorService validatorService = CDI.current()
                .select(TransactionValidatorService.class, RestClient.LITERAL).get();
        NotificationService notificationService = CDI.current().select(NotificationService.class, RestClient.LITERAL)
                .get();

        TransactionValitadorResponse validate = validatorService.validate();
        if (!validate.authorized()) {
            throw new NotAuthorizedException("Transaction not authorized.", "auth-service");
        }

        this.persist();

        CompletableFuture.runAsync(() -> {
            NotificationResponse notificationResponse = notificationService.sendNotification();
            System.out.println(notificationResponse);
            //TODO: retry or save when notification fails
        });
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
