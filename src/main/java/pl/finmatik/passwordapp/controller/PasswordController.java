package pl.finmatik.passwordapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.finmatik.passwordapp.model.Password;
import pl.finmatik.passwordapp.service.PasswordService;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PasswordController {
    private final PasswordService passwordService;

    @GetMapping("/password")
    public ResponseEntity<List<Password>> getPasswords (@RequestParam int chars,
                                                      @RequestParam boolean letters,
                                                      @RequestParam boolean digits,
                                                      @RequestParam boolean specialsigns) {
        List<Password> passwordList;
        passwordList = passwordService.generateRandomPassword(chars, letters, digits, specialsigns);
        return ResponseEntity.ok(passwordList);
    }
}
