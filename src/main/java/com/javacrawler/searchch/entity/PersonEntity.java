package com.javacrawler.searchch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
  name = "tb_person",
  uniqueConstraints = {
    @UniqueConstraint(
      columnNames = { "name", "occupation", "address", "phone", "locationcode" }
    ),
  }
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PersonEntity {

  public PersonEntity(
    String name,
    String occupation,
    String address,
    String phone,
    String locationcode
  ) {
    this.name = name;
    this.occupation = occupation;
    this.address = address;
    this.phone = phone;
    this.locationcode = locationcode;
  }

  @Id
  @SequenceGenerator(name = "seq_person", allocationSize = 1)
  @GeneratedValue(generator = "seq_person", strategy = GenerationType.SEQUENCE)
  private long id;

  @Column(name = "name")
  private String name;

  @Column(name = "occupation")
  private String occupation;

  @Column(name = "address")
  private String address;

  @Column(name = "phone")
  private String phone;

  @Column(name = "locationcode")
  private String locationcode;
}
