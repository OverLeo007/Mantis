package ru.paskal.MantisManager.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@Table(name = "comments")
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "comment_id")
  private Integer id;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "task_id")
  private Task task;

  @ManyToOne
  @EqualsAndHashCode.Exclude
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "comment_text")
  private String text;

  @Column(name = "comment_date")
  private Timestamp commentDate;

  public Comment(Task task, User user, String text, Timestamp commentDate) {
    this.task = task;
    this.user = user;
    this.text = text;
    this.commentDate = commentDate;
  }
}
