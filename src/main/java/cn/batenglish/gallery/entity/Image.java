package cn.batenglish.gallery.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;


import java.sql.Timestamp;
import java.util.List;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="images")
public class Image {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="uploader_user_id", referencedColumnName="id")
    private User uploader;

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    @Column(name = "filename")
    private String filename;
    @Column(name = "caption")
    private String caption;
    @CreationTimestamp
    @Column(name = "upload_time", nullable = false, updatable = false)
    private Timestamp uploadTime;

    @OneToMany(mappedBy = "targetImage", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Timestamp getUploadTime() {
        return uploadTime;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}