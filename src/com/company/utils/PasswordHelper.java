package com.company.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.company.utils.Constants.ALGORITHM;
import static com.company.utils.Constants.PATTERN;

public final class PasswordHelper {

    public static String encrypt(String string) throws NoSuchAlgorithmException {
        String hash = null;
        try{
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            byte[] result = messageDigest.digest(string.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte el:result) {
                sb.append(Integer.toString((el & 0xff) + 0x100, 16).substring(1));
            }
            hash = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return hash;
    }

    public static boolean checkSymbol(String string) {
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(string);
        return matcher.find();
    }
}
