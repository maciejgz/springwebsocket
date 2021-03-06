package pl.mg.springwebsocket.dao;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "user_region")
public class UserEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "registration_date")
    private Date registrationDate;


    public UserEntity() {
        super();
    }

    public UserEntity(String id, String name, String city, Date registrationDate) {
        super();
        this.id = id;
        this.name = name;
        this.city = city;
        this.registrationDate = registrationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "UserEntity [id=" + id + ", name=" + name + ", city=" + city + ", registrationDate=" + registrationDate + "]";
    }

}
