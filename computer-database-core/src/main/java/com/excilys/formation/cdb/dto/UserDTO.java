package com.excilys.formation.cdb.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDTO {

    private int id;
    @NotNull
    @Size(min=1, max=30)
    @Pattern(regexp="^[\\wÀ-ÿ]+[\\wÀ-ÿ_\\-'\\+\\.\\* ]+$")
    private String login;
    @NotNull
    @Size(min=4, max=30)
    @Pattern(regexp="^[\\wÀ-ÿ]+[\\wÀ-ÿ_\\-'\\+\\.\\* ]+$")
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
