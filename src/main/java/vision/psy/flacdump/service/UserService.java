package vision.psy.flacdump.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vision.psy.flacdump.model.UserAccount;
import vision.psy.flacdump.repository.UserAccountRepository;

@Service
public class UserService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // User registration
    public boolean register(String name, String password, String mail) {
        try {
            String encryptedPassword = passwordEncoder.encode(password);
            UserAccount userAccount = new UserAccount(null, name, encryptedPassword, mail);
            userAccountRepository.save(userAccount);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // User login
    public UserAccount login(String username, String password) {
        UserAccount userAccount = userAccountRepository.findByName(username);
        if (userAccount != null && passwordEncoder.matches(password, userAccount.getPassword())) {
            return userAccount;
        }
        return null;
    }

    // Change password
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        UserAccount userAccount = userAccountRepository.findByName(username);
        if (userAccount != null && passwordEncoder.matches(oldPassword, userAccount.getPassword())) {
            userAccount.setPassword(passwordEncoder.encode(newPassword));
            userAccountRepository.save(userAccount);
            return true;
        }
        return false;
    }
}