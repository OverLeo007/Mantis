package ru.paskal.MantisManager.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Data
@NoArgsConstructor
@Table(name = "labels")
public class Label {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "label_id")
  private Integer id;

  @Column(name = "title")
  private String title;

  @Column(name = "label_info")
  private String info;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToMany(mappedBy = "labels")
  private List<Task> tasks;

  @Column(name = "label_preferences")
  @JdbcTypeCode(SqlTypes.JSON)
  private String labelPreferences;


  public Label(String title, String info, Task task) {
    this.title = title;
    this.info = info;
  }


}
