package vision.psy.flacdump.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

// Manuelle Model-Klasse für Benutzerkonten mit Passwort-Hash-Funktion
// Wird später in Spring Security überführt bzw. ersetzt

public class        UserAccount {
    public Integer id;
    public String name;
    public String password;
    public String mail;

    public UserAccount(Integer id, String name, String password, String mail) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.mail = mail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public static String encode(String passwort) throws Exception {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(passwort.getBytes(StandardCharsets.UTF_8));

        // Byte-Array in Hexadezimal-String umwandeln
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0'); // Auffüllen mit '0'
            hexString.append(hex);
        }
        // Hexadezimal-String zurückgeben
        return hexString.toString();
    }
}
