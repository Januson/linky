module linky.domain {
    exports linky.links;
    exports linky.visits;
    exports linky.infrastructure;
//    requires jakarta.transaction;
    requires java.transaction;
}
