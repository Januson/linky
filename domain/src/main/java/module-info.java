module linky.domain {
    exports linky.links;
    exports linky.visits;
    exports linky.infrastructure;
    requires jakarta.cdi;
    requires jakarta.interceptor;
    requires jakarta.transaction;
}
