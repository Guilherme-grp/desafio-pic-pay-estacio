package io.tacsio.fixtures;

import java.math.BigDecimal;
import java.util.List;

import io.tacsio.api.dto.PaymentForm;
import io.tacsio.model.User;
import io.tacsio.model.UserType;
import io.tacsio.service.dto.TransactionValitadorResponse;

public class PaymentFixtures {

  public static TransactionValitadorResponse authorizedResponse() {
    TransactionValitadorResponse response = new TransactionValitadorResponse();
    response.message = "Autorizado";

    return response;
  }

  public static TransactionValitadorResponse unauthorizedResponse() {
    TransactionValitadorResponse response = new TransactionValitadorResponse();
    response.message = "Denied";

    return response;
  }

  public static PaymentForm of(Long idPayer, Long idPayee, double value) {
    PaymentForm form = new PaymentForm();
    form.payer = idPayer;
    form.payee = idPayee;
    form.value = BigDecimal.valueOf(value);

    return form;
  }

  public static PaymentForm userToUser(double ammount) {

    List<User> users = User.list("type", UserType.DEFAULT);
    if (users.size() < 2) {
      throw new IllegalStateException("There is no 2 users registered in database to make this payment.");
    }

    long idPayer = users.get(0).getId();
    long idPayee = users.get(1).getId();

    return of(idPayer, idPayee, ammount);
  }

  public static PaymentForm userToShopkeeper(double ammount) {
    User client = User.find("type", UserType.DEFAULT).firstResult();
    User shoopkeeper = User.find("type", UserType.SHOPKEEPER).firstResult();

    return of(client.getId(), shoopkeeper.getId(), ammount);
  }

  public static PaymentForm shopkeeperToUser(double ammount) {
    User client = User.find("type", UserType.DEFAULT).firstResult();
    User shoopkeeper = User.find("type", UserType.SHOPKEEPER).firstResult();

    return of(shoopkeeper.getId(), client.getId(), ammount);
  }

  public static PaymentForm shopkeeperToShopkeeper(double ammount) {
    List<User> shoopkeepers = User.list("type", UserType.SHOPKEEPER);
    if (shoopkeepers.size() < 2) {
      throw new IllegalStateException("There is no 2 shoopkeepers registered in database to make this payment.");
    }

    return of(shoopkeepers.get(0).getId(), shoopkeepers.get(1).getId(), ammount);
  }

}
