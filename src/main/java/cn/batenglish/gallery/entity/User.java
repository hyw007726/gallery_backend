package cn.batenglish.gallery.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jakarta.validation.constraints.Email;
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "users")
@JsonIgnoreProperties(value = { "password" })
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    @Column(name = "nickname")
    private String nickname="";
    @Column(name = "password")
    private String password;
    @Column(name = "is_admin")
    private Boolean is_admin=false;
    @Column(name = "avatar_path")
    private String avatarPath="";
    @Column(name = "email")
    private String email;

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String url) {
        this.avatarPath = url;
    }




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(Boolean is_admin) {
        this.is_admin = is_admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", is_admin=" + is_admin +
                ", avatarPath=" + avatarPath +
                ", email='" + email + '\'' +
                '}';
    }
}





