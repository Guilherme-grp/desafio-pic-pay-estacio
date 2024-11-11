package io.tacsio.model;

import com.google.common.base.Preconditions;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.Positive;

import java.math.BigDecimal;

@Entity
@Table(name = "picpay_wallet")
public class Wallet extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;

    @OneToOne
    private User owner;

    protected Wallet() {
    }

    public Wallet(BigDecimal balance, User owner) {
        this.balance = balance;
        this.owner = owner;
    }

    @Transactional
    public void withdraw(BigDecimal value) {
        // don't accepts a value greater than balance
        Preconditions.checkArgument(balance.compareTo(value) > -1, "Insufficient money to complete this transaction.");

        this.balance = balance.subtract(value);
        this.persist();
    }

    @Transactional
    public void deposit(BigDecimal value) {
        // don't accepts negative values
        Preconditions.checkArgument(value.compareTo(BigDecimal.ZERO) > 0, "You can only increment positive values.");

        this.balance = balance.add(value);
        this.persist();
    }

    @Transactional
    public Transaction transfer(@Positive BigDecimal value, Wallet payeeWallet) {
        Preconditions.checkArgument(this.owner.canPay(), "This user can not pay, only receive payments.");
        Preconditions.checkArgument(value.compareTo(BigDecimal.ZERO) > 0, "Payment value must be greater than 0.");

        this.withdraw(value);
        payeeWallet.deposit(value);

        Transaction transaction = new Transaction(this.owner, payeeWallet.owner, value);
        transaction.confirm();

        return transaction;
    }
}
