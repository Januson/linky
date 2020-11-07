package linky.persistence.links;

import linky.links.Link;
import linky.links.Name;
import linky.links.NewLink;
import linky.links.Url;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({LinkPersistenceAdapter.class, LinkMapper.class})
class LinkPersistenceAdapterTest {

    @Autowired
    private LinkPersistenceAdapter adapter;

    @Autowired
    private LinkRepository links;

    @Test
    @Sql("PreexistingLinks.sql")
    void findsALink() {
        final Optional<Link> link = this.adapter.findBy(new Name("ggl"));

        assertThat(link)
            .map(Link::url)
            .hasValue(new Url("www.google.com"));
    }

    @Test
    void createsALink() {
        assertThat(this.links.count()).isZero();

        final Name name = new Name("stckowfl");
        final NewLink link = new NewLink(
            name, new Url("www.stackoverflow.com"));

        adapter.add(link);

        assertThat(this.links.count()).isEqualTo(1);
        assertThat(this.links.findByName(name.toString())).isPresent();
    }

}