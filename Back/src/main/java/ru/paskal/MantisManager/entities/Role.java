package ru.paskal.MantisManager.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Deprecated
//@Entity
//@NoArgsConstructor
//@ToString
//@EqualsAndHashCode
//@Setter
//@Getter
//@Table(name = "roles")
//public class Role {

//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  @Column(name = "role_id")
//  private Integer id;
//
//  @ToString.Exclude
//  @EqualsAndHashCode.Exclude
//  @ManyToOne
//  @JoinColumn(name = "board_id")
//  private Board board;
//
//  @Column(name = "role_name")
//  private String roleName;
//
//  @Column(name = "role_permissions")
//  private String rolePermissions;
//
//  @ToString.Exclude
//  @EqualsAndHashCode.Exclude
//  @ManyToMany(mappedBy = "roles")
//  private List<User> users;
//
//  public Role(Board board, String roleName, String rolePermissions) {
//    this.board = board;
//    this.roleName = roleName;
//    this.rolePermissions = rolePermissions;
//  }

//}


