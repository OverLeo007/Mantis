package ru.paskal.MantisManager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@Table(name = "boards")
public class Board {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "board_id")
  private Integer id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "last_edit")
  private Timestamp lastEdit;

  @JsonIgnore
  @ManyToMany(mappedBy = "boards")
  private List<User> users;

//  @JsonIgnore
//  @OneToMany(mappedBy = "board")
//  private List<Role> roles;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "board")
  private List<BoardList> lists;

  public Board(String title, Timestamp lastEdit) {
    this.title = title;
    this.lastEdit = lastEdit;
  }

}
