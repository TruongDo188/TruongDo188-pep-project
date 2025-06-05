package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::handleRegister);
        app.post("/login", this::handleLogin);
        app.post("/messages", this::handleCreateMessage);
        app.get("/messages", this::handleGetAllMessages);
        app.get("/messages/{message_id}", this::handleGetMessageById);
        app.delete("/messages/{message_id}", this::handleDeleteMessageById);
        app.patch("/messages/{message_id}", this::handleUpdateMessageById);
        app.get("/accounts/{account_id}/messages", this::handleGetMessagesByAccountId);
        return app;
    }
    private MessageService messageService = new MessageService();
    
    private void handleCreateMessage(Context ctx) {
        Message message = ctx.bodyAsClass(Message.class);

        Message createdMessage = messageService.createMessage(message);

        if (createdMessage != null) {
            ctx.json(createdMessage);
            ctx.status(200);
        } else {
            ctx.status(400);
        }
    }
    private void handleGetAllMessages(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
        ctx.status(200);
    }
    private void handleRegister(Context ctx) {
        Account account = ctx.bodyAsClass(Account.class);

        boolean isUsernameBlank = account.getUsername() == null || account.getUsername().isBlank();
        boolean isPasswordTooShort= account.getPassword() == null || account.getPassword().length() < 4;
        boolean isDuplicateUsername = accountService.usernameExists(account.getUsername());

            if (isUsernameBlank || isPasswordTooShort || isDuplicateUsername) {
                ctx.status(400);
                return;
            }
                Account created = accountService.register(account);
                if (created == null) {
                ctx.status(400);
            } else {
                ctx.json(created);
                ctx.status(200);
            }
            }
    private void handleDeleteMessageById(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message deleted = messageService.deleteMessageById(messageId);

        if (deleted != null) {
            ctx.json(deleted);
        } else {
            ctx.result("");
        }
        ctx.status(200);
    }
    private void handleUpdateMessageById(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message incoming = ctx.bodyAsClass(Message.class);
        
        Message updated = messageService.updateMessage(messageId, incoming.getMessage_text());

        if (updated != null) {
            ctx.json(updated);
            ctx.status(200);
        } else {
            ctx.status(400);
        }
    }
    private void handleGetMessageById(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
    
        if (message != null) {
            ctx.json(message);
        } else {
            ctx.result("");
        }
        ctx.status(200);
    }
    private void handleGetMessagesByAccountId(Context ctx) {
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByAccountId(accountId);
        ctx.json(messages);
        ctx.status(200);
    }
    private void handleLogin(Context ctx) {
        Account credentials = ctx.bodyAsClass(Account.class);
        Account matchedAccount= accountService.login(credentials);

        if (matchedAccount != null) {
            ctx.json(matchedAccount); 
            ctx.status(200);
        } else { 
            ctx.status(401);
        }
    }
    private AccountService accountService;

    public SocialMediaController() {
        this.accountService = new AccountService();
    }
    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


}