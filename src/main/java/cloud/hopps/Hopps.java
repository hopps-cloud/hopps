package cloud.hopps;

import io.quarkus.runtime.StartupEvent;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class Hopps
{
	private static final Logger LOGGER = LoggerFactory.getLogger("ListenerBean");

	@Inject
	Vertx vertx;

	void onStart(@Observes StartupEvent ev)
	{
		HttpServer backendServer = vertx.createHttpServer();
		Router backendRouter = Router.router(vertx);

		backendRouter.route(HttpMethod.GET, "/foo").handler(rc -> {
			rc.response()
				.putHeader("content-type", "text/html")
				.end("<html><body><h1>I'm the target resource!</h1></body></html>");
		});

		backendServer.requestHandler(backendRouter).listen(7070);
	}
}
