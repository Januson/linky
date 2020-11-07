//package linky.links;
//
//import org.junit.jupiter.api.Test;
//
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//class UrlValidationTest {
//
//    @Test
//    void nullLink() {
//        final Url.Unvalidated unvalidated = new Url.Unvalidated("");
//
//        assertThatThrownBy(unvalidated::valid)
//            .isInstanceOf(Url.ValidationFailed.class);
//    }
//
//    @Test
//    void emptyLink() {
//        final Url.Unvalidated unvalidated = new Url.Unvalidated("");
//
//        assertThatThrownBy(unvalidated::valid)
//            .isInstanceOf(Url.ValidationFailed.class);
//    }
//
//    @Test
//    void validLink() {
//        final Url.Unvalidated unvalidated = new Url.Unvalidated("");
//
//        assertThatThrownBy(unvalidated::valid)
//            .isInstanceOf(Url.ValidationFailed.class);
//    }
//
//}
