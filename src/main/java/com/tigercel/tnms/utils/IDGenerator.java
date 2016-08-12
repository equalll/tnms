package com.tigercel.tnms.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * Created by freedom on 2016/5/26.
 */
public class IDGenerator {

    private static Logger logger = LoggerFactory.getLogger(IDGenerator.class);


    public static String getID() {
        String id = UUID.randomUUID().toString();

        return id.replace("-", "");
    }
    /*
    public static String getID() {
        String id = null;

        try {
            //Initialize SecureRandom
            //This is a lengthy operation, to be done only upon
            //initialization of the application
            SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");

            //generate a random number
            String randomNum = new Integer(prng.nextInt()).toString();

            //get its digest
            //MessageDigest sha = MessageDigest.getInstance("SHA-256");
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] result =  sha.digest(randomNum.getBytes());

            //System.out.println("Random number: " + randomNum);
            //System.out.println("Message digest: " + hexEncode(result));
            id = hexEncode(result);
        }
        catch (NoSuchAlgorithmException ex) {
            logger.error(ex.getMessage());
        }
        return id;
    }
    */


    /**
     * The byte[] returned by MessageDigest does not have a nice
     * textual representation, so some form of encoding is usually performed.
     *
     * This implementation follows the example of David Flanagan's book
     * "Java In A Nutshell", and converts a byte array into a String
     * of hex characters.
     *
     * Another popular alternative is to use a "Base64" encoding.
     */
    /*
    private static String hexEncode(byte[] aInput){
        StringBuilder result = new StringBuilder();
        char[] digits = {'0', '1', '2', '3', '4','5','6','7','8','9','a','b','c','d','e','f'};
        for (int idx = 0; idx < aInput.length; ++idx) {
            byte b = aInput[idx];
            result.append(digits[ (b&0xf0) >> 4 ]);
            result.append(digits[ b&0x0f]);
        }

        return result.toString();
    }
    */
}