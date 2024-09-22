package com.ing.brokefirm.controller.command;

import lombok.Data;

@Data
public class CustomerCommand {
    private String username;
    private String password;
    private String role;
}
