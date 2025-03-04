/*
package vision.psy.flacdump.service.legacy;
import org.springframework.stereotype.Service;
import vision.psy.flacdump.model.UserAccount;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

// wird durch Spring Security ersetzt

@Service
public class UserService {

    final static String usernameDB = System.getenv("DB_USERNAME");
    final static String passwordDB = System.getenv("DB_PASSWORD");
    final static String connectionString = "jdbc:mysql://localhost:3306/flacdump";

    // User registrieren
    public static boolean register(int id, String name, String password, String mail) {
        boolean success = false;
        try (Connection connection = DriverManager.getConnection(connectionString, usernameDB, passwordDB);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO useraccount (id, name, password, mail) VALUES (?, ?, ?, ?)");) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            try {
                String passwordEncrypted = UserAccount.encode(password);
                preparedStatement.setString(3, passwordEncrypted);
            } catch (Exception e) {
                System.out.println("Fehler bei der Passwortverschlüsselung");
                e.printStackTrace();
                return false;
            }
            preparedStatement.setString(4, mail);
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                System.out.println("Der Benutzer wurde erfolgreich angelegt.");
                success = true;
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                System.out.println("Der Benutzername existiert bereits. Bitte einen anderen Namen wählen.");
            } else {
                System.out.println("Es ist ein Datenbankfehler aufgetreten.");
                e.printStackTrace();
            }
        }
        return success;
    }

    // User Login
    public static UserAccount login(String username, String password) {
        String query = "SELECT * FROM useraccount WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(connectionString, usernameDB, passwordDB);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String passwordLogin = rs.getString("password");
                String passwordInput = UserAccount.encode(password); //Passwörter verschlüsseln
                if (passwordLogin.equals(passwordInput)) {
                    System.out.println("Login erfolgreich. \nEingeloggter Benutzer: " + rs.getString("name") + " " + rs.getString("mail"));
                    return new UserAccount(rs.getInt("id"), rs.getString("name"), rs.getString("password"), rs.getString("mail"));
                } else {
                    System.out.println("Login fehlgeschlagen. Falsches Passwort.");
                    return null;
                }
            } else {
                System.out.println("Benutzer nicht gefunden.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Es ist ein Datenbankfehler aufgetreten.");
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Passwort ändern
    public static boolean changePassword(String username, String altesPasswort, String neuesPasswort) throws Exception {
        boolean ausgabe = false;

        // altes Passwort verschlüsseln

        String altesPasswortEncrypted = UserAccount.encode(altesPasswort);

        try (Connection connection = DriverManager.getConnection(connectionString, usernameDB, passwordDB);
             Statement statement = connection.createStatement();) {
            ResultSet rs = statement.executeQuery("SELECT password FROM useraccount WHERE name = '" + username + "'");
            String passwortEncryptedAusDB = null;
            if (rs.next()) {
                passwortEncryptedAusDB = rs.getString("password");
            } else {
                System.out.println("Nutzer nicht gefunden");
                return ausgabe;
            }

            // Vergleich mit bestehendem Passwort Hash
            // neues Passwort verschlüsseln und in die DB schreiben
            if (altesPasswortEncrypted.equals(passwortEncryptedAusDB)) {
                String neuesPasswortEncrypted = UserAccount.encode(neuesPasswort);
                try (PreparedStatement preparedPasswortChange = connection.prepareStatement("UPDATE useraccount SET password = ? WHERE name = ?");) {
                    preparedPasswortChange.setString(1, neuesPasswortEncrypted);
                    preparedPasswortChange.setString(2, username);
                    int result = preparedPasswortChange.executeUpdate();
                    if (result == 1) {
                        System.out.println("Das Passwort wurde erfolgreich geändert. ");
                        ausgabe = true;
                        return ausgabe;
                    } else {
                        System.out.println("Das Passwort konnte nicht geändert werden. ");
                        return ausgabe;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return ausgabe;
                }
            } else {
                System.out.println("Das Passwort konnte nicht geändert werden. Die Passwörter stimmen nicht überein.");
                return ausgabe;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ausgabe;
        }
    }
}

 */