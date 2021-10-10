
package web;

import com.google.gson.Gson;
import dao.*;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import org.jooby.Jooby;
import org.jooby.json.Gzon;

/**
 *
 * @author Will
 */
public class Server extends Jooby{
    MemberDAO studentDao = new MemberJdbcDAO();    
    EventDAO eventDao = new EventJdbcDAO();    
    
    Server(){
        port(8080);
        use(new Gzon());
        use(new MemberModule(studentDao));
        use(new EventModule(eventDao));
        assets("/", "index.html");
        assets("*");
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
