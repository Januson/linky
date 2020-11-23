package linky.web.visits;

class VisitDto {
    private final String linkName;
    private final String visitedAt;
    private final OriginDto origin;

    VisitDto(String linkName, String visitedAt, OriginDto origin) {
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