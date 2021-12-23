package edu.egg.AgendaJJ.controller;

import edu.egg.AgendaJJ.entity.CourtUser;
import edu.egg.AgendaJJ.exception.TrialsException;
import edu.egg.AgendaJJ.service.CourtUserService;
import edu.egg.AgendaJJ.service.PhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class CourtUserController {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private CourtUserService courtUserService;

    private CourtUser courtUser;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, Principal principal) {

        ModelAndView mav = new ModelAndView("login");
        if (error != null) {
            mav.addObject("error", "Correo o contraseña incorrecta");
        }
        if (logout != null) {
            mav.addObject("logout", "Sesión finalizada");
        }
        if (principal != null) {
            //imprimimos el correo
            LOGGER.info("Principal -> {}", principal.getName());
            mav.setViewName("redirect:/index");
        }
        return mav;
    }

    @GetMapping("/signup")
    public ModelAndView signup(HttpServletRequest request, Principal principal) {
        ModelAndView mav = new ModelAndView("signup");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            mav.addObject("success", flashMap.get("success"));
            mav.addObject("error", flashMap.get("error"));
            mav.addObject("name", flashMap.get("name"));
            mav.addObject("dni", flashMap.get("dni"));
            mav.addObject("mail", flashMap.get("mail"));
            mav.addObject("password", flashMap.get("password"));
            mav.addObject("jobTitle", flashMap.get("jobTitle"));
            mav.addObject("image", flashMap.get("image"));
        }
        if (principal != null) {
            LOGGER.info("Principal -> {}", principal.getName());
            mav.setViewName("redirect:/");
        }
        return mav;
    }

    @PostMapping("/registration")
    public RedirectView signup(@RequestParam String name, @RequestParam Long dni, @RequestParam String mail, @RequestParam String password, @RequestParam String jobTitle, String image, MultipartFile photo, RedirectAttributes attributes, HttpServletRequest request) throws TrialsException, ServletException, IOException {
        RedirectView redirectView = new RedirectView("/login");
        courtUserService.createUser(name, dni, mail, password, jobTitle, image, photo);
        attributes.addFlashAttribute("success", "Se ha registrado con éxito.");
        request.login(mail, password);
        redirectView.setUrl("/info");
        return redirectView;
    }
}