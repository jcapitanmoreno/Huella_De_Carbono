package com.github.jcapitanmoreno.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {

    /**
     * Hashea una contraseña utilizando el algoritmo SHA-256.
     *
     * @param password la contraseña a hashear.
     * @return la contraseña hasheada en formato hexadecimal.
     * @throws RuntimeException si ocurre un error al hashear la contraseña.
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear la contraseña", e);
        }
    }

}
