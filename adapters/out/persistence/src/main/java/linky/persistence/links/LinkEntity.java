package linky.persistence.links;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "link")
class LinkEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private String url;

    LinkEntity() {
    }

    LinkEntity(final String name, final String url) {
        this.name = name;
        this.url = url;
    }

    String getName() {
        return this.name;
    }

    String getUrl() {
        return this.url;
    }

}
