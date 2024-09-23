package com.ing.brokefirm.controller.resource;

import lombok.Data;

@Data
public class CustomerResource {
    private Long id;
    private String username;
    private String password;
    private String role;
}
