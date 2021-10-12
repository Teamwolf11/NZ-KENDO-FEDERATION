package web;

import com.google.gson.Gson;
import dao.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import domain.Event;
import java.util.ArrayList;
import org.jooby.Jooby;
import org.jooby.Results;
import org.jooby.json.Gzon;

/**
 *
 * @author Will
 */
public class Server extends Jooby {

    MemberDAO memberDao = new MemberJdbcDAO();
    EventDAO eventDao = new EventJdbcDAO();

    Server() {
        port(8080);
//        assets("/", "index.html");
//        assets("*");

        // prevent 404 errors due to browsers requesting favicons
        //get("/favicon.ico", () -> Results.noContent());
        assets("/*.html");
        assets("/member/*.html");
        assets("/clubLeader/*.html");
        assets("/fedLeader/*.html");
        assets("/css/*.css");
        assets("/js/*.js");
        assets("/images/*.png");
        assets("/favicon.png");
        assets("/images/*.jpg");
        // make index.html the default page
        assets("/", "index.html");

        // List of paths that should not be protected
        List<String> noAuth = new ArrayList<>();
        noAuth.add("/api/register");
        noAuth.add("/api/events");
        noAuth.add("/api/adminCreateEvent");
        noAuth.add("/api/bookEvents");
        // Add auth filter
        use(new BasicHttpAuthenticator(memberDao, noAuth));

        use(new Gzon());
        use(new MemberModule(memberDao));
        use(new EventModule(eventDao, memberDao));
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
