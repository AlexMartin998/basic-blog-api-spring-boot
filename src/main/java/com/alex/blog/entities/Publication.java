package com.alex.blog.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;



@Entity
// @Table(name = "publications", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}) // title unique
@Table(name = "publications")
public class Publication implements Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String title;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private String content;


  @OneToMany(mappedBy = "publication", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference // establecemos la serializacion en 1 direccion para evitar el loop infinoto SIN ignorar los comments
  private Set<Comment> comments = new HashSet<>();
  



  public Publication() {}




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

  public Set<Comment> getComments() {
    return comments;
  }

  public void setComments(Set<Comment> comments) {
    this.comments = comments;
  }



// Eclipse pide esto, pero vscode no lo sugiere
  private static final long serialVersionUID = 1L;

}
