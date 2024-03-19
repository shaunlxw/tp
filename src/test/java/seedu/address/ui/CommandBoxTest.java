package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.CommandBox.CommandExecutor;

public class CommandBoxTest {
    private CommandBox commandBox;

    @BeforeAll
    public static void setupJavaFX() {
        // Initialize JavaFX toolkit via Application.launch()
        Thread thread = new Thread(() -> Application.launch(DummyApplication.class));
        thread.setDaemon(true);
        thread.start();

        try {
            // Wait until the JavaFX application is initialized
            Thread.sleep(1000); // Adjust the delay as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Dummy JavaFX Application class
    public static class DummyApplication extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
            // This method is intentionally left blank
        }
    }
    @BeforeEach
    public void setUp() {
        // Initialize CommandBox with a dummy CommandExecutor
        CommandExecutor dummyCommandExecutor = new CommandExecutor() {
            @Override
            public CommandResult execute(String commandText) throws CommandException, ParseException {
                return null; // Dummy implementation
            }
        };
        commandBox = new CommandBox(dummyCommandExecutor);
    }

    @Test
    public void createNewCommandBox_successfullyCreated() {
        assertNotNull(commandBox);
    }

    @Test
    public void setStyleToIndicateCommandFailure_addsErrorStyleClass() {
        ObservableList<String> styleClass = commandBox.getCommandTextField().getStyleClass();
        assertFalse(styleClass.contains(CommandBox.ERROR_STYLE_CLASS));
        commandBox.setStyleToIndicateCommandFailure();

        assertTrue(styleClass.contains(CommandBox.ERROR_STYLE_CLASS));
    }

    @Test
    public void setStyleToDefault_removesErrorStyleClass() {
        ObservableList<String> styleClass = commandBox.getCommandTextField().getStyleClass();
        styleClass.add(CommandBox.ERROR_STYLE_CLASS); // Add ERROR_STYLE_CLASS initially
        assertTrue(styleClass.contains(CommandBox.ERROR_STYLE_CLASS));
        commandBox.setStyleToDefault();

        assertFalse(styleClass.contains(CommandBox.ERROR_STYLE_CLASS));
    }

}
