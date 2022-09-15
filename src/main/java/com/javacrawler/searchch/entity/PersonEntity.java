package com.javacrawler.searchch.entity;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "tb_person", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "name", "occupation", "address", "phone", "locationcode" }) })
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class PersonEntity {
    public PersonEntity(String text, String text2, String text3, String text4, String text5) {
        this.name = text;
        this.occupation = text2;
        this.address = text3;
        this.phone = text4;
        this.locationcode = text5;
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