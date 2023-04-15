package linky.persistence.links;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

	protected LinkEntity() {}

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
