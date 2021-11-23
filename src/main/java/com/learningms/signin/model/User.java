package com.learningms.signin.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;
}
