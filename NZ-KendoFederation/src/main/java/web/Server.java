/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import com.google.gson.Gson;
import dao.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.jooby.Jooby;
import org.jooby.json.Gzon;

/**
 *
 * @author Will
 */
public class Server extends Jooby{
    MemberDAO memberDao = new MemberJdbcDAO();    
  
    Server(){
        port(8080);
        assets("/*.html");
        assets("/css/*.css");
        assets("/js/*.js");
        assets("/images/*.png");
        assets("/images/*.jpg");
        // make index.html the default page
        assets("/", "index.html");
        // prevent 404 errors due to browsers requesting favicons
        // get("/favicon.ico", () -> Results.noContent());
        
        List<String> noAuth = Arrays.asList("/api/register");
        use(new BasicHttpAuthenticator(memberDao, noAuth));        
        
        use(new Gzon());
        use(new MemberModule(memberDao));
        
    }
    
    
    public static void main(String[] args) throws Exception {
        
        Gson bob = new Gson();
        System.out.println(bob.toJson(LocalDate.now()));
        
        System.out.println("\nStarting Server.");

        Server server = new Server();

        CompletableFuture.runAsync(() -> {
            server.start();
        });

        server.onStarted(() -> {
            System.out.println("\nPress Enter to stop the server.");
        });

        // wait for user to hit the Enter key
        System.in.read();
        System.exit(0);
    }


    
}
