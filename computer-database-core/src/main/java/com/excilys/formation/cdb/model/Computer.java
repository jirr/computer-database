package com.excilys.formation.cdb.model;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * @author jirr
 *
 */
@Entity
@Table(name="computer")
public class Computer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="introduced")
    private LocalDate dateIntroduced;
    @Column(name="discontinued")
    private LocalDate dateDiscontinued;
    @ManyToOne(optional = true)
    @JoinColumn(name="company_id")
    private Company manufactor;

    public Computer() {}

    private Computer(ComputerBuilder builder) {
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

    public Optional<LocalDate> getDateIntroduced() {
        return Optional.ofNullable(dateIntroduced);
    }

    public void setDateIntroduced(LocalDate dateIntroduced) {
        this.dateIntroduced = dateIntroduced;
    }

    public Optional<LocalDate> getDateDiscontinued() {
        return Optional.ofNullable(dateDiscontinued);
    }

    public void setDateDiscontinued(LocalDate dateDiscontinued) {
        this.dateDiscontinued = dateDiscontinued;
    }

    public Optional<Company> getManufactor() {
        return Optional.ofNullable(manufactor);
    }

    public void setManufactor(Company manufactor) {
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

    public static class ComputerBuilder {
        private int id;
        private final String name;
        private LocalDate dateIntroduced;
        private LocalDate dateDiscontinued;
        private Company manufactor;

        public ComputerBuilder(String name) {
            this.name = name;
        }

        public ComputerBuilder id(int id) {
            this.id = id;
            return this;
        }

        public ComputerBuilder dateIntroduced(LocalDate dateIntroduced) {
            this.dateIntroduced = dateIntroduced;
            return this;
        }

        public ComputerBuilder dateDiscontinued(LocalDate dateDiscontinued) {
            this.dateDiscontinued = dateDiscontinued;
            return this;
        }

        public ComputerBuilder manufactor(Company manufactor) {
            this.manufactor = manufactor;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }
    }
}
