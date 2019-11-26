package com.gustavo.comelon.utils;

import java.util.regex.Pattern;

public class Regex {

    public static final Pattern VALID_EMAIL_REGEX =
            Pattern.compile("^[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$");

    public static final Pattern VALID_PASSWORD_REGEX =
            Pattern.compile("[a-zA-Z0-9]{8,15}");

}
