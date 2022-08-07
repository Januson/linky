package linky.links;

import linky.infrastructure.Events;

class FakeEvents implements Events<LinkVisited> {

    private LinkVisited lastEvent;

    @Override
    public void fire(LinkVisited event) {
        this.lastEvent = event;
    }

    public LinkVisited lastEvent() {
        return this.lastEvent;
    }
}
