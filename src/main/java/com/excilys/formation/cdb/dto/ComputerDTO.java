package com.excilys.formation.cdb.dto;

/**
 * @author jirr
 *
 */
public class ComputerDTO {
    private int id;
    private String name;
    private String dateIntroduced;
    private String dateDiscontinued;
    private CompanyDTO manufactor;
    
    public ComputerDTO() { };

    private ComputerDTO(ComputerDTOBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.dateIntroduced = builder.dateIntroduced;
        this.dateDiscontinued = builder.dateDiscontinued;
        this.manufactor = builder.manufactor;
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

    public String getDateIntroduced() {
        return dateIntroduced;
    }

    public void setDateIntroduced(String dateIntroduced) {
        this.dateIntroduced = dateIntroduced;
    }

    public String getDateDiscontinued() {
        return dateDiscontinued;
    }

    public void setDateDiscontinued(String dateDiscontinued) {
        this.dateDiscontinued = dateDiscontinued;
    }

    public CompanyDTO getManufactor() {
        return manufactor;
    }

    public void setManufactor(CompanyDTO manufactor) {
        this.manufactor = manufactor;
    }

    @Override
    public String toString() {
        String result = "";
        result += "id: " + this.id;
        result += " {nom: " + this.name;
        result += ", date1: " + this.dateIntroduced;
        result += ", date2: " + this.dateDiscontinued;
        result += ", manufactor: " + this.manufactor.getName() + "}";
        return result;
    }

    public static class ComputerDTOBuilder {
        private int id;
        private final String name;
        private String dateIntroduced;
        private String dateDiscontinued;
        private CompanyDTO manufactor;

        public ComputerDTOBuilder(String name) {
            this.name = name;
        }

        public ComputerDTOBuilder id(int id) {
            this.id = id;
            return this;
        }

        public ComputerDTOBuilder dateIntroduced(String dateIntroduced) {
            this.dateIntroduced = dateIntroduced;
            return this;
        }

        public ComputerDTOBuilder dateDiscontinued(String dateDiscontinued) {
            this.dateDiscontinued = dateDiscontinued;
            return this;
        }

        public ComputerDTOBuilder manufactor(CompanyDTO manufactor) {
            this.manufactor = manufactor;
            return this;
        }

        public ComputerDTO build() {
            return new ComputerDTO(this);
        }
    }
}
