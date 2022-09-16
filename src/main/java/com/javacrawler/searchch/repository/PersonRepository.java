package com.javacrawler.searchch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javacrawler.searchch.entity.PersonEntity;

public interface PersonRepository extends JpaRepository<PersonEntity, String> {

}
