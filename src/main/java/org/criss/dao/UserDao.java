package org.criss.dao;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;


import java.io.Serializable;
@XmlRootElement(name = "UserDao")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserDao implements Serializable {

    @XmlAttribute(name = "firstName")
    public String firstName;

    @XmlAttribute(name = "lastName")
    public String lastName;

    @XmlAttribute(name = "username")
    public String username;

    @XmlAttribute(name = "password")
    public String password;
    @XmlAttribute(name = "money")
    public Long money;



}
