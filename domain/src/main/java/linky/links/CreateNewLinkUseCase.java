package linky.links;

import linky.links.validation.CompositeValidator;

import java.util.List;
import java.util.UUID;

class CreateNewLinkUseCase implements CreateNewLink {

    private final Links links;
    private final IsNameUsed isNameUsed;

    CreateNewLinkUseCase(final Links links, final IsNameUsed isNameUsed) {
        this.links = links;
        this.isNameUsed = isNameUsed;
    }

    @Override
    public Name create(final NewLink newLink) {
        final var link = new Link(
            validName(newLink),
            validUrl(newLink)
        );
        this.links.add(link);
        return link.name();
    }

    private Name validName(final NewLink linkName) {
        return linkName.name()
            .map(name -> name.valid(nameValidator()))
            .orElseGet(this::randomName);
    }

    private Url validUrl(NewLink newLink) {
        return newLink.url().valid();
    }

    private Name randomName() {
        return new Name(UUID.randomUUID().toString());
    }

    private CompositeValidator<Name> nameValidator() {
        return new CompositeValidator<>(List.of(
            new Name.IsAbusive(),
            new Name.IsUnique(this.isNameUsed)
        ));
    }

}
