package io.github.iridiumcao.jpademo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    // === n-1 关系: Post -> User ===
    @ManyToOne
    @JoinColumn(name = "user_id") // 指定外键列
    private User user;

    // === n-m 关系: Post <-> Tag ===
    // 使用 @JoinTable 描述中间表
    @ManyToMany(cascade = CascadeType.PERSIST) // 通常 n-m 不用 ALL，避免删文章把标签也删了
    @JoinTable(
            name = "t_post_tag", // 中间表名
            joinColumns = @JoinColumn(name = "post_id"), // 当前对象在中间表的外键
            inverseJoinColumns = @JoinColumn(name = "tag_id") // 对方对象在中间表的外键
    )
    private List<Tag> tags = new ArrayList<>();

    public Post() {
    }

    public Post(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}