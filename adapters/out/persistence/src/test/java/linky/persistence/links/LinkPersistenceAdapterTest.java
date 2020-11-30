package linky.persistence.links;

import linky.links.Link;
import linky.links.Name;
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
    void noLinkFound() {
        final Optional<Link> link = this.adapter.findBy(new Name("ggl"));

        assertThat(link).isEmpty();
    }

    @Test
    @Sql("PreexistingLinks.sql")
    void findsExistingLink() {
        final Optional<Link> link = this.adapter.findBy(new Name("ggl"));

        assertThat(link)
            .map(Link::url)
            .hasValue(new Url("www.google.com"));
    }

    @Test
    void storesNewLink() {
        assertThat(this.links.count()).isZero();

        final Name name = new Name("stckowfl");
        final Link link = new Link(
            name, new Url("www.stackoverflow.com"));

        adapter.add(link);

        assertThat(this.links.count()).isEqualTo(1);
        assertThat(this.links.findByName(name.toString())).isPresent();
    }

    @Test
    @Sql("PreexistingLinks.sql")
    void linkNameIsInUse() {
        final boolean nameInUse = this.adapter.isInUse(new Name("ggl"));
        assertThat(nameInUse).isTrue();
    }

    @Test
    @Sql("PreexistingLinks.sql")
    void linkNameIsNotInUse() {
        final boolean uniqueName = this.adapter.isInUse(new Name("ggl2"));
        assertThat(uniqueName).isFalse();
    }

}