package org.criss.entities;

import jakarta.persistence.*;

@Entity
@Table(name ="autoturism_useri_link")
public class AutoturismUserLink {
    public Long id;
    public Autoturism autoturism;
    public User user;

    @Id
    @SequenceGenerator(name = "autoturism_useri_linkSeq", sequenceName = "autoturism_useri_link_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "autoturism_useri_linkSeq")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autoturism_id")
    public Autoturism getAutoturism() {
        return autoturism;
    }

    public void setAutoturism(Autoturism autoturism) {
        this.autoturism = autoturism;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
