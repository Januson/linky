package linky.links;

import linky.links.validation.CompositeValidator;

import java.util.List;

class CreateNewLinkUseCase implements CreateNewLink {

    private final Links links;

    CreateNewLinkUseCase(final Links links) {
        this.links = links;
    }

    @Override
    public Name create(final NewLink newLink) {
        newLink.name();
        final var link = new Link(
            newLink.name()
                .map(name -> name.valid(nameValidator()))
                .orElseGet(this::randomName),
            newLink.url().valid()
        );
        this.links.add(link);
        return link.name();
    }

    private Name randomName() {
        return new Name("random");
    }

    private CompositeValidator<Name> nameValidator() {
        return new CompositeValidator<>(List.of(
            new Name.IsAbusive()
        ));
    }

}
