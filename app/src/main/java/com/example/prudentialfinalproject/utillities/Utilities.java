package com.example.prudentialfinalproject.utillities;
//fix2
import android.text.TextUtils;
import android.util.Patterns;

public class Utilities {
    public static boolean isPasswordValid(String passedPassword){
        return passedPassword.length() >= 8;
    }

    public static boolean isUserEmailValid(String passedUserEmail){
        return !TextUtils.isEmpty(passedUserEmail)
                && Patterns.EMAIL_ADDRESS.matcher(passedUserEmail).matches();

    }

    public static boolean isAccountNumberValid(String passedAccountNumber){
      return passedAccountNumber.length() > 8
              && passedAccountNumber.matches("[0-9]+");
    }
}
