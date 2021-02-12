package visits;

import org.gradle.testkit.runner.GradleRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.DigestUtils;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.containers.output.ToStringConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.LazyFuture;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Future;


@Testcontainers
class DemoApplicationTests {

	private static final Future<String> IMAGE_FUTURE = new LazyFuture<>() {
		@Override
		protected String resolve() {
			// Find project's root dir
			Path cc = Paths.get(".").toAbsolutePath().getParent().getParent();
			File cwd = cc.toFile();
//			for (
//				cwd = new File(".");
//				!new File(cwd, "settings.gradle").isFile();
//				cwd = cwd.getParentFile()
//			);

			// Make it unique per folder (for caching)
			var imageName = String.format(
				"local/app-%s:%s",
				DigestUtils.md5DigestAsHex(cwd.getAbsolutePath().getBytes()),
				System.currentTimeMillis()
			);

			// Run Gradle task and override the image name
			GradleRunner.create()
				.withProjectDir(cwd)
				.withArguments("-q", "bootBuildImage", "--imageName=" + imageName)
//				.withArguments("-q", "bootBuildImage", "-PdockerImageName=" + imageName)
				.forwardOutput()
				.build();

			return imageName;
		}
	};

	@Container
	static final GenericContainer<?> APP = new GenericContainer<>(IMAGE_FUTURE)
		.withExposedPorts(8080)
		// Forward logs
		.withLogConsumer(new ToStringConsumer() {
			@Override
			public void accept(OutputFrame outputFrame) {
				if (outputFrame.getBytes() != null) {
					try {
						System.out.write(outputFrame.getBytes());
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
		});

	WebTestClient webClient;

	@BeforeEach
	void setUp() {
		var endpoint = String.format(
            "http://%s:%d/",
            APP.getContainerIpAddress(),
            APP.getFirstMappedPort()
        );
		webClient = WebTestClient.bindToServer().baseUrl(endpoint).build();
	}

	@Test
	public void starts() {
		
	}

	@Test
	public void healthy() {
		webClient.get()
				.uri("/actuator/health")
				.exchange()
				.expectStatus()
				.is2xxSuccessful();
	}
}