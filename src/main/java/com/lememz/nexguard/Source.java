package com.lememz.nexguard;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Entity
public class Source {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
    private String password;
    @OneToMany(mappedBy="source", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JsonManagedReference
    private final List<ValidAddress> validAddresses = new ArrayList<>();

    public Source() {}

    public Source(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ValidAddress> getValidAddresses() {
        return validAddresses;
    }

    public void updateValidAddresses(List<ValidAddress> addresses, EntityManager em) {
        this.getValidAddresses().forEach(em::remove);
        this.getValidAddresses().clear();
        addresses.forEach(a -> a.setSource(this));
        this.getValidAddresses().addAll(addresses);
    }
}
