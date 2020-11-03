package linky.persistence.links;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
class UserEntityRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private LinkRepository userRepository;

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(userRepository).isNotNull();
    }

    @Test
    void createNewLink() {
        NewLink newLink = new NewLink();
        newLink.entityManager = entityManager;

        assertThat(userRepository).isNotNull();
    }

    class NewLink {
        EntityManager entityManager;

        public void create(LinkEntity link) {
            entityManager.getTransaction().begin();
            entityManager.persist(link);
            entityManager.getTransaction().commit();
        }
    }
}
