package com.misomota.exam.DRY;

import jakarta.servlet.http.HttpSession;

public class Session {

    public static boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("account") != null;
    }
}
