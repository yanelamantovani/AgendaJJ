package edu.egg.AgendaJJ.repository;

import edu.egg.AgendaJJ.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TrialRepository extends JpaRepository<Trial, Integer> {

    @Modifying
    @Query("UPDATE Trial t SET t.status = true WHERE t.id = :id")
    void enableTrial(@Param("id") Integer id);

    @Query(value = "SELECT * FROM trial WHERE judicial_division_id = ?", nativeQuery = true)
    List<Trial> findByJudicialDivision(Integer idJudicialDivision);

    @Query(value = "SELECT * FROM trial WHERE MONTH(date_Trial) = ?1", nativeQuery = true)
    List<Trial> findByDateTrial(Integer month);

    @Query(value = "SELECT tr.* FROM trial tr JOIN lawsuit ls on tr.lawsuit_id = ls.id WHERE ls.case_title LIKE '%' ? '%'", nativeQuery = true)
    List<Trial> findByCaseTitle(String caseTitle);
}
