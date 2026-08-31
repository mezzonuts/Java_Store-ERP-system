package com.sosha.util;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
public class HashGen {
    public static void main(String[] args){
        BCryptPasswordEncoder e = new BCryptPasswordEncoder();
        System.out.println(e.encode("admin123"));
    }
}
