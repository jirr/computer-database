package com.excilys.formation.cdb.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author jirr
 *
 */
public class CompanyDTO {
    private int id;
    @NotNull
    @Size(min=1, max=30)
    @Pattern(regexp="^[\\wÀ-ÿ]+[\\wÀ-ÿ_\\-'\\+\\.\\* ]+$")
    private String name;

    public CompanyDTO() {}
    
    /**
     * @param id unique identifier of company
     * @param name Name of company
     */
    public CompanyDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        String result = "";
        result += "id: " + this.id;
        result += " {nom: " + this.name + "}";
        return result;
    }
}
