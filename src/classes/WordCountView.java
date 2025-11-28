package classes;

import javax.swing.*;
import interfaces.Observer;

/**
 * ===========================================================================================
 * OBSERVER PATTERN: Concrete Observer - WordCountView
 * ===========================================================================================
 * Automatically updates to display the current word count whenever the editor content changes.
 */
public class WordCountView implements Observer {
    private JLabel label;

    public WordCountView(JLabel label) {
        this.label = label;
    }

    @Override
    public void update(String content) {
        int wordCount = 0;
        if (content != null && !content.trim().isEmpty()) {
            // Split by whitespace and count non-empty tokens
            String[] words = content.trim().split("\\s+");
            wordCount = words.length;
        }
        label.setText("Word Count: " + wordCount);
    }
}
