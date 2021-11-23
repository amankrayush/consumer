package com.learningms.signin.contract;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.jayway.jsonpath.JsonPath;

import io.pactfoundation.consumer.dsl.LambdaDsl;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "User", port = "1234")
public class UserApi {

    @Pact(consumer = "SignIn")
    public RequestResponsePact createPact(PactDslWithProvider builder) {

        Map<String, String> responseHeader = new HashMap<>();
        responseHeader.put("Content-Type", "application/json");

        return builder
                .given("user exists")
                .uponReceiving("user exists")
                .method(HttpMethod.GET.name())
                .path("/getUser/rohan@gmail.com")
                //.pathFromProviderState("/getUser/{email}", "/getUser/rohan@gmail.com")
                .willRespondWith()
                .headers(responseHeader)
                .status(HttpStatus.OK.value())
                .body(LambdaDsl.newJsonBody((object) -> {
                    object.stringType("firstname", "rohan");
                    object.stringType("lastname", "rohan");
                    object.stringType("email", "rohan@gmail.com");
                }).build())
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "createPact")
    public void testValidUserFromProvider(MockServer mockServer) throws IOException {
        int expectedStatusCode = HttpStatus.OK.value();
        String expectedName = "rohan";
        String expectedEmail = "rohan@gmail.com";

        HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/getUser/rohan@gmail.com")
                .execute()
                .returnResponse();
        int actualStatusCode = httpResponse.getStatusLine().getStatusCode();
        String actualName = JsonPath.read(httpResponse.getEntity().getContent(), "$.firstname").toString();
        String actualEmail = JsonPath.read(httpResponse.getEntity().getContent(), "$.email").toString();

        assertThat(actualStatusCode).isEqualTo(expectedStatusCode);
        assertThat(actualName).isEqualTo(expectedName);
        assertThat(actualEmail).isEqualTo(expectedEmail);
    }

}
