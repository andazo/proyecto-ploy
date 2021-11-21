import javax.swing.JOptionPane;

public class Message {
    
    public Message() {

    }

    public void printMessage(Object message) {
        JOptionPane.showMessageDialog(null, message);
    }
    
    public void printSimpleMessage(String message) {
    	JOptionPane.showMessageDialog(null, message);
    }

    public void printMessageWithTitle(Object message, String title) {
        JOptionPane.showMessageDialog(null, message, title, -1, null);
    }

    public String inputMessage(String showMessage) {
        return JOptionPane.showInputDialog(showMessage);
    }

    public String inputQuestionMessage(String showMessage, String title, Object[] object, Object obj) {
        return (String) JOptionPane.showInputDialog(null, showMessage, title, JOptionPane.QUESTION_MESSAGE, null, object, obj);
    }
    
    public int inputMessageWithOptions(String showMessage, String title, String[] options) {
    	return JOptionPane.showOptionDialog(null, showMessage, title, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    }
}
