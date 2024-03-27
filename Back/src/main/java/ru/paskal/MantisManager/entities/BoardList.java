package ru.paskal.MantisManager.entities;

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
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@Table(name = "lists")
public class BoardList {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "list_id")
  private Integer id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "list_position")
  private Integer listPosition;

  @JsonIgnore
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToOne
  @JoinColumn(name = "board_id")
  private Board board;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "list")
  private List<Task> tasks;


  public BoardList(String title, Integer listPosition, Board board) {
    this.title = title;
    this.listPosition = listPosition;
    this.board = board;
  }

}
