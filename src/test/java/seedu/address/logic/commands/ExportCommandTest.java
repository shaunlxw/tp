package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalNetConnect;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code ExportCommand}.
 */
public class ExportCommandTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "ExportCommandTest");
    private static final Path NON_EXISTENT_FILE = TEST_DATA_FOLDER.resolve("nonexistent.csv");

    private final Model model = new ModelManager(getTypicalNetConnect(), new UserPrefs());

    @Test
    public void constructor_nullFilename_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ExportCommand(null));
    }

    @Test
    public void execute_exportSuccess() {
        String filename = "test_export.csv";
        Path filePath = Paths.get(filename);
        ExportCommand exportCommand = new ExportCommand(filename);
        String expectedMessage = ExportCommand.MESSAGE_SUCCESS + filename;

        Model expectedModel = new ModelManager(model.getNetConnect(), new UserPrefs());
        assertCommandSuccess(exportCommand, model, expectedMessage, expectedModel);
        assertTrue(filePath.toFile().exists());

        // Clean up
        filePath.toFile().delete();
    }

    @Test
    public void execute_exportFailure_emptyList() {
        ExportCommand exportCommand = new ExportCommand();
        String expectedMessage = ExportCommand.MESSAGE_FAILURE_EMPTY_LIST;

        Model emptyModel = new ModelManager();
        assertCommandFailure(exportCommand, emptyModel, expectedMessage);
    }

    @Test
    public void execute_exportFailure_fileWriteError() {
        String filename = NON_EXISTENT_FILE.toString();
        ExportCommand exportCommand = new ExportCommand(filename);
        String expectedMessage = ExportCommand.MESSAGE_FAILURE_FILE_WRITE;

        assertCommandFailure(exportCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        ExportCommand exportCommand1 = new ExportCommand("file1.csv");
        ExportCommand exportCommand2 = new ExportCommand("file2.csv");

        // Same object -> returns true
        assertEquals(exportCommand1, exportCommand1);

        // Same values -> returns true
        ExportCommand exportCommand1Copy = new ExportCommand("file1.csv");
        assertEquals(exportCommand1, exportCommand1Copy);

        // Different types -> returns false
        assertNotEquals(exportCommand1, 1);

        // Null -> returns false
        assertNotEquals(exportCommand1, null);

        // Different person -> returns false
        assertNotEquals(exportCommand1, exportCommand2);
    }
}
