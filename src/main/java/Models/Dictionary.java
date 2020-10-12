package Models;

import java.util.ArrayList;
import java.util.Collection;

public class Dictionary extends ArrayList<Word> {

    public Dictionary(int initialCapacity) {
        super(initialCapacity);
    }

    public Dictionary() {
    }

    public Dictionary(Collection<? extends Word> c) {
        super(c);
    }
}
