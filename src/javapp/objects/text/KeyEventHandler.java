package javapp.objects.text;

import java.awt.datatransfer.Clipboard;
import java.awt.event.KeyEvent;

public class KeyEventHandler {

    public static final Clipboard CLIPBOARD = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();

    public static final int CODED = 0xffff;
    public static final int BACKSPACE = 0x8;
    public static final int DELETE = 0x7f;
    public static final int TAB = 0x9;
    public static final int ENTER = 0xa;

    public static final int LEFT = 0x25;
    public static final int RIGHT = 0x27;
    public static final int UP = 0x26;
    public static final int DOWN = 0x28;

    /**
     * KeyEvent handler for a TextContainer.
     * 
     * @param event     the KeyEvent
     * @param container the TextContainer
     */
    public static void handleKeyEvent(KeyEvent event, TextContainer c) {
        switch (event.getID()) {
        case KeyEvent.KEY_PRESSED:
            keyPress(event, c);
            break;
        case KeyEvent.KEY_RELEASED:
            keyRelease(event, c);
            break;
        case KeyEvent.KEY_TYPED:
            keyType(event, c);
            break;
        }
    }

    /**
     * KeyPressed event handler for a TextContainer.
     * 
     * @param event     the KeyEvent
     * @param container the TextContainer
     */
    public static void keyPress(KeyEvent event, TextContainer container) {

        // Update the type index
        int keyCode = event.getKeyCode();
        if (keyCode == LEFT || keyCode == RIGHT || keyCode == UP || keyCode == DOWN) {
            typeIndex(event, container);
        }
    }

    /**
     * KeyReleased event handler for a TextContainer.
     * 
     * @param event     the KeyEvent
     * @param container the TextContainer
     */
    public static void keyRelease(KeyEvent event, TextContainer container) {

    }

    /**
     * KeyTyped event handler for a TextContainer.
     * 
     * @param event     the KeyEvent
     * @param container the TextContainer
     */
    public static void keyType(KeyEvent event, TextContainer container) {
        if (event.getKeyChar() == CODED) {
            return;
        } else if (event.isControlDown()) {
            ctrlActions(event, container);
        } else if (container.isEditable()) {
            typeActions(event, container);
        }
    }

    /*
     * 
     * 
     * TYPE INDEX
     * 
     * 
     */
    private static void typeIndex(KeyEvent event, TextContainer container) {

        // easy access key and keyCode
        int keyCode = event.getKeyCode();
        int typeIndex = container.getTypeIndex();

        // If theres a selection and shift is not pressed, go to the end of start of the
        // selection depending on the key.
        if (container.selection() && !event.isShiftDown()) {
            if (keyCode == LEFT) {
                container.setTypeIndex(container.lowestSelectIndex());
            } else if (keyCode == RIGHT) {
                container.setTypeIndex(container.highestSelectIndex());
            }
            return;
        }

        // If ctrl is down move the index an entire 'word'
        if (event.isControlDown()) {
            if (keyCode == LEFT) {
                typeIndex = ctrlLeft(container);
            } else if (keyCode == RIGHT) {
                typeIndex = ctrlRight(container);
            }

            // Otherwise just normal index adjusting
        } else {
            if (keyCode == LEFT) {
                typeIndex--;
            } else if (keyCode == RIGHT) {
                typeIndex++;
            }
        }

        if (!event.isShiftDown()) {
            container.setTypeIndex(typeIndex);
        } else {
            container.setSelectStop(typeIndex);
        }
    }

    /*
     * 
     * 
     * TYPE ACTIONS
     * 
     * 
     */

    private static void typeActions(KeyEvent event, TextContainer container) {
        char key = event.getKeyChar();

        if (key == BACKSPACE) {
            backspace(container);
        } else if (key == DELETE) {
            delete(container);
        } else if (key == TAB) {
            tab(container);
        } else if (key == ENTER) {
            enter(container);
        } else {
            typeKey(key, container);
        }
    }

    /**
     * Simply add a character to the container.
     * 
     * @param key       the character
     * @param container the container
     */
    private static void typeKey(char key, TextContainer container) {
        container.insert(key);
    }

    /**
     * Add a line break to the container.
     * 
     * @param container the container
     */
    private static void enter(TextContainer container) {
        container.insert("\n");
    }

    /**
     * add a tab to the container.
     * 
     * @param container the container
     */
    private static void tab(TextContainer container) {
        container.insert("  ");
    }

    /**
     * Delete key.
     * 
     * @param container the container
     */
    private static void delete(TextContainer container) {
        container.delete();
    }

    /**
     * Backspace key.
     * 
     * @param container the container
     */
    private static void backspace(TextContainer container) {
        container.backspace();
    }

    /*
     * 
     * 
     * CTRL ACTIONS
     * 
     * 
     * 
     */
    private static void ctrlActions(KeyEvent event, TextContainer container) {
        char key = event.getKeyChar();
        if ((int) key == 127 && container.isEditable()) {
            ctrlBackspace(container);
        } else if ((int) key == 1) {
            ctrlA(container);
        } else if ((int) key == 3) {
            ctrlC(container);
        } else if ((int) key == 24 && container.isEditable()) {
            ctrlX(container);
        } else if ((int) key == 22 && container.isEditable()) {
            ctrlV(container);
        }
    }

    private static void ctrlBackspace(TextContainer container) {
        if (container.selection()) {
            container.removeSelection();
        } else {
            container.remove(ctrlLeft(container), container.getTypeIndex());
        }
    }

    /**
     * Selects everything in the container.
     * 
     * @param container the container
     */
    private static void ctrlA(TextContainer container) {
        container.select(0, container.length());
    }

    /**
     * Copy the selection from the container.
     * 
     * @param container the container
     */
    private static void ctrlC(TextContainer container) {
        String selection = container.getSelection();

        // Put the selection in the clipboard
        try {
            CLIPBOARD.setContents(new java.awt.datatransfer.StringSelection(selection), null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Paste the clipboard to the container.
     * 
     * @param container the container
     */
    private static void ctrlV(TextContainer container) {
        try {

            // Get clipboard into String
            String add = (String) CLIPBOARD.getData(java.awt.datatransfer.DataFlavor.stringFlavor);

            // Insert the clipboard text
            container.insert(add);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Cut the selection from the container.
     * 
     * @param container the container
     */
    private static void ctrlX(TextContainer container) {
        String selection = container.getSelection();

        // Put the selected text into the clipboard
        try {
            CLIPBOARD.setContents(new java.awt.datatransfer.StringSelection(selection), null);

            // Remove the selected text
            container.removeSelection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 
     * 
     * CTRL ARROW
     * 
     * 
     */

    private static int ctrlLeft(TextContainer container) {
        return ctrlLeft(container, container.getTypeIndex());
    }

    private static int ctrlLeft(TextContainer container, int start) {

        // Get the text up until the type index
        String text = container.getContent().substring(0, start);

        // If there is no text, keep the same
        if (text.length() == 0) {
            return start;
        }

        // Get the type of character at the end of the string
        boolean letter = Character.isAlphabetic(text.charAt(text.length() - 1));

        // Loop through each index from back to front
        for (int index = start - 1; index >= 0; index--) {

            // If there's a change in character type, return the index + 1, because we want
            // the index after the change in character type.
            if (letter != Character.isAlphabetic(text.charAt(index))) {
                return index + 1;
            }
        }

        // Otherwise if it went through the entire loop, it's at the start of the text,
        // so index will be 0.
        return 0;
    }

    private static int ctrlRight(TextContainer container) {
        return ctrlRight(container, container.getTypeIndex());
    }

    private static int ctrlRight(TextContainer container, int start) {

        // Get the text from index to end
        String text = container.getContent().substring(start);

        // If there is no text, keep the same
        if (text.length() == 0) {
            return start;
        }

        // Get the type of character at the start of the string
        boolean letter = Character.isAlphabetic(text.charAt(0));

        // Loop through each index from front to back
        for (int index = 0; index < text.length(); index++) {

            // If there's a change in character type, return the index - 1 + start, because
            // we want the index after the change in character type.
            if (letter != Character.isAlphabetic(text.charAt(index))) {
                return index + start;
            }
        }

        // Otherwise if it went through the entire loop, it's at the end of the text,
        // so index will be length.
        return container.length();
    }
}
