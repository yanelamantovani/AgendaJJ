package edu.egg.AgendaJJ.repository;

import edu.egg.AgendaJJ.entity.Lawsuit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LawsuitRepository extends JpaRepository<Lawsuit, Integer> {
}
