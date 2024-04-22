package de.qytera.qtaf.apitesting.restassured.mixed.Entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String username;
    private String password;
}
