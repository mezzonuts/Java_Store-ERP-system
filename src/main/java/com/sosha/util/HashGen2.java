package com.sosha.util;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
public class HashGen2 {
    public static void main(String[] args){
        BCryptPasswordEncoder e = new BCryptPasswordEncoder();
        System.out.println(e.encode("admin123"));
    }
}
