package GUI;

import classes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * ===========================================================================================
 * GUI CLASS: GUI.TextEditorGUI
 * ===========================================================================================
 * Creates the Swing-based user interface for the text editor.
 * Demonstrates all four design patterns in action.
 */
class TextEditorGUI {
    private Editor editor;
    private JTextArea textArea;
    private JLabel wordCountLabel;
    private JLabel charCountLabel;
    private JLabel stateLabel;
    private JButton stateButton;
    private JButton undoButton;
    private JButton redoButton;

    public TextEditorGUI() {
        editor = new Editor();
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        // Create main frame
        JFrame frame = new JFrame("Text classes.Editor - Design Patterns Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout(10, 10));

        // ===========================================================================================
        // TOP PANEL: Control buttons
        // ===========================================================================================
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        undoButton = new JButton("Undo");
        redoButton = new JButton("Redo");
        stateButton = new JButton("Switch to Read-Only");

        JButton insertButton = new JButton("Insert 'Hello' at cursor");
        JButton deleteButton = new JButton("Delete 5 chars at cursor");

        topPanel.add(undoButton);
        topPanel.add(redoButton);
        topPanel.add(new JSeparator(SwingConstants.VERTICAL));
        topPanel.add(stateButton);
        topPanel.add(new JSeparator(SwingConstants.VERTICAL));
        topPanel.add(insertButton);
        topPanel.add(deleteButton);

        frame.add(topPanel, BorderLayout.NORTH);

        // ===========================================================================================
        // CENTER PANEL: Text area
        // ===========================================================================================
        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("classes.Editor Content"));
        frame.add(scrollPane, BorderLayout.CENTER);

        // ===========================================================================================
        // BOTTOM PANEL: interfaces.Observer views (status labels)
        // ===========================================================================================
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Statistics (interfaces.Observer Pattern)"));

        wordCountLabel = new JLabel("Word Count: 0");
        charCountLabel = new JLabel("Character Count: 0");
        stateLabel = new JLabel("State: Insert Mode");

        wordCountLabel.setFont(new Font("Arial", Font.BOLD, 12));
        charCountLabel.setFont(new Font("Arial", Font.BOLD, 12));
        stateLabel.setFont(new Font("Arial", Font.BOLD, 12));

        bottomPanel.add(wordCountLabel);
        bottomPanel.add(new JSeparator(SwingConstants.VERTICAL));
        bottomPanel.add(charCountLabel);
        bottomPanel.add(new JSeparator(SwingConstants.VERTICAL));
        bottomPanel.add(stateLabel);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        // ===========================================================================================
        // OBSERVER PATTERN: Attach observers to the editor
        // ===========================================================================================
        WordCountView wordCountView = new WordCountView(wordCountLabel);
        CharacterCountView charCountView = new CharacterCountView(charCountLabel);

        editor.attach(wordCountView);
        editor.attach(charCountView);

        // ===========================================================================================
        // EVENT LISTENERS
        // ===========================================================================================

        // Undo button - COMMAND PATTERN
        undoButton.addActionListener(e -> {
            editor.undo();
            syncTextAreaWithEditor();
            updateButtonStates();
        });

        // Redo button - COMMAND PATTERN
        redoButton.addActionListener(e -> {
            editor.redo();
            syncTextAreaWithEditor();
            updateButtonStates();
        });

        // State toggle button - STATE PATTERN
        stateButton.addActionListener(e -> {
            if (editor.getState() instanceof InsertState) {
                editor.setState(new ReadOnlyState());
                stateButton.setText("Switch to Insert Mode");
                stateLabel.setText("State: Read-Only Mode");
                textArea.setEditable(false);
                textArea.setBackground(new Color(245, 245, 245));
            } else {
                editor.setState(new InsertState());
                stateButton.setText("Switch to Read-Only");
                stateLabel.setText("State: Insert Mode");
                textArea.setEditable(true);
                textArea.setBackground(Color.WHITE);
            }
        });

        // Insert button - Demonstrates COMMAND pattern with specific operation
        insertButton.addActionListener(e -> {
            try {
                int position = textArea.getCaretPosition();
                editor.insert("Hello", position);
                syncTextAreaWithEditor();
                updateButtonStates();
            } catch (IllegalStateException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Delete button - Demonstrates COMMAND pattern with specific operation
        deleteButton.addActionListener(e -> {
            try {
                int position = textArea.getCaretPosition();
                editor.delete(5, position);
                syncTextAreaWithEditor();
                updateButtonStates();
            } catch (IllegalStateException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Text area document listener - Sync changes to editor via COMMAND pattern
        textArea.addKeyListener(new KeyAdapter() {
            private String previousContent = "";

            @Override
            public void keyReleased(KeyEvent e) {
                String currentContent = textArea.getText();

                // Only process if content actually changed
                if (!currentContent.equals(previousContent)) {
                    try {
                        // Determine what changed and create appropriate command
                        if (currentContent.length() > previousContent.length()) {
                            // Text was inserted
                            int position = findChangePosition(previousContent, currentContent);
                            String insertedText = currentContent.substring(position, position + (currentContent.length() - previousContent.length()));
                            editor.insert(insertedText, position);
                        } else if (currentContent.length() < previousContent.length()) {
                            // Text was deleted
                            int position = findChangePosition(currentContent, previousContent);
                            int length = previousContent.length() - currentContent.length();
                            editor.delete(length, position);
                        }

                        previousContent = currentContent;
                        updateButtonStates();
                    } catch (IllegalStateException ex) {
                        // If in read-only mode, this shouldn't happen due to textArea.setEditable(false)
                        // but we handle it just in case
                        JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        syncTextAreaWithEditor();
                    }
                }
            }

            private int findChangePosition(String shorter, String longer) {
                for (int i = 0; i < shorter.length(); i++) {
                    if (shorter.charAt(i) != longer.charAt(i)) {
                        return i;
                    }
                }
                return shorter.length();
            }
        });

        // Initial button state update
        updateButtonStates();

        // Show the frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Sync the text area display with the editor's actual content.
     * Used after undo/redo operations.
     */
    private void syncTextAreaWithEditor() {
        String editorContent = editor.getContent();
        if (!textArea.getText().equals(editorContent)) {
            int caretPosition = textArea.getCaretPosition();
            textArea.setText(editorContent);
            // Try to maintain caret position
            try {
                textArea.setCaretPosition(Math.min(caretPosition, editorContent.length()));
            } catch (IllegalArgumentException e) {
                textArea.setCaretPosition(editorContent.length());
            }
        }
    }

    /**
     * Update the enabled/disabled state of undo/redo buttons.
     */
    private void updateButtonStates() {
        undoButton.setEnabled(editor.canUndo());
        redoButton.setEnabled(editor.canRedo());
    }

    public static void main(String[] args) {
        // Run GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new TextEditorGUI());
    }
}