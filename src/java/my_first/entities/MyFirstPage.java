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
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jessica
 */
@Entity
@Table(name = "MYFIRSTPAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MyFirstPage.findAll", query = "SELECT m FROM MyFirstPage m WHERE m.userId.id = :userId"),
    @NamedQuery(name = "MyFirstPage.findById", query = "SELECT m FROM MyFirstPage m WHERE m.userId.id = :userId AND m.id = :id"),
    @NamedQuery(name = "MyFirstPage.findByPageNumber", query = "SELECT m FROM MyFirstPage m WHERE m.userId.id = :userId AND m.pageNumber = :pageNumber"),
    @NamedQuery(name = "MyFirstPage.findByEventDate", query = "SELECT m FROM MyFirstPage m WHERE m.userId.id = :userId AND m.eventDate = :eventDate"),
    @NamedQuery(name = "MyFirstPage.findByTitle", query = "SELECT m FROM MyFirstPage m WHERE m.userId.id = :userId AND m.title = :title"),
    @NamedQuery(name = "MyFirstPage.findByImgName", query = "SELECT m FROM MyFirstPage m WHERE m.userId.id = :userId AND m.imgName = :imgName")})
public class MyFirstPage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAGENUMBER")
    private int pageNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "EVENTDATE")
    private String eventDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "TITLE")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "IMGNAME")
    private String imgName;
    @Basic(fetch=FetchType.LAZY)
    @NotNull
    @Lob
    @Column(name = "IMG")
    @XmlTransient
    private byte[] img;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 32700)
    @Column(name = "CAPTION")
    private String caption;
    @JoinColumn(name = "USERID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private MyFirstUser userId;

    public MyFirstPage() {
    }

    public MyFirstPage(Integer id) {
        this.id = id;
    }

    public MyFirstPage(Integer id, int pageNumber, String eventDate, String title, String imgName, byte[] img, String caption) {
        this.id = id;
        this.pageNumber = pageNumber;
        this.eventDate = eventDate;
        this.title = title;
        this.imgName = imgName;
        this.img = img;
        this.caption = caption;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public MyFirstUser getUserId() {
        return userId;
    }

    public void setUserId(MyFirstUser userId) {
        this.userId = userId;
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
        if (!(object instanceof MyFirstPage)) {
            return false;
        }
        MyFirstPage other = (MyFirstPage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "my_first.entities.MyFirstPage[ id=" + id + " ]";
    }
    
}
