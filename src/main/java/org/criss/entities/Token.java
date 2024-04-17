package org.criss.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "token")
public class Token {
    private Long id;
    private String token;
    private Date expirationDate;


    @Id
    @SequenceGenerator(name = "tokenSeq", sequenceName = "token_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "tokenSeq")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    @Column(name = "expiration_date")
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }


}
