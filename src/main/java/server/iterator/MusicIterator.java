package server.iterator;

import lombok.AllArgsConstructor;

import java.io.File;
import java.util.Iterator;
import java.util.NoSuchElementException;

@AllArgsConstructor
public class MusicIterator implements Iterator<File> {
    private final File[] files;
    private int currentIndex = 0;

    public MusicIterator(File[] files) {
        this.files = files;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < files.length;
    }

    @Override
    public File next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Немає більше елементів для ітерації");
        }
        return files[currentIndex++];
    }

}
