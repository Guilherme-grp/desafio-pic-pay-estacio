package io.tacsio.model;

import static io.tacsio.model.UserType.DEFAULT;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "picpay_user")
public class User extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(unique = true)
    private String email;

    @CPF
    @Column(unique = true)
    private String cpf;

    @CNPJ
    @Column(unique = true)
    private String cnpj;

    @NotEmpty
    private String encryptedPassword;

    @Enumerated(EnumType.STRING)
    private UserType type;

    @OneToOne(mappedBy = "owner", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Wallet wallet;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    protected User() {
    }

    public User(UserType type, @Email String email, String document, @NotEmpty String encryptedPassword) {
        this.type = type;
        this.email = email;
        this.encryptedPassword = encryptedPassword;

        if (DEFAULT.equals(type)) {
            this.cpf = document;
        } else {
            this.cnpj = document;
        }
    }

    public boolean canPay() {
        return DEFAULT.equals(this.type);
    }

    public Transaction pay(BigDecimal value, User payee) {
        Wallet payeeWallet = payee.wallet;
        return this.wallet.transfer(value, payeeWallet);
    }

    public Long getId() {
        return id;
    }
    
}
