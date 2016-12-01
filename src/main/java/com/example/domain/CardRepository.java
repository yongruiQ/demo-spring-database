package com.example.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Louie Qin on 01/12/16.
 */
public interface CardRepository extends JpaRepository<Card, Long> {
}
