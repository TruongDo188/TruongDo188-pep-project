package Service;
import DAO.MessageDAO;
import Model.Message;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;
    
    public MessageService() {
        this.messageDAO = new MessageDAO();
    }
    public Message createMessage(Message message) {
         if (message.getMessage_text() == null || message.getMessage_text().isBlank()
            || message.getMessage_text().length() > 255 
            || !messageDAO.userExists(message.getPosted_by())) {
                return null;
    }
            return messageDAO.insert(message); 
        
}
    public List<Message> getAllMessages() {
        return messageDAO.findAll();
    }
    public Message getMessageById(int messageId) {
        return messageDAO.findById(messageId);
    }
    public Message deleteMessageById(int messageId) {
        return messageDAO.deleteById(messageId);
    }
    public Message updateMessage(int messageId, String newText) {
        if (newText == null || newText.isBlank() || newText.length() > 255) {
            return null;
        }
        return messageDAO.updateTextById(messageId, newText); 
    }
    public List<Message> getMessagesByAccountId(int accountId) {
        return messageDAO.findByAccountId(accountId);
    }

}
