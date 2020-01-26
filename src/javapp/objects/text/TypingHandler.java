package javapp.objects.text;

import java.awt.event.KeyEvent;

public class TypingHandler {

    String text = "";
    int selectPos = 0;
    int typePos = 0;
    boolean editable = true;
//    private double textLeading;
//    private double typeY;
//    private Object typeXV;

    public void typeKey(KeyEvent e) {
        char key = e.getKeyChar();

        // Constrain the typepos to prevent exceptions
        typePos = constrain(typePos, 0, text.length());
        selectPos = constrain(selectPos, 0, text.length());

        // Don't do anything if the key is a CODED one
        // Else do the appropriate action.
        if (key == 0xffff) {
            ; // Don't do stuff when key is coded
        } else if (e.isControlDown()) {
            ctrlActions(key);
        } else if (editable) {
            typeActions(e);
        }
    }

    private int constrain(int a, int b, int c) {
        return Math.max(Math.min(a, c), b);
    }

    public void pressKey(KeyEvent e) {

        e.getKeyCode();
        e.getKeyChar();

        // This handles the arrow keys and uses them
        // to move around the typepos.
        updateTypepos(e);
    }

    public void releaseKey(KeyEvent event) {
    }

    // Key actions when ctrl is down
    private void ctrlActions(char key) {

        // CTRL + Backspace removes entire words
        if ((int) key == 127 && editable) {
            ctrlBackspace();
        } else if ((int) key == 1) {
            ctrlA();
        } else if ((int) key == 3) {
            ctrlC();
        } else if ((int) key == 24 && editable) {
            ctrlX();
        } else if ((int) key == 22 && editable) {
            ctrlV();
        }
    }

    // Normal type actions
    private void typeActions(KeyEvent e) {

        e.getKeyCode();
        char key = e.getKeyChar();

        // This is true when there is text selected
        boolean selection = selectPos != typePos;

        // Always remove the selection
        removeSelection();

        // Don't do anything when there's a selection and backspace
        if (key == KeyEvent.VK_BACK_SPACE && selection) {
            ;
        } else if (key == KeyEvent.VK_BACK_SPACE) {
            backspace();
        } else if (key == KeyEvent.VK_DELETE && selection) {
            ;
        } else if (key == KeyEvent.VK_DELETE) {
            delete();
        } else if (key == KeyEvent.VK_TAB) {
            tab();
        } else if (key == KeyEvent.VK_ENTER) {
            enter();
        } else {
            typeKey(key);
        }

        // Adjust the selectpos to the typepos
        selectPos = typePos;
    }

    // Update the typepos using the arrow keys
    private void updateTypepos(KeyEvent e) {

        // easy access key and keyCode
        int keyCode = e.getKeyCode();
        e.getKeyChar();

        // When ctrl is down the arrow keys skip words.
        if (e.isControlDown()) {
            if (keyCode == KeyEvent.VK_LEFT) {
                typePos = moveLeftCtrlIndex(typePos);
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                typePos = moveRightCtrlIndex(typePos);
            } else if (keyCode == KeyEvent.VK_UP) {
//                typePos = getIndexFromCoords(typeXV, typeY - (double) 0.5 * textLeading);
            } else if (keyCode == KeyEvent.VK_DOWN) {
//                typePos = getIndexFromCoords(typeXV, typeY + (double) 1.5 * textLeading);
            }
        } else {

            // If something is selected, left and right keys teleport to
            // beginning or ending of selected text, only when not pressing
            // shift because then it'll just keep increasing the selection.
            if (!e.isShiftDown() && selectPos != typePos) {
                if (keyCode == KeyEvent.VK_LEFT) {
                    typePos = Math.min(selectPos, typePos);
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    typePos = Math.max(selectPos, typePos);
                }

                // Else, normal increase or decrease in typepos
            } else {
                if (keyCode == KeyEvent.VK_LEFT) {
                    typePos--;
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    typePos++;
                }
            }

            // Up and down key functionality, use the y coord and
            // textleading to go to the line above or below
            if (keyCode == KeyEvent.VK_UP) {
//                typePos = getIndexFromCoords(typeXV, typeY - (double) 0.5 * textLeading);
            } else if (keyCode == KeyEvent.VK_DOWN) {
//                typePos = getIndexFromCoords(typeXV, typeY + (double) 1.5 * textLeading);
            }
        }

        // Constrain the typepos to prevent exceptions.
        typePos = constrain(typePos, 0, text.length());
        selectPos = constrain(selectPos, 0, text.length());

        // Adjust the selectpos to the typepos only when shift isn't pressed
        // Because when shift is pressed you select text.
        if ((keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_UP
                || keyCode == KeyEvent.VK_DOWN) && !e.isShiftDown()) {
            selectPos = typePos;
        }
    }

    // Backspace an entire word
    private void ctrlBackspace() {
        selectPos = moveLeftCtrlIndex(typePos);
        text = text.substring(0, Math.max(0, selectPos)) + text.substring(typePos, text.length());
        typePos = selectPos;
    }

    // Select everything
    private void ctrlA() {
        typePos = text.length();
        selectPos = 0;
    }

    // Copy selection
    private void ctrlC() {
        try {

            // Store selected text in a String.
            String copy = text.substring(Math.min(typePos, selectPos), Math.max(typePos, selectPos));

            // Put the selection in the clipboard
            java.awt.Toolkit.getDefaultToolkit().getSystemClipboard()
                    .setContents(new java.awt.datatransfer.StringSelection(copy), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Cut selection
    private void ctrlX() {
        try {

            // Store the selected text in String
            String copy = text.substring(Math.min(typePos, selectPos), Math.max(typePos, selectPos));

            // Put the selected text into the clipboard
            java.awt.Toolkit.getDefaultToolkit().getSystemClipboard()
                    .setContents(new java.awt.datatransfer.StringSelection(copy), null);

            // Remove the selected text
            removeSelection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Paste clipboard
    private void ctrlV() {
        try {

            // Get clipboard into String
            String addThing = ((String) java.awt.Toolkit.getDefaultToolkit().getSystemClipboard()
                    .getData(java.awt.datatransfer.DataFlavor.stringFlavor));

            // Remove any selected text
            removeSelection();

            // Insert clipboard into text at typepos
            text = text.substring(0, typePos) + addThing + text.substring(typePos, text.length());

            // Adjust typepos to be at the end of the pasted text
            typePos += addThing.length();

            // Adjust the selectpos to the typepos to make sure nothing
            // is selected
            selectPos = typePos;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Put the typed char into the text
    private void typeKey(char key) {

        // To prevent arrayindexoutofbounds exceptions
        if (text.length() == 0) {
            selectPos = typePos;
        }

        if (key == '}') {
            String[] s = text.substring(0, typePos).split("\n");
            if (s[s.length - 1].contains("  ") && s[s.length - 1].trim().length() == 0) {
                text = text.substring(0, typePos - 2) + text.substring(typePos, text.length());
                typePos -= 2;
            }
        }

        // Put the char at the typepos in the text.
        text = text.substring(0, Math.max(typePos, 0)) + key + text.substring(Math.max(typePos, 0), text.length());
        typePos++; // Also adjust typepos
    }

    // Remove the entire selection
    private void removeSelection() {
        if (selectPos > typePos) {
            text = text.substring(0, Math.max(0, typePos)) + text.substring(Math.max(selectPos, 0), text.length());
        } else if (selectPos < typePos) {
            text = text.substring(0, Math.max(0, selectPos)) + text.substring(Math.max(typePos, 0), text.length());
            typePos = selectPos;
        }
    }

    // Remove a single character from the text before the typepos
    private void backspace() {
        text = text.substring(0, Math.max(0, typePos - 1)) + text.substring(typePos, text.length());
        typePos--; // Also adjust the typepos
    }

    // Remove a single character from the text after the typepos
    private void delete() {
        text = text.substring(0, Math.max(0, typePos))
                + text.substring(Math.min(typePos + 1, text.length()), text.length());
    }

    // Add a double space, since tab doesn't work with processing's text() method
    private void tab() {
        text = text.substring(0, typePos) + "  " + text.substring(typePos, text.length());
        typePos += 2; // Also adjust the typepos
    }

    // This auto tabs if a syntax is loaded
    private void enter() {
        text = text.substring(0, typePos) + "\n" + text.substring(typePos, text.length());
        typePos += 1; // Also adjust the typepos
    }

    // Calculates the index left from index "cur" when pressing ctrl.
    // This is divided into 2 classes: 1. [a-zA-Z0-9] and 2. [^a-zA-Z0-9],
    // so if the current class is (1) then the returned index will be the
    // closest letter on the left that is from class (2) and vice versa.
    private int moveLeftCtrlIndex(int cur) {

        // Make sure the index is within the text boundaries.
        cur = constrain(cur, 0, text.length());

        // Get all lines from beginning of text up until current index.
        // add a space at the end so when the typepos is at the beginning
        // of a line it doesn't result in an empty String which means it
        // wont be in the final String[].
        String[] l = (text.substring(0, cur) + " ").split("\n");

        // The current line is the last one in the array of lines.
        String current = l[l.length - 1];
        int currentIndex = cur;

        // Go to previous line if length is 1, this happens when the typepos
        // is at the beginning of a line, so doing CTRL left wraps to previous line.
        // We check for length 1 and not 0 because we added a space.
        if (current.length() == 1) {
            currentIndex--;
            return currentIndex;
        }

        // Check from which class the current char is.
        if ((current.charAt(current.length() - 2) + "").replaceAll("[a-zA-Z0-9]", "").length() == 0) {

            // Go through each character in order and see if it's the other class
            for (int i = current.length() - 2; i >= 0; i--) {

                // If it has found a char from the other class, it just breaks out of the
                // for-loop, leaving the currentIndex variable at the right value.
                if ((current.charAt(i) + "").replaceAll("[^a-zA-Z0-9]", "").length() == 0) {
                    break;
                }
                currentIndex--;
            }
        } else {

            // Go through each character in order and see if it's the other class
            for (int i = current.length() - 2; i >= 0; i--) {

                // If it has found a char from the other class, it just breaks out of the
                // for-loop, leaving the currentIndex variable at the right value.
                if ((current.charAt(i) + "").replaceAll("[a-zA-Z0-9]", "").length() == 0) {
                    break;
                }
                currentIndex--;
            }
        }

        // Return the found index.
        return currentIndex;
    }

    // Calculates the index right from index "cur" when pressing ctrl.
    // Same as left one: divided into 2 classes: 1. [a-zA-Z0-9] and 2. [^a-zA-Z0-9],
    // so if the current class is (1) then the returned index will be the
    // closest letter on the right that is from class (2) and vice versa.
    private int moveRightCtrlIndex(int cur) {

        // To prevent some weird exceptions just return the cur
        // if it's the last character in the text.
        if (cur == text.length()) {
            return cur;
        }

        // Make sure the index is within the text boundaries.
        cur = constrain(cur, 0, text.length());

        // Get all lines from current up until the end of the text.
        String[] l = text.substring(cur, text.length()).split("\n");

        // If there's at least 1 line, set that as String current, else
        // just place an empty string as current;
        String current = l.length > 0 ? l[0] : "";
        int currentIndex = cur;

        // Go to next line if length is 0, this happens when the typepos
        // is at the end of a line, so doing CTRL right wraps to next line.
        if (current.length() == 0) {
            currentIndex++;
            return currentIndex;
        }

        // Check from which class the current char is.
        if ((current.charAt(0) + "").replaceAll("[a-zA-Z0-9]", "").length() == 0) {

            // Go through each character in order and see if it's the other class
            for (int i = 0; i < current.length(); i++) {

                // If it has found a char from the other class, it just breaks out of the
                // for-loop, leaving the currentIndex variable at the right value.
                if ((current.charAt(i) + "").replaceAll("[^a-zA-Z0-9\n]", "").length() == 0) {
                    break;
                }
                currentIndex++;
            }
        } else if (current.charAt(0) != '\n') {

            // Go through each character in order and see if it's the other class
            for (int i = 0; i < current.length(); i++) {

                // If it has found a char from the other class, it just breaks out of the
                // for-loop, leaving the currentIndex variable at the right value.
                if ((current.charAt(i) + "").replaceAll("[a-zA-Z0-9]", "").length() == 0) {
                    break;
                }
                currentIndex++;
            }
        }

        // Return the found index.
        return currentIndex;
    }
}
