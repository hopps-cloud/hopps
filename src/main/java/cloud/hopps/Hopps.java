package cloud.hopps;

import io.quarkus.runtime.StartupEvent;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.proxy.handler.ProxyHandler;
import io.vertx.httpproxy.HttpProxy;
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
		HttpServer proxyServer = vertx.createHttpServer();
		Router proxyRouter = Router.router(vertx);
		proxyServer.requestHandler(proxyRouter);
		proxyServer.listen(8081);

		HttpClient proxyClient = vertx.createHttpClient();
		HttpProxy httpProxy = HttpProxy.reverseProxy(proxyClient);

		proxyRouter
			.route(HttpMethod.GET, "/")
			.handler(ProxyHandler.create(httpProxy, 8088, "localhost"));
	}
}
