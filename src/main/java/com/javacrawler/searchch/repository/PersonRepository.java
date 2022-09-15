package com.javacrawler.searchch.repository;

import java.util.List;

import com.javacrawler.searchch.entity.PersonEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, String> {

}
