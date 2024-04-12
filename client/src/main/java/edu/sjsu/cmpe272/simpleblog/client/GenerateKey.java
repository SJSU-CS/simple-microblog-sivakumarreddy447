package com.course.simplemicroblogcli;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class KeyPairGeneratorUtility {

    public static Map<String, String> createRSAKeyPair() {
        try {
            Map<String, String> keyMap = new HashMap<>();
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);

            KeyPair pair = generator.generateKeyPair();

            PublicKey pubKey = pair.getPublic();
            PrivateKey privKey = pair.getPrivate();

            String encodedPublicKey = Base64.getEncoder().encodeToString(pubKey.getEncoded());
            String encodedPrivateKey = Base64.getEncoder().encodeToString(privKey.getEncoded());

            keyMap.put("publicKey", encodedPublicKey);
            keyMap.put("privateKey", encodedPrivateKey);
            return keyMap;
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return new HashMap<>();
        }
    }

    public static PrivateKey stringToPrivateKey(String privateKeyContent, String keyAlgorithm) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(privateKeyContent);

        KeyFactory factory = KeyFactory.getInstance(keyAlgorithm);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decodedKey);
        return factory.generatePrivate(spec);
    }

    public static String publicKeyToPEMFormat(String publicKeyEncoded) {
        StringBuilder pemFormattedKey = new StringBuilder();
        pemFormattedKey.append("-----BEGIN PUBLIC KEY-----\n");

        int length = publicKeyEncoded.length();
        for (int i = 0; i < length; i += 64) {
            int end = Math.min(length, i + 64);
            pemFormattedKey.append(publicKeyEncoded, i, end).append("\n");
        }

        pemFormattedKey.append("-----END PUBLIC KEY-----");
        return pemFormattedKey.toString();
    }
}
