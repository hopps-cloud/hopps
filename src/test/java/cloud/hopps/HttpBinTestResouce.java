package cloud.hopps;

import io.quarkus.test.common.DevServicesContext;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.Container;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.shaded.com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Optional;

public class HttpBinTestResouce implements QuarkusTestResourceLifecycleManager, DevServicesContext.ContextAware
{
	private GenericContainer httpBin;
	private Optional<String> containerNetworkId;

	@Override
	public void setIntegrationTestContext(DevServicesContext context)
	{
		containerNetworkId = context.containerNetworkId();
	}

	@Override
	public Map<String, String> start()
	{
		httpBin = new GenericContainer("kennethreitz/httpbin")
			.withExposedPorts(8088);

		httpBin.start();
		return ImmutableMap.of("httpbin.port", "8088");
	}

	@Override
	public void stop()
	{
		httpBin.stop();
	}
}
