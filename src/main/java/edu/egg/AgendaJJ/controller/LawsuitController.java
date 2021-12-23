package edu.egg.AgendaJJ.controller;

import edu.egg.AgendaJJ.entity.Court;
import edu.egg.AgendaJJ.entity.Lawsuit;
import edu.egg.AgendaJJ.exception.TrialsException;
import edu.egg.AgendaJJ.service.CourtService;
import edu.egg.AgendaJJ.service.LawsuitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/lawsuit")
public class LawsuitController {

    @Autowired
    private LawsuitService lawsuitService;

    @Autowired
    private CourtService courtService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView showLawsuit(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("lawsuits");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            mav.addObject("success", flashMap.get("success"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("lawsuits", lawsuitService.getLawsuits());
        return mav;
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView createLawsuit(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("lawsuit-form");
        mav.addObject("lawsuit", new Lawsuit());
        mav.addObject("courts", courtService.getCourts());
        mav.addObject("title", "Crear Causa");
        mav.addObject("action", "save");
        return mav;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView saveLawsuit(@RequestParam String number, @RequestParam String caseTitle, @RequestParam Court court, RedirectAttributes redirectAttributes) throws TrialsException {
        try {
            lawsuitService.createLawsuit(number, caseTitle, court);
            redirectAttributes.addFlashAttribute("success", "La causa se guardó exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return new RedirectView("/lawsuit/create");
        }
        return new RedirectView("/lawsuit");
    }

    @GetMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView editLawsuit(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("lawsuit-form");
        mav.addObject("lawsuit", lawsuitService.getLawsuit(id));
        mav.addObject("courts", courtService.getCourts());
        mav.addObject("title", "Editar Causa");
        mav.addObject("action", "update");
        return mav;
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView updateLawsuit(@RequestParam Integer id, @RequestParam String number, @RequestParam String caseTitle, @RequestParam Court court, RedirectAttributes redirectAttributes) throws TrialsException {
        try {
            lawsuitService.updateLawsuit(id, number, caseTitle, court);
            redirectAttributes.addFlashAttribute("success", "La causa fue modificada exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return new RedirectView("/lawsuit/update/{id}");
        }
        return new RedirectView("/lawsuit");
    }
}
