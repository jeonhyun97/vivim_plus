package project_Team7;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;

import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.util.Pair;
import com.intellij.ui.awt.RelativePoint;

import org.jetbrains.annotations.NotNull;


import project_Team7.HandlerMap.TypedHandlerMap;
import project_Team7.Handlers.InsertModeHandler;
import project_Team7.Handlers.TypedHandler;

import java.util.Map;

public class EditorTypedHandler implements TypedActionHandler {

    private static char storedChar = 'x';
    private boolean hasDocumentListener = false;
    private static String recentDeletedString = null;
    private static Integer multiExecute = 0;

    private Map<Pair<String, Character>, TypedHandler> handlerMap = TypedHandlerMap.getMap();

    public static String getRecentDeletedString() {
        return recentDeletedString;
    }

    public void setRecentDeletedString(String s) {
        recentDeletedString = s;
    }

    public static void setStoredChar(char c){
        storedChar = c;
    }

    public static char getStoredChar(){
        return storedChar;
    }

    public static Integer getMultiExecute() {
        return multiExecute;
    }

    public static void setMultiExecute(Integer multiExecute) {
        EditorTypedHandler.multiExecute = multiExecute;
    }

    /** End of getter, setters */


    /**
     *  Execute for the every keyboard input, except the input of
     *  special keys like ESC, BACKSPACE, AND ENTER
     */
    @Override
    public void execute(@NotNull Editor editor, char charTyped, @NotNull DataContext dataContext) {
        if(!hasDocumentListener) {    // prevent more than one listeners to be added
            editor.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void documentChanged(@NotNull DocumentEvent event) {
                    setRecentDeletedString(event.getOldFragment().toString());
                }
            });
            VIMMode.setMode(VIMMode.modeType.NORMAL);
            hasDocumentListener = true;
        }
        /** add listener to detect ESC, BACKSPACE, and ENTER input */
        InsertModeHandler.addKeyListener(editor);

        char key = charTyped;
        if(VIMMode.getModeToString().equals("INSERT MODE"))
            key = ' ';
        if(handlerMap.containsKey(new Pair<>(VIMMode.getModeToString(),key)))
            handlerMap.get(new Pair<>(VIMMode.getModeToString(),key)).execute(editor, charTyped, dataContext);

        /** Set correct cursor shape for each mode */
        setProperCursorShape(editor);
    }

    /**
     * In modern vim plugin, there exists a convention which represents cursor
     * with single line in the INSERT MODE, and represents by block in the
     * NORMAL MODE. This method sets proper cursor types depending on the current
     * MODE.
     * @param editor Opened editor
     */

    private void setProperCursorShape(Editor editor) {
        if(VIMMode.getModeToString().equals("INSERT MODE")) {
            editor.getSettings().setBlockCursor(false);
        }
        else {
            editor.getSettings().setBlockCursor(true);
        }
    }

}