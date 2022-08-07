package linky.web.visits;

import org.springframework.hateoas.RepresentationModel;

class VisitDto extends RepresentationModel<VisitDto> {
	private final String linkName;
	private final String visitedAt;
	private final OriginDto origin;

	VisitDto(final String linkName, final String visitedAt, final OriginDto origin) {
		this.linkName = linkName;
		this.visitedAt = visitedAt;
		this.origin = origin;
	}

	public String getLinkName() {
		return linkName;
	}

	public String getVisitedAt() {
		return visitedAt;
	}

	public OriginDto getOrigin() {
		return origin;
	}
}
