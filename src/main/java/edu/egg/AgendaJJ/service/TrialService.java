package edu.egg.AgendaJJ.service;

import edu.egg.AgendaJJ.entity.*;
import edu.egg.AgendaJJ.exception.TrialsException;
import edu.egg.AgendaJJ.repository.TrialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@Service
public class TrialService {

    @Autowired
    private TrialRepository trialRepository;

    @Transactional
    public void createTrial(LocalDate dateTrial, Lawsuit lawsuit) throws TrialsException {
        Trial trial = new Trial();
        trial.setDateTrial(dateTrial);
        trial.setLawsuit(lawsuit);
        trial.setCourt(lawsuit.getCourt());
        trial.setJudicialDivision(lawsuit.getCourt().getJudicialDivision());
        trial.setStatus(true);
        trialRepository.save(trial);
    }

    @Transactional(readOnly = true)
    public Trial getTrial(Integer id) {
        Optional<Trial> trialOptional = trialRepository.findById(id);
        return trialOptional.orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Trial> getTrials() {
        return trialRepository.findAll();
    }

    @Transactional
    public void updateTrial(Integer id, LocalDate dateTrial, Lawsuit lawsuit) throws TrialsException {
        Trial trial = trialRepository.findById(id).get();
        trial.setDateTrial(dateTrial);
        trial.setLawsuit(lawsuit);
        trial.setCourt(lawsuit.getCourt());
        trial.setJudicialDivision(lawsuit.getCourt().getJudicialDivision());
        trialRepository.save(trial);
    }

    @Transactional
    public void deleteTrial(Integer id) {
        trialRepository.deleteById(id);
    }

    @Transactional
    public void enableTrial(Integer id) {
        trialRepository.enableTrial(id);
    }

    @Transactional(readOnly = true)
    public List<Trial> findByJudicialDivision(Integer idJudicialDivision) {
        return trialRepository.findByJudicialDivision(idJudicialDivision);
    }

    @Transactional(readOnly = true)
    public List<Trial> findByDateTrial(Integer month) {
        return trialRepository.findByDateTrial(month);
    }

    @Transactional(readOnly = true)
    public List<Trial> findByCaseTitle(String caseTitle) {
        return trialRepository.findByCaseTitle(caseTitle);
    }
}