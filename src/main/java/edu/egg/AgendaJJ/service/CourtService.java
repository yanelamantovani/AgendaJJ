package edu.egg.AgendaJJ.service;

import edu.egg.AgendaJJ.entity.Court;
import edu.egg.AgendaJJ.entity.JudicialDivision;
import edu.egg.AgendaJJ.exception.TrialsException;
import edu.egg.AgendaJJ.repository.CourtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CourtService {

    @Autowired
    private CourtRepository courtRepository;

    @Transactional
    public void createCourt(String name, String courtPresident, String address, Long phoneNumber, JudicialDivision judicialDivision) throws TrialsException {
        Court court = new Court();
        court.setName(name);
        court.setCourtPresident(courtPresident);
        court.setAddress(address);
        court.setPhoneNumber(phoneNumber);
        court.setJudicialDivision(judicialDivision);
        courtRepository.save(court);
    }

    @Transactional(readOnly = true)
    public Court getCourt(Integer id) {
        Optional<Court> courtOptional = courtRepository.findById(id);
        return courtOptional.orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Court> getCourts() {
        return courtRepository.findAll();
    }

    @Transactional
    public void updateCourt(Integer id, String name, String courtPresident, String address, Long phoneNumber, JudicialDivision judicialDivision) throws TrialsException {
        Court court = courtRepository.findById(id).get();
        court.setName(name);
        court.setCourtPresident(courtPresident);
        court.setAddress(address);
        court.setPhoneNumber(phoneNumber);
        court.setJudicialDivision(judicialDivision);
        courtRepository.save(court);
    }
}
