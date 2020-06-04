package javapp.objects.text;

import javapp.core.Functions;

public class DataContainer<T> {

    // Editable
    private boolean editable;

    // The content of this container
    private String content;
    private DataType<T> parser;

    // The selection and position where you type
    private int selectStrt;
    private int selectStop;

    /**
     * Constructor.
     */
    public DataContainer(DataType<T> t) {
        this("", true, t);
    }

    /**
     * Constructor.
     * 
     * @param ed editable
     */
    public DataContainer(boolean ed, DataType<T> t) {
        this("", ed, t);
    }

    /**
     * Constructor.
     *
     * @param c content
     */
    public DataContainer(String c, DataType<T> t) {
        this(c, true, t);
    }

    /**
     * Constructor.
     * 
     * @param c  content
     * @param ed editable
     */
    public DataContainer(String c, boolean ed, DataType<T> t) {
        content = c;
        editable = ed;
        setSelectStart(length());
        setSelectStop(length());
        parser = t;
    }

    /**
     * Returns the content of the container.
     * 
     * @return content
     */
    public T getContent() {
        return parser.parse(content);
    }

    /**
     * Sets the content of this container.
     * 
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
        constrainIndice();
    }

    /**
     * Returns the last index of the selection.
     * 
     * @return last index of the selection.
     */
    public int getSelectStop() {
        return selectStop;
    }

    /**
     * Sets the last index of the selection.
     * 
     * @param selectStop last index
     */
    public void setSelectStop(int selectStop) {
        this.selectStop = Functions.constrain(selectStop, 0, length());
    }

    /**
     * Returns the first index of the selection.
     * 
     * @return first index
     */
    public int getSelectStart() {
        return selectStrt;
    }

    /**
     * Sets the first index of the selection.
     * 
     * @param selectStrt first index
     */
    public void setSelectStart(int selectStrt) {
        this.selectStrt = Functions.constrain(selectStrt, 0, length());
    }

    /**
     * Returns the lowest index from selectStop and selectStart.
     * 
     * @return lowest index
     */
    public int lowestSelectIndex() {
        return selectStrt < selectStop ? selectStrt : selectStop;
    }

    /**
     * Returns the highest index from selectStop and selectStart.
     * 
     * @return highest index
     */
    public int highestSelectIndex() {
        return selectStrt < selectStop ? selectStop : selectStrt;
    }

    /**
     * Selects from index a to b.
     * 
     * @param a begin
     * @param b end
     */
    public void select(int a, int b) {
        setSelectStart(a);
        setSelectStop(b);
    }

    /**
     * Sets the type index.
     * 
     * @param a index
     */
    public void setTypeIndex(int a) {
        select(a, a);
    }

    /**
     * Returns the type index.
     * 
     * @return index
     */
    public int getTypeIndex() {
        return selectStop;
    }

    /**
     * Returns the length of the string in this container.
     * 
     * @return length of string
     */
    public int length() {
        return content.length();
    }

    /**
     * True when this container is editable.
     * 
     * @return true when editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * Returns the text currently selected.
     * 
     * @return selection
     */
    public String getSelection() {
        return content.substring(lowestSelectIndex(), highestSelectIndex());
    }

    /**
     * Returns true if some text is selected.
     * 
     * @return true when there's a selection
     */
    public boolean selection() {
        return selectStop != selectStrt;
    }

    /**
     * Removes the selected text from the content. And updates the selection.
     */
    public void removeSelection() {
        insert("");
    }

    /**
     * Inserts a string at the type index, also removes the selection.
     * 
     * @param add string
     */
    public void insert(String add) {
        content = content.substring(0, lowestSelectIndex()) + add + content.substring(highestSelectIndex());
        setTypeIndex(lowestSelectIndex() + add.length());
    }

    /**
     * Inserts a character at the type index, also removes the selection.
     * 
     * @param key character
     */
    public void insert(char key) {
        insert("" + key);
    }

    /**
     * Delete key, removes the character after the type index.
     */
    public void delete() {
        if (selection()) {
            removeSelection();
        } else if (highestSelectIndex() + 1 < length()) {
            content = content.substring(0, lowestSelectIndex()) + content.substring(highestSelectIndex() + 1);
            setTypeIndex(lowestSelectIndex());
        }
    }

    /**
     * Backspace key, removes the character before the type index.
     */
    public void backspace() {
        if (selection()) {
            removeSelection();
        } else if (lowestSelectIndex() > 0) {
            content = content.substring(0, lowestSelectIndex() - 1) + content.substring(highestSelectIndex());
            setTypeIndex(lowestSelectIndex() - 1);
        }
    }

    /**
     * Removes a substring of the content from index start to index end.
     * 
     * @param start start index
     * @param end   end index
     */
    public void remove(int start, int end) {
        start = Functions.constrain(start, 0, length());
        end = Functions.constrain(end, 0, length());

        content = content.substring(0, start) + content.substring(end);
        setTypeIndex(start);
    }

    /**
     * Returns the content as a String
     * 
     * @return content as string
     */
    public String getContentAsString() {
        return content;
    }

    // Makes sure the indice are within bounds of the string
    private void constrainIndice() {
        selectStrt = Functions.constrain(selectStrt, 0, content.length());
        selectStop = Functions.constrain(selectStop, 0, content.length());
    }
}
