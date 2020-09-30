package linky.shortcuts;

import an.awesome.pipelinr.Command;

public final class CreateShortcut implements Command<String> {

    public final String link;
    
    public CreateShortcut(final String link) {
        this.link = link;
    }

}
