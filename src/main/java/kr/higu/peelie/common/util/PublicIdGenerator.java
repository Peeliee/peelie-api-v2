package kr.higu.peelie.common.util;

import java.security.SecureRandom;

public class PublicIdGenerator {
    private static final int TOKEN_LENGTH = 20;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String NUMERIC_CHARACTERS = "0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String randomCharacter(int length) {
        validateLength(length);
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    public static String randomCharacterWithPrefix(String prefix) {
        String safePrefix = (prefix == null) ? "" : prefix;
        int remainingLength = Math.max(0, TOKEN_LENGTH - safePrefix.length());
        return safePrefix + randomCharacter(remainingLength);
    }

    public static String randomNumeric(int length) {
        validateLength(length);
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(NUMERIC_CHARACTERS.charAt(RANDOM.nextInt(NUMERIC_CHARACTERS.length())));
        }
        return sb.toString();
    }

    private static void validateLength(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("publicId의 길이는 0이상의 양수여야합니다.");
        }
    }
}
