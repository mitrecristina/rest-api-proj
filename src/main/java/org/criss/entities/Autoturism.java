package org.criss.entities;

import jakarta.persistence.*;

@Entity
@Table(name ="autoturism")
public class Autoturism {

    private Long id;
    private String chassisSeries;
    private Autoturisme autoturisme;

    @Id
    @SequenceGenerator(name = "autoturismSeq", sequenceName = "autoturism_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "autoturismSeq")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Column(name="chassis_series")
    public String getChassisSeries() {
        return chassisSeries;
    }

    public void setChassisSeries(String chassisSeries) {
        this.chassisSeries = chassisSeries;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="autoturisme_id")
    public Autoturisme getAutoturisme() {
        return autoturisme;
    }

    public void setAutoturisme(Autoturisme autoturisme) {
        this.autoturisme = autoturisme;
    }


}
