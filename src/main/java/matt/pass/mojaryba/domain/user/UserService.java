package matt.pass.mojaryba.domain.user;

import jakarta.transaction.Transactional;
import matt.pass.mojaryba.domain.config.CustomSecurityService;
import matt.pass.mojaryba.domain.email.EmailService;
import matt.pass.mojaryba.domain.user.dto.UserAdministrationDto;
import matt.pass.mojaryba.domain.user.dto.UserCredentialsDto;
import matt.pass.mojaryba.domain.user.dto.UserRegisterDto;
import org.apache.commons.mail.EmailException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       UserRoleRepository userRoleRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
        this.emailService = emailService;
    }

    public Optional<UserCredentialsDto> findActivUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .filter(User::isActiv)
                .map(UserMapper::map);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    public void register(UserRegisterDto userRegisterDto) throws EmailException {
        final User userToSave = new User();
        userToSave.setEmail(userRegisterDto.getEmail().toLowerCase());
        userToSave.setNick(userRegisterDto.getNick());
        final String encodePassword = passwordEncoder.encode(userRegisterDto.getPassword());
        userToSave.setPassword(encodePassword);
        userToSave.setActiv(false);
        userToSave.setActivKey(generateActivKey());
        final UserRole defaultRole = userRoleRepository.findByName(CustomSecurityService.USER_ROLE).orElseThrow();
        userToSave.getRoles().add(defaultRole);
        final User savedUser = userRepository.save(userToSave);
        emailService.sendActivEmail(savedUser);
    }

    private String generateActivKey() {
        return UUID.randomUUID().toString();
    }

    @Transactional
    public boolean checkAndActivUserAccount(long id, String activKey) {
        final User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (user.getActivKey().equals(activKey)) {
            user.setActiv(true);
            return true;
        } else return false;
    }

    public boolean chechExistByEmail(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

    public boolean chechExistByNick(String nick) {
        return userRepository.existsByNickIgnoreCase(nick);
    }

    public void remindPassEmail(User user) throws EmailException {
        emailService.sendRemindPassEmail(user);
    }

    @Transactional
    public boolean setNewPass(long id, String activKey, String newPassword) {
        final User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (user.getActivKey().equals(activKey)) {
            final String encodePassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodePassword);
            return true;
        }
        return false;
    }

    public List<User> findUsers(String findUser) {
        return userRepository.findAll().stream()
                .filter(user -> user.getNick().toLowerCase().contains(findUser.toLowerCase()) ||
                        user.getEmail().equalsIgnoreCase(findUser))
                .toList();
    }

    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    public UserAdministrationDto findAdministrationUserById(long id) {
        return userRepository.findById(id)
                .map(UserMapper::mapToUserAdministration)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public void adminEditUserNick(String nick, long userId) {
        final User user = userRepository.findById(userId).orElseThrow();
        user.setNick(nick);
    }

    @Transactional
    public void adminEditUserPass(String password, long userId) {
        final User user = userRepository.findById(userId).orElseThrow();
        final String encodePassword = passwordEncoder.encode(password);
        user.setPassword(encodePassword);
    }

    @Transactional
    public void adminEditUserEmail(String email, long userId) {
        final User user = userRepository.findById(userId).orElseThrow();
        user.setEmail(email);
    }

    @Transactional
    public void deactivateOrActivateUser(long userId) {
        final User user = userRepository.findById(userId).orElseThrow();
        user.setActiv(!user.isActiv());
    }

    @Transactional
    public void blockUser(long userId) {
        final User user = userRepository.findById(userId).orElseThrow();
        user.getRoles().clear();
        final UserRole blockRole = userRoleRepository.findByName(CustomSecurityService.BLOCKED_ROLE).orElseThrow();
        user.getRoles().add(blockRole);
    }

    @Transactional
    public void unblockUser(long userId) {
        final User user = userRepository.findById(userId).orElseThrow();
        user.getRoles().clear();
        final UserRole userRole = userRoleRepository.findByName(CustomSecurityService.USER_ROLE).orElseThrow();
        user.getRoles().add(userRole);
    }

    public boolean isBlocked(User user) {
        final UserRole blockerRole = userRoleRepository.findByName(CustomSecurityService.BLOCKED_ROLE).orElseThrow();
        return user.getRoles().contains(blockerRole);
    }
}
