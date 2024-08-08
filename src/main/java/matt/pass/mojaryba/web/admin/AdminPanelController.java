package matt.pass.mojaryba.web.admin;

import matt.pass.mojaryba.domain.user.User;
import matt.pass.mojaryba.domain.user.UserService;
import matt.pass.mojaryba.domain.user.dto.UserAdministrationDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AdminPanelController {

    private UserService userService;

    public AdminPanelController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/uzytkownicy")
    String adminUsers(){
        return "admin-panel-users";
    }

    @GetMapping("/admin/szukaj-uzytkownika")
    String finsUser(Model model, @RequestParam String userFind) {
        final List<User> users = userService.findUsers(userFind);
        model.addAttribute("users", users);
        return "admin-panel-users";
    }
    @GetMapping("/admin/usun-uzytkownika/{id}")
    String deleteUser(@PathVariable long id, RedirectAttributes redirectAttributes){
        userService.deleteUserById(id);
        sendNotyfication(redirectAttributes, "Użytkownik został usunięty");
        return "redirect:/admin/uzytkownicy";
    }
    @GetMapping("/admin/uzytkownik/{id}")
    String userEditPage(Model model, @PathVariable long id) {
        final UserAdministrationDto user = userService.findAdministrationUserById(id);
        model.addAttribute("user", user);
        return "admin-panel-user-edit";
    }
    @PostMapping("/admin/edytuj-nick/{id}")
    String editUserNick(@PathVariable long id, @RequestParam String nick, RedirectAttributes redirectAttributes){
        if (userService.chechExistByNick(nick)) {
            sendNotyfication(redirectAttributes, "Podany nick jest już zajęty");
        }else {
            userService.adminEditUserNick(nick, id);
          sendNotyfication(redirectAttributes, "Nick został zmieniony");
        }
        return "redirect:/admin/uzytkownik/" + id;
    }


    @PostMapping("/admin/edytuj-email/{id}")
    String editUserEmail(@PathVariable long id, @RequestParam String email, RedirectAttributes redirectAttributes) {
        if (userService.chechExistByEmail(email)) {
            sendNotyfication(redirectAttributes, "Podany email jest już zajęty");
        } else {
            userService.adminEditUserEmail(email, id);
           sendNotyfication(redirectAttributes, "Adres email został zmieniony");
        }
        return "redirect:/admin/uzytkownik/" + id;
    }
    @PostMapping("/admin/edytuj-haslo/{id}")
    String editUserPass(@PathVariable long id, @RequestParam String password, RedirectAttributes redirectAttributes){
        userService.adminEditUserPass(password, id);
        sendNotyfication(redirectAttributes, "Hasło zostało zmienione");
        return "redirect:/admin/uzytkownik/" + id;
    }
    private static void sendNotyfication(RedirectAttributes redirectAttributes, String notification) {
        redirectAttributes.addFlashAttribute
                (FishManagementController.NOTIFICATION_ATTRIBUTE, notification);
    }
    @GetMapping("/admin/dezaktywuj/{id}")
        String deactivateUser(@PathVariable long id, RedirectAttributes redirectAttributes){
        userService.deactivateOrActivateUser(id);
        sendNotyfication(redirectAttributes, "Konto zostało dezaktywowane");
        return "redirect:/admin/uzytkownik/" + id;
    }
    @GetMapping("/admin/aktywuj/{id}")
        String activateUser(@PathVariable long id, RedirectAttributes redirectAttributes){
            userService.deactivateOrActivateUser(id);
            sendNotyfication(redirectAttributes, "Konto zostało aktywowane");
            return "redirect:/admin/uzytkownik/" + id;
    }
    @GetMapping("/admin/block/{id}")
        String blockUser(@PathVariable long id, RedirectAttributes redirectAttributes) {
        userService.blockUser(id);
        sendNotyfication(redirectAttributes, "Użytkownik został zablokowany");
        return "redirect:/admin/uzytkownik/" + id;
    }
    @GetMapping("/admin/unblock/{id}")
        String unblockUser(@PathVariable long id, RedirectAttributes redirectAttributes) {
        userService.unblockUser(id);
        sendNotyfication(redirectAttributes, "Użytkownik został odblokowany");
        return "redirect:/admin/uzytkownik/" + id;
    }
}
