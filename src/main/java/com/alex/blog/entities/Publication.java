package com.alex.blog.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
// @Table(name = "publications", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}) // title unique
@Table(name = "publications")
public class Publication {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)   // auto-incremental in MySQL
  private Long id;

  @Column(nullable = false, unique = true)
  private String title;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private String content;



  public Publication() {}

  public Publication(Long id, String title, String description, String content) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.content = content;
  }



  // setters & getters
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  


}
