package classes;

import javax.swing.*;
import interfaces.Observer;

/**
 * ===========================================================================================
 * OBSERVER PATTERN: Concrete Observer - CharacterCountView
 * ===========================================================================================
 * Automatically updates to display the current character count whenever the editor content changes.
 */
public class CharacterCountView implements Observer {
    private JLabel label;

    public CharacterCountView(JLabel label) {
        this.label = label;
    }

    @Override
    public void update(String content) {
        int charCount = (content != null) ? content.length() : 0;
        label.setText("Character Count: " + charCount);
    }
}
