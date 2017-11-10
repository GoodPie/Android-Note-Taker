package britton.brandyn.notetaking;

import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;

import britton.brandyn.notetaking.models.NoteItem;

import static junit.framework.Assert.fail;

public class NoteItemUnitTest {

    private NoteItem testNote;

    @Before
    public void setupTestNote() {
        this.testNote = new NoteItem("", "", null);
    }

    @Test
    public void validateTitleLength() throws Exception {
        // Create title that is greater than the max title length
        String testTitle = new String(new char[NoteItem.MAX_TITLE_LENGTH + 1])
                .replace("\0", "-");

        try {
            // Test the setter
            testNote.setTitle(testTitle);
            fail();
        } catch (InvalidParameterException e) {
            // Passed setter test
        }

        try {
            // Test the constructor
            NoteItem newNote = new NoteItem(testTitle, "", null);
            fail();
        } catch (InvalidParameterException e) {
            // Passed constructor test
        }
    }


}
