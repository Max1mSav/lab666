package server.utility;
import interaction.*;


/**
 * Handles requests.
 */
public class RequestHandler {
    private CommandManager commandManager;

    public RequestHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    /**
     * Handles requests.
     *
     * @param request Request to be processed.
     * @return Response to request.
     */
    public Response handle(Request request) {
        commandManager.addToHistory(request.getCommandName());
        ResponseCode responseCode = executeCommand(request.getCommandName(), request.getCommandStringArgument(),
                request.getCommandObjectArgument());
        return new Response(responseCode, ResponseOutputer.getAndClear());
    }

    /**
     * Executes a command from a request.
     *
     * @param command               Name of command.
     * @param commandStringArgument String argument for command.
     * @param commandObjectArgument Object argument for command.
     * @return Command execute status.
     */
    private ResponseCode executeCommand(String command, String commandStringArgument,
                                        Object commandObjectArgument) {
        switch (command) {
            case "":
                break;
            case "help":
                if (!commandManager.help(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "info":
                if (!commandManager.info(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "show":
                if (!commandManager.show(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "add":
                if (!commandManager.add(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "update":
                if (!commandManager.update(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "remove_by_id":
                if (!commandManager.removeById(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "clear":
                if (!commandManager.clear(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "save":
                if (!commandManager.save(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "execute_script":
                if (!commandManager.executeScript(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "exit":
                if (!commandManager.exit(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "add_if_max":
                if (!commandManager.addIfMax(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "remove_greater":
                if (!commandManager.removeGreater(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "history":
                if (!commandManager.history(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "filter_contains_name":
                if (!commandManager.filterContainsName(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "filter_less_than_price":
                if (!commandManager.filterLessPrice(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "print_field_ascending_discount":
                if (!commandManager.discountInAscendingOrder(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "server_exit":
                if (!commandManager.serverExit(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                return ResponseCode.SERVER_EXIT;
            default:
                ResponseOutputer.appendln("?????????????? '" + command + "' ???? ??????????????. ???????????????? 'help' ?????? ??????????????.");
                return ResponseCode.ERROR;
        }
        return ResponseCode.OK;
    }
}