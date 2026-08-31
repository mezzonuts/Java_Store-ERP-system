import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
public class BGen {
    public static void main(String[] a){
        BCryptPasswordEncoder e=new BCryptPasswordEncoder();
        System.out.println(e.encode("admin123"));
        System.out.println(e.encode("password"));
    }
}
