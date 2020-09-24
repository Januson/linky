package linky.adapter.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestEndpointTest {

    private TestEndpoint endpoint;

    @BeforeEach
    void setUp() {
        this.endpoint = new TestEndpoint();
    }

    @Test
    public void itWorks() {
        String response = this.endpoint.greet();

        assertThat(response).isEqualTo("Hello, World!");
    }

}
