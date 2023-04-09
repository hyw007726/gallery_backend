package cn.batenglish.gallery.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Column(name = "is_like")
    private Boolean isLike;


    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="sender_user_id", referencedColumnName="id")
    private User sender;

    public Boolean getIsLike() {
        return isLike;
    }

    public void setIsLike(Boolean like) {
        isLike = like;
    }

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_image_id")
    @JsonBackReference
    private Image targetImage;


    public void setId(Integer id) {
        this.id = id;
    }

    public Image getTargetImage() {
        return targetImage;
    }

    public void setTargetImage(Image targetImage) {
        this.targetImage = targetImage;
    }

    public Timestamp getPost_time() {
        return post_time;
    }

    public void setPost_time(Timestamp post_time) {
        this.post_time = post_time;
    }
//
//    public Image getImage() {
//        return image;
//    }
//
//    public void setImage(Image image) {
//        this.image = image;
//    }

    @CreationTimestamp
    @Column(name = "post_time", nullable = false, updatable = false)
    private Timestamp post_time;

    @UpdateTimestamp
    @Column(name = "update_time")
    private Timestamp updateTime;

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public Integer getId() {
        return id;
    }

//    public Integer getImageId() {
//        return imageId;
//    }
//
//    public void setImageId(Integer imageId) {
//        this.imageId = imageId;
//    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", isLike=" + isLike +
                ", sender=" + sender +
                ", content='" + content + '\'' +
                ", targetImage=" + targetImage +
                ", post_time=" + post_time +
                ", updateTime=" + updateTime +
                '}';
    }
}
