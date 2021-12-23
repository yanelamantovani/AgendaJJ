package edu.egg.AgendaJJ.repository;

import edu.egg.AgendaJJ.entity.CourtUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourtUserRepository extends JpaRepository<CourtUser, Integer>{

    Optional<CourtUser> findByMail(String mail);

    boolean existsByMail(String mail);
}