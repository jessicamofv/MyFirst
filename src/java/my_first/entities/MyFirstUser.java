/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my_first.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jessica
 */
@Entity
@Table(name = "MYFIRSTUSER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MyFirstUser.findAll", query = "SELECT m FROM MyFirstUser m"),
    @NamedQuery(name = "MyFirstUser.findById", query = "SELECT m FROM MyFirstUser m WHERE m.id = :id"),
    @NamedQuery(name = "MyFirstUser.findByFirstName", query = "SELECT m FROM MyFirstUser m WHERE m.firstName = :firstName"),
    @NamedQuery(name = "MyFirstUser.findByLastName", query = "SELECT m FROM MyFirstUser m WHERE m.lastName = :lastName"),
    @NamedQuery(name = "MyFirstUser.findByUsername", query = "SELECT m FROM MyFirstUser m WHERE m.username = :username"),
    @NamedQuery(name = "MyFirstUser.findByPassword", query = "SELECT m FROM MyFirstUser m WHERE m.password = :password")})
public class MyFirstUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "FIRSTNAME")
    private String firstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "LASTNAME")
    private String lastName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "PASSWORD")
    private String password;

    public MyFirstUser() {
    }

    public MyFirstUser(Integer id) {
        this.id = id;
    }

    public MyFirstUser(Integer id, String firstName, String lastName, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MyFirstUser)) {
            return false;
        }
        MyFirstUser other = (MyFirstUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "my_first.entities.MyFirstUser[ id=" + id + " ]";
    }
    
}