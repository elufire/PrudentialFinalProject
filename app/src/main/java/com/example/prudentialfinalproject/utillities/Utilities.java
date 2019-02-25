package com.example.prudentialfinalproject.utillities;

import android.text.TextUtils;
import android.util.Patterns;

public class Utilities {
    public static boolean isPasswordValid(String passedPassword){
        return passedPassword.length() > 8;
    }

    public static boolean isUserEmailValid(String passedUserEmail){
        return !TextUtils.isEmpty(passedUserEmail)
                && Patterns.EMAIL_ADDRESS.matcher(passedUserEmail).matches();

    }
}
