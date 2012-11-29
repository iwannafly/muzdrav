package ru.nkz.ivcgzo.clientInfomat;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Window;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class OptionDialog extends JDialog {
    private static final long serialVersionUID = 5288093811733141959L;
    public static int YES_OPTION = 1;
    public static int NO_OPTION = 2;
    private String question;
    private String title;
    private ComponentOrientation componentOrientation;

    public OptionDialog(Component parentComponent, String question, String title) {
        this.question = question;
        this.title = title;
//        updateUI();
    }

    public static int showInputOptionDialog(
            Component parentComponent,String question, String title) {
        OptionDialog optionDialog = new OptionDialog(parentComponent, question, title);
        optionDialog.setComponentOrientation(parentComponent.getComponentOrientation());
        JDialog dialog = optionDialog.createDialog(parentComponent, title);
        return NO_OPTION;
    }

    private JDialog createDialog(Component parentComponent, String title2) {
        final JDialog dialog;
        Window window = OptionDialog.getWindowForComponent(parentComponent);
        if (window instanceof Frame) {
            dialog = new JDialog((Frame)window, title, true);
        } else {
            dialog = new JDialog((Dialog)window, title, true);
        }
        return dialog;
    }

    static Window getWindowForComponent(Component parentComponent)
        throws HeadlessException {
        if (parentComponent instanceof Frame || parentComponent instanceof Dialog)
            return (Window)parentComponent;
        return OptionDialog.getWindowForComponent(parentComponent.getParent());
    }
    
    public void setComponentOrientation(ComponentOrientation o) {
        ComponentOrientation oldValue = componentOrientation;
        componentOrientation = o;

        // This is a bound property, so report the change to
        // any registered listeners.  (Cheap if there are none.)
        firePropertyChange("componentOrientation", oldValue, o);

        // This could change the preferred size of the Component.
//        invalidateIfValid();
    }

    

}
