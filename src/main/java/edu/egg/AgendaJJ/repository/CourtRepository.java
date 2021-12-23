package edu.egg.AgendaJJ.repository;

import edu.egg.AgendaJJ.entity.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourtRepository extends JpaRepository <Court, Integer> {
}
