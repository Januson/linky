package linky.persistence.links;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "link")
class LinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String shortcut;

    @Column
    private String url;

    protected LinkEntity() {
    }

    LinkEntity(final String shortcut, final String url) {
        this.shortcut = shortcut;
        this.url = url;
    }

    String getShortcut() {
        return this.shortcut;
    }

    String getUrl() {
        return this.url;
    }

}
