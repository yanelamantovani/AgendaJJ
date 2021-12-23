package edu.egg.AgendaJJ.controller;

import edu.egg.AgendaJJ.entity.Lawsuit;
import edu.egg.AgendaJJ.entity.Trial;
import edu.egg.AgendaJJ.exception.TrialsException;
import edu.egg.AgendaJJ.service.CourtService;
import edu.egg.AgendaJJ.service.JudicialDivisionService;
import edu.egg.AgendaJJ.service.LawsuitService;
import edu.egg.AgendaJJ.service.TrialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/trial")
public class TrialController {

    @Autowired
    private TrialService trialService;

    @Autowired
    private CourtService courtService;

    @Autowired
    private LawsuitService lawsuitService;

    @Autowired
    private JudicialDivisionService judicialDivisionService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView showTrials(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("trials");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            mav.addObject("success", flashMap.get("success-name"));
            mav.addObject("error", flashMap.get("error-name"));
        }
        mav.addObject("trials", trialService.getTrials());
        return mav;
    }

    @GetMapping("/findByMonth")
    public ModelAndView showByMonth(@RequestParam(name="month", required=false) Integer month) {
        ModelAndView mav = new ModelAndView("find-by-month");
        if (month != null) {
            List<Trial> trials = trialService.findByDateTrial(month);
            mav.addObject("trials", trials);
        } else {
            mav.addObject("trials", Collections.EMPTY_LIST);
        }
        return mav;
    }

    @GetMapping("/findByJudicialDivision")
    public ModelAndView showByJudicialDivision(@RequestParam(name="idJudicialDivision", required=false) Integer idJudicialDivision) {
        ModelAndView mav = new ModelAndView("find-by-judicial-division");
        if (idJudicialDivision != null) {
            List<Trial> trials = trialService.findByJudicialDivision(idJudicialDivision);
            mav.addObject("trials", trials);
        } else {
            mav.addObject("trials", Collections.EMPTY_LIST);
        }
        return mav;
    }

    @GetMapping("/findByCaseTitle")
    public ModelAndView showByCaseTitle(@RequestParam(name="caseTitle", required=false) String caseTitle) {
        ModelAndView mav = new ModelAndView("find-by-case-title");
        if (caseTitle != null) {
            List<Trial> trials = trialService.findByCaseTitle(caseTitle);
            mav.addObject("trials", trials);
        } else {
            mav.addObject("trials", Collections.EMPTY_LIST);
        }
        return mav;
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView createTrial() {
        ModelAndView mav = new ModelAndView("trial-form");
        mav.addObject("trial", new Trial());
        List<Lawsuit> lawsuits = lawsuitService.getLawsuits();
        lawsuits.sort(Comparator.comparing(Lawsuit::getCaseTitle));
        mav.addObject("lawsuits", lawsuits);
        mav.addObject("title", "Crear Juicio");
        mav.addObject("action", "save");
        return mav;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView saveTrial(@RequestParam LocalDate dateTrial, @RequestParam Lawsuit lawsuit, RedirectAttributes redirectAttributes) throws TrialsException {
        try {
            trialService.createTrial(dateTrial, lawsuit);
            redirectAttributes.addFlashAttribute("success-name", "El juicio se guardó exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error-name", e.getMessage());
            return new RedirectView("/trial/create");
        }
        return new RedirectView("/trial");
    }

    @GetMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView editTrial(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("trial-form");
        mav.addObject("trial", trialService.getTrial(id));
        List<Lawsuit> lawsuits = lawsuitService.getLawsuits();
        lawsuits.sort(Comparator.comparing(Lawsuit::getCaseTitle));
        mav.addObject("lawsuits", lawsuits);
        mav.addObject("title", "Editar Juicio");
        mav.addObject("action", "update");
        return mav;
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView updateTrial(@RequestParam Integer id, @RequestParam LocalDate dateTrial, @RequestParam Lawsuit lawsuit, RedirectAttributes redirectAttributes) throws TrialsException {
        try {
            trialService.updateTrial(id, dateTrial, lawsuit);
            redirectAttributes.addFlashAttribute("success-name", "El juicio fue modificado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error-name", e.getMessage());
            return new RedirectView("/trial/update/{id}");
        }
        return new RedirectView("/trial");
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView deleteTrial(@PathVariable Integer id) {
        trialService.deleteTrial(id);
        return new RedirectView("/trial");
    }

    @PostMapping("/enable/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView enableTrial(@PathVariable Integer id) {
        trialService.enableTrial(id);
        return new RedirectView("/trial");
    }
}