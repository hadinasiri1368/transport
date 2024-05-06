package org.transport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.transport.model.Person;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
