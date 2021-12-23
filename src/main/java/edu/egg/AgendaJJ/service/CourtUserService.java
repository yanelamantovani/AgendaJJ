package edu.egg.AgendaJJ.service;

import edu.egg.AgendaJJ.entity.CourtUser;
import edu.egg.AgendaJJ.enums.UserRole;
import edu.egg.AgendaJJ.exception.TrialsException;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import edu.egg.AgendaJJ.repository.CourtUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class CourtUserService implements UserDetailsService {
   
    @Autowired
    private CourtUserRepository courtUserRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private PhotoService photoService;
    @Autowired
    private EmailService emailService;

    private final String message = "No existe un usuario registrado con el correo %s";

    @Transactional
    public void createUser(String name, Long dni, String mail, String password, String jobTitle, String image, MultipartFile photo) throws TrialsException, IOException {
        if (courtUserRepository.existsByMail(mail)) {
            throw new TrialsException("Ya existe un usuario asociado con el correo ingresado");
        }
        CourtUser courtUser = new CourtUser();
        courtUser.setName(name);
        courtUser.setDni(dni);
        courtUser.setMail(mail);
        courtUser.setPassword(encoder.encode(password));
        courtUser.setJobTitle(jobTitle);
        if (courtUserRepository.findAll().isEmpty()) {
            courtUser.setUserRole(UserRole.ADMIN);
        } else {
            courtUser.setUserRole(UserRole.USER);
        }
        if (!photo.isEmpty()) courtUser.setImage(photoService.copyPhoto(photo));
        courtUser.setStatus(true);
        courtUserRepository.save(courtUser);
        emailService.sendThread(mail);
    }

    @Transactional(readOnly = true)
    public CourtUser findById(Integer id) {
        return courtUserRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public CourtUser findByMail(String mail) {
        Optional<CourtUser> optionalCourtUser = courtUserRepository.findByMail(mail);
        return optionalCourtUser.orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        CourtUser courtUser = courtUserRepository.findByMail(mail).orElseThrow(() -> new UsernameNotFoundException(String.format(message, mail)));
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + courtUser.getUserRole().name());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);
        session.setAttribute("name", courtUser.getName());
        session.setAttribute("mail", courtUser.getMail());
        session.setAttribute("userRol", courtUser.getUserRole().name());
        session.setAttribute("image", courtUser.getImage());
        return new User(courtUser.getMail(), courtUser.getPassword(), Collections.singletonList(authority));
    }
}