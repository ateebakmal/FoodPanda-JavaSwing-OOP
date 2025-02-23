package gui.helper;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class AppendableObjectOutputStream extends ObjectOutputStream {
    // Constructor
    public AppendableObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    // Overriding the header-writing method to skip writing it
    @Override
    protected void writeStreamHeader() throws IOException {
        // Do nothing to prevent header from being written again
    }
}