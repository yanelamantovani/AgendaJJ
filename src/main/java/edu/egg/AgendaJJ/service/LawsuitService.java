package edu.egg.AgendaJJ.service;

import edu.egg.AgendaJJ.entity.*;
import edu.egg.AgendaJJ.exception.TrialsException;
import edu.egg.AgendaJJ.repository.LawsuitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class LawsuitService {

    @Autowired
    private LawsuitRepository lawsuitRepository;

    @Transactional
    public void createLawsuit(String number, String caseTitle, Court court) throws TrialsException {
        Lawsuit lawsuit = new Lawsuit();
        lawsuit.setNumber(number);
        lawsuit.setCaseTitle(caseTitle);
        lawsuit.setCourt(court);
        lawsuitRepository.save(lawsuit);
    }

    @Transactional(readOnly = true)
    public Lawsuit getLawsuit(Integer id) {
        Optional<Lawsuit> lawsuitOptional = lawsuitRepository.findById(id);
        return lawsuitOptional.orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Lawsuit> getLawsuits() {
        return lawsuitRepository.findAll();
    }

    @Transactional
    public void updateLawsuit(Integer id, String number, String caseTitle, Court court) throws TrialsException {
        Lawsuit lawsuit = lawsuitRepository.findById(id).get();
        lawsuit.setNumber(number);
        lawsuit.setCaseTitle(caseTitle);
        lawsuit.setCourt(court);
        lawsuitRepository.save(lawsuit);
    }

}

