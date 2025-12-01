package com.lememz.nexguard;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@SuppressWarnings("unused")
@Entity
public class ValidAddress {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name="source_id")
    @JsonBackReference
    private Source source;
    private String address;
    private Type type;

    public ValidAddress() {}

    public ValidAddress(String address, Type type) {
        this.address = address;
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public enum Type { EMAIL, PHONE_NUMBER, TEMPLATE }
}
