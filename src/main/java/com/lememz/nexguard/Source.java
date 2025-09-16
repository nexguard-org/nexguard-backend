package com.lememz.nexguard;

import jakarta.persistence.*;

import java.util.List;

@SuppressWarnings("unused")
@Entity
public class Source {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(mappedBy="source")
    private List<ValidAddress> validAddress;

    public Source() {}

    public Source(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ValidAddress> getValidAddresses() {
        return validAddress;
    }

    public void setName(String name) {
        this.name = name;
    }
}
