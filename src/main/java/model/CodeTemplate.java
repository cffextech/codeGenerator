package model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lzy on 2016/9/24.
 */
public class CodeTemplate implements Serializable {
    /*
    * TODO-upgrade 版本管理
    * version:0.0.1
    * */
    private static final long serialVersionUID=1L;

    private int id;
    private String name;
    private String author;
    private String description;
    private Date createTime;
    private Date updateTime;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
