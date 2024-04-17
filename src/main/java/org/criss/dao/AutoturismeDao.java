package org.criss.dao;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement(name = "AutoturismeDao")
@XmlAccessorType(XmlAccessType.FIELD)
public class AutoturismeDao implements Serializable {
    @XmlAttribute(name = "name")
    public String name;

    @XmlAttribute(name = "pieces")
    public Long pieces;

    @XmlAttribute(name = "price")
    public Long price;




}
