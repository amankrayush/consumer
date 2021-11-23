package com.learningms.signin.client;

import com.learningms.signin.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class UserClient {

    public WebClient webClient = WebClient.builder().build();

    public Mono<User> getUserByEmail(String email) {
        try {
            String  uri = "http://localhost:8080/getUser?email=" + email;
            Mono<User> userMono = webClient.get().uri(uri).retrieve().bodyToMono(User.class);
            return userMono;
        } catch (WebClientResponseException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Mono<User> getUser(String email) {
        try {
            String  uri = "http://localhost:8080/getUser/" + email;
            Mono<User> userMono = webClient.get().uri(uri).retrieve().bodyToMono(User.class);
            return userMono;
        } catch (WebClientResponseException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
