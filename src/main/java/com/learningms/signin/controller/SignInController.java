package com.learningms.signin.controller;

import com.learningms.signin.client.UserClient;
import com.learningms.signin.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class SignInController {

    private final UserClient userClient;

    @GetMapping("/signIn")
    public Mono<User> signInUser(@RequestParam String email){
        Mono<User> user = userClient.getUserByEmail(email);
        System.out.println(user);
        return user;
    }

    @GetMapping("/logIn")
    public Mono<User> logInUser(@RequestParam String email){
        Mono<User> user = userClient.getUser(email);
        System.out.println(user);
        return user;
    }

    @GetMapping("/helloWorld")
    public String getHelloWorld(){
        return "Hello-world";
    }

}
