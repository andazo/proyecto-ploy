import javax.swing.JOptionPane;

public class Message {
    
    /**
     * 
     */
    public Message() {

    }

    /**
     * @param message
     */
    public void printMessage(Object message) {
        JOptionPane.showMessageDialog(null, message);
    }
    
    /**
     * @param message
     */
    public void printSimpleMessage(String message) {
    	JOptionPane.showMessageDialog(null, message);
    }

    /**
     * @param message
     * @param title
     */
    public void printMessageWithTitle(Object message, String title) {
        JOptionPane.showMessageDialog(null, message, title, -1, null);
    }

    /**
     * @param showMessage
     * @return
     */
    public String inputMessage(String showMessage) {
        return JOptionPane.showInputDialog(showMessage);
    }

    /**
     * @param showMessage
     * @param title
     * @param object
     * @param obj
     * @return
     */
    public String inputQuestionMessage(String showMessage, String title, Object[] object, Object obj) {
        return (String) JOptionPane.showInputDialog(null, showMessage, title, JOptionPane.QUESTION_MESSAGE, null, object, obj);
    }
    
    /**
     * @param showMessage
     * @param title
     * @param options
     * @return
     */
    public int inputMessageWithOptions(String showMessage, String title, String[] options) {
    	return JOptionPane.showOptionDialog(null, showMessage, title, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    }
}
