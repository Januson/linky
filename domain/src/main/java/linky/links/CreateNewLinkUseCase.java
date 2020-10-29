package linky.links;

class CreateNewLinkUseCase implements CreateNewLink {

    private final Links links;

    CreateNewLinkUseCase(final Links links) {
        this.links = links;
    }

    @Override
    public LinkName create(final NewLink newLink) {
        this.links.add(newLink);
        return new LinkName();
    }
}