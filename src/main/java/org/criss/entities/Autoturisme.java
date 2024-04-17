package org.criss.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "autoturisme")
public class Autoturisme {
    private Long id;
    private String name;
    private Long pieces;
    private Long price;

    @Id
    @SequenceGenerator(name = "autoturismeSeq", sequenceName = "autoturisme_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "autoturismeSeq")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "pieces")
    public Long getPieces() {
        return pieces;
    }

    public void setPieces(Long pieces) {
        this.pieces = pieces;
    }

    @Column(name = "price")
    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }


}
