package com.timeToast.timeToast.global.util;

import java.util.regex.Pattern;

public class StringValidator {

    public static boolean nicknameValidation(final String input) {
        if (input == null || input.isEmpty() || input.length()>10) {
            return false;
        }

        return Pattern.matches("^[a-zA-Z0-9가-힣]+$", input);
    }

}
