package com.cffex.demo.bean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "T_TODO")
public class  ${tableName?cap_first}{

@Id
@Column(name = "id")
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id = null;

@Size(min = 2)
@Column(name = "title", nullable = false, length = 255)
private String title;

@Size(max = 250)
@Column(name = "content", nullable = false, length = 255)
private String content;

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
public String getContent() {
return content;
}
public void setContent(String content) {
this.content = content;
}
}