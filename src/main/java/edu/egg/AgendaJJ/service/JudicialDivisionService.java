package edu.egg.AgendaJJ.service;

import edu.egg.AgendaJJ.entity.JudicialDivision;
import edu.egg.AgendaJJ.exception.TrialsException;
import edu.egg.AgendaJJ.repository.JudicialDivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class JudicialDivisionService {

    @Autowired
    private JudicialDivisionRepository judicialDivisionRepository;

    @Transactional
    public void createJudicialDivision(String name) throws TrialsException {
        JudicialDivision judicialDivision = new JudicialDivision();
        judicialDivision.setName(name);
        judicialDivisionRepository.save(judicialDivision);
    }

    @Transactional(readOnly = true)
    public JudicialDivision getJudicialDivision(Integer id) {
        Optional<JudicialDivision> judicialDivisionOptional = judicialDivisionRepository.findById(id);
        return judicialDivisionOptional.orElse(null);
    }

    @Transactional(readOnly = true)
    public List<JudicialDivision> getJudicialDivisions() {
        return judicialDivisionRepository.findAll();
    }
}