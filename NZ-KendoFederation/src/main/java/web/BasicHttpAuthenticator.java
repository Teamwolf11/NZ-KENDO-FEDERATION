package web;

import com.google.inject.Binder;
import com.typesafe.config.Config;
import web.CredentialsValidator;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jooby.Env;
import org.jooby.Err;
import org.jooby.Jooby;
import org.jooby.Result;
import org.jooby.Router;
import org.jooby.Status;

public class BasicHttpAuthenticator implements Jooby.Module {

	private final CredentialsValidator validator;
	private final List<String> noAuth = new ArrayList<>();

	public BasicHttpAuthenticator(CredentialsValidator validator) {
		this.validator = validator;
	}

	public BasicHttpAuthenticator(CredentialsValidator validator, List<String> noAuth) {
		this(validator);
		this.noAuth.addAll(noAuth);
	}

	@Override
	public void configure(Env env, Config conf, Binder binder) throws Throwable {

		Router router = env.router();

		// listen on ALL requests
		router.use("*", "*", (req, rsp, chain) -> {

			try {
				String authToken = req.header("Authorization").value();

				Base64.Decoder decoder = Base64.getDecoder();

				// strip off the "Basic " part
				String stripped = authToken.replace("Basic ", "");

				String authDetails = new String(decoder.decode(stripped));

				// split the decoded string into user name and password
				Matcher matcher
					= Pattern.compile("(?<userName>.+?):(?<password>.*)").matcher(authDetails);

				if (!matcher.matches()) {
					// token is not in the expected format so is likely invalid
					rsp.send(new Result().header("WWW-Authenticate", "None").status(Status.UNAUTHORIZED));
				}

				String userName = matcher.group("userName");
				String password = matcher.group("password");

				if (validator.validateCredentials(userName, password)) {
					// add userName to request
					req.set("userName", userName);
                                        
                                        
                                        // query database to determine role of user
                                        // check what role is required for path requesting
                                        // String path = req.path();
                                        // If path matches role, then do chain.next, else do rsp.send
                                        
					//chain.next(req, rsp);
				} else {
					// bad credentials
					rsp.send(new Result().header("WWW-Authenticate", "None").status(Status.UNAUTHORIZED));
				}
			} catch (Err ex) {
				rsp.send(new Result().header("WWW-Authenticate", "None").status(Status.UNAUTHORIZED));
			}

		}).name("BasicHttpAuthenticator").excludes(noAuth);

	}

}
