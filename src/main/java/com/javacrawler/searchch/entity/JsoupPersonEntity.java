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
  name = "tb_person_jsoup",
  uniqueConstraints = {
    @UniqueConstraint(columnNames = { "name", "locationcode" }),
  }
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class JsoupPersonEntity {

  public JsoupPersonEntity(String text, String string) {}

  @Id
  @SequenceGenerator(name = "jsoup_seq_person", allocationSize = 1)
  @GeneratedValue(
    generator = "jsoup_seq_person",
    strategy = GenerationType.SEQUENCE
  )
  private long id;

  @Column(name = "name")
  private String name;

  @Column(name = "locationcode")
  private String locationcode;
}
