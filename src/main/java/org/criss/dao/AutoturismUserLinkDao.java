package org.criss.dao;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement(name = "AutoturismUserLinkDao")
@XmlAccessorType(XmlAccessType.FIELD)
public class AutoturismUserLinkDao implements Serializable {

    @XmlAttribute(name = "chassis_series")
    public String chassisSeries;

    @XmlAttribute(name = "name")
    public String name;
}
