package duke.command;

import duke.DateParserService;
import duke.DukeList;
import duke.Storage;
import duke.Ui;
import duke.exceptions.DukeException;
import duke.task.Event;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * The AddEventCommand class represents a command to add a new Event task to the list.
 */
public class AddEventCommand extends Command{
    /**
     * Constructs a new AddEventCommand object with the specified command.
     *
     * @param command The input command string.
     */
    public AddEventCommand(String command) {
        super(command);
    }

    /**
     * Executes the add Event command, adding a new Event task to the list.
     *
     * @param tasks   The DukeList containing the list of tasks.
     * @param ui      The Ui object for user interaction.
     * @param storage The Storage object for file storage.
     * @throws DukeException If there is an error during execution.
     */
    @Override
    public void execute(DukeList tasks, Ui ui, Storage storage) throws DukeException {
        String[] inputs = this.command.split(" ", 2);
        Event event;
        try {
            String[] from = inputs[1].split("/from", 2);
            String[] to = from[1].split("/to", 2);
            LocalDate f = DateParserService.parseDate(to[0]);
            LocalDate t = DateParserService.parseDate(to[1]);
            event = new Event(from[0], f, t);
        } catch (ArrayIndexOutOfBoundsException | DateTimeParseException e) {
            if (e instanceof ArrayIndexOutOfBoundsException) {
                throw new DukeException("Invalid Event format");
            }
            throw new DukeException("Invalid date format");
        }
        tasks.add(event);
        ui.addToList(event, tasks.getSize());
    }
}
