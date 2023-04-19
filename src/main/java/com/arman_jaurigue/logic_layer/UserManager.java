package com.arman_jaurigue.logic_layer;

import com.arman_jaurigue.data_access.database.*;
import com.arman_jaurigue.data_objects.*;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UserManager {
    UserAccessor userAccessor;

    public UserManager() {
        userAccessor = new UserAccessor();
    }

    public User loginUser(String email, char[] password) throws RuntimeException {
        User user;
        try {
            if (userAccessor.selectUserCountByEmailAndPassword(email, sha256(password)) != 0) {
                user = userAccessor.selectUserByEmail(email);
            } else {
                throw new RuntimeException("Invalid email and/or password");
            }
        } catch (Exception ex) {
            throw new RuntimeException("Failed to log in the user", ex);
        }
        return user;
    }

    public User getUserById(int id) throws Exception {
        User user = null;
        try {
            user = userAccessor.selectUserById(id);
            if (user == null) {
                throw new RuntimeException("Invalid Id");
            }
        } catch (Exception ex) {
            throw new RuntimeException("Failed to retrieve the user", ex);
        }
        return user;
    }

    public User registerUser(User user, char[] password) throws RuntimeException {
        User newUser;
        try {
            if (userAccessor.selectEmailCountByEmail(user.getEmail()) == 0) {
                if (userAccessor.addUser(user, sha256(password)) == 0)
                {
                    throw new RuntimeException("Failed to create the account.");
                } else {
                    newUser = userAccessor.selectUserByEmail(user.getEmail());
                }
            } else {
                throw new RuntimeException("An account with that email already exists.");
            }
        } catch (RuntimeException | SQLException | NoSuchAlgorithmException ex) {
            throw new RuntimeException("Failed to register the user." + "\n" + ex.getMessage(), ex);
        }
        return newUser;
    }

    private static String sha256(char[] password) throws NoSuchAlgorithmException {
        return toHexString(getSHA(String.valueOf(password)));
    }

    // Source: Geeks for Geeks
    private static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    private static String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 64)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }
}
