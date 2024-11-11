package io.tacsio.integration;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import io.tacsio.api.dto.PaymentForm;
import io.tacsio.fixtures.PaymentFixtures;
import io.tacsio.service.NotificationService;
import io.tacsio.service.TransactionValidatorService;
import io.tacsio.service.dto.NotificationResponse;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
public class ShopkeeperPaymentTest {

  @InjectMock
  @RestClient
  NotificationService notificationService;

  @InjectMock
  @RestClient
  TransactionValidatorService transactionValidatorService;

  @BeforeEach
  void setup() {
    Mockito.when(notificationService.sendNotification()).thenReturn(new NotificationResponse());
    Mockito.when(transactionValidatorService.validate()).thenReturn(PaymentFixtures.authorizedResponse());
  }

  @Test
  @DisplayName("Shoopkeepers should receive payments from users.")
  void receiveFromUser() {
    PaymentForm request = PaymentFixtures.userToShopkeeper(100.0);

    given().when().contentType(ContentType.JSON).body(request).post("/transaction").then().log().all().statusCode(200)
        .body("id", notNullValue(), "timestamp", notNullValue());
  }

  @Test
  @DisplayName("Shopkeepers should not make payments, only receive.")
  void shoopkeeperInvalidPayment() {
    
    PaymentForm request1 = PaymentFixtures.shopkeeperToShopkeeper(100.0);
    PaymentForm request2 = PaymentFixtures.shopkeeperToUser(100.0);

    expect()
      .statusCode(400)
      .body("error", CoreMatchers.is("This user can not pay, only receive payments."))
    .given().when().contentType(ContentType.JSON)
      .body(request1)
      .post("/transaction");

    expect()
      .statusCode(400)
      .body("error", CoreMatchers.is("This user can not pay, only receive payments."))
    .given().when().contentType(ContentType.JSON)
      .body(request2)
      .post("/transaction");
  }
}
