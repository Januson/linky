//package visits
//
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.Assertions.assertTrue
//import org.spekframework.spek2.Spek
//import org.spekframework.spek2.style.gherkin.Feature
//import org.spekframework.spek2.style.specification.describe
//
//class ShortcutSpec : Spek({
//    Feature("Find a link by a shortcut") {
//        Scenario("adding items") {
//            Given("an existing shortcut") {
//                //create
//            }
//            When("looking for a link") {
//                set.add("foo")
//            }
//
//            Then("it should have a size of 1") {
//                assertEquals(1, set.size)
//            }
//
//            Then("it should contain foo") {
//                assertTrue(set.contains("foo"))
//            }
//        }
//    }
//
//    describe("searching for a shortcut") {
//        val aShortcut = "a745dc"
//        it("should return a corresponding link") {
//            assertTrue(true)
//        }
//    }
//})