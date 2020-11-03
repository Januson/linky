//package linky.events;
//
//import linky.links.Link;
//import linky.links.LinkVisited;
//import linky.visits.AddNewVisit;
//import linky.visits.NewVisit;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//public class LinkVisitedEventTest {
//
//    @MockBean
//    private AddNewVisit visits;
//    @Autowired
//    private Publisher publisher;
//    @Autowired
//    private LinkVisitedListener listener;
//
//    @Test
//    public void visitedLinkResultsInANewVisit() {
//        this.publisher.fire(new LinkVisited(new Link.Name("")));
//
//        verify(this.visits).add(any(NewVisit.class));
//    }
//
//}
