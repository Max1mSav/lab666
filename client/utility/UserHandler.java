package client.utility;

import data.Coordinates;
import data.Person;
import data.TicketType;
import exceptions.CommandUsageException;
import exceptions.IncorrectInputException;
import exceptions.IncorrectInputScriptException;
import exceptions.RecursionException;
import interaction.Outputer;
import interaction.Request;
import interaction.ResponseCode;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;
import server.commands.TicketObj;

public class UserHandler {
    private final int maxRewriteAttempts = 1;
    private Scanner userScanner;
    private Stack<File> scriptStack = new Stack();
    private Stack<Scanner> scannerStack = new Stack();

    public UserHandler(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    public Request handle(ResponseCode serverResponseCode) {
        String[] userCommand = new String[]{"", ""};
        int rewriteAttempts = 0;

        while(true) {
            try {
                ProcessingCode processingCode;
                do {
                    try {
                        if (this.fileMode() && (serverResponseCode == ResponseCode.ERROR || serverResponseCode == ResponseCode.SERVER_EXIT)) {
                            throw new IncorrectInputScriptException();
                        }

                        while(this.fileMode() && !this.userScanner.hasNextLine()) {
                            this.userScanner.close();
                            this.userScanner = (Scanner)this.scannerStack.pop();
                            Outputer.println("Возвращаюсь к скрипту '" + ((File)this.scriptStack.pop()).getName() + "'...");
                        }

                        String userInput;
                        if (this.fileMode()) {
                            userInput = this.userScanner.nextLine();
                            if (!userInput.isEmpty()) {
                                Outputer.print("$");
                                Outputer.println(userInput);
                            }
                        } else {
                            Outputer.print("$");
                            userInput = this.userScanner.nextLine();
                        }

                        userCommand = (userInput.trim() + " ").split(" ", 2);
                        userCommand[1] = userCommand[1].trim();
                    } catch (IllegalStateException | NoSuchElementException var13) {
                        Outputer.println();
                        Outputer.printerror("Произошла ошибка при вводе команды!");
                        userCommand = new String[]{"", ""};
                        ++rewriteAttempts;
                        if (rewriteAttempts >= 1) {
                            Outputer.printerror("Превышено количество попыток ввода!");
                            System.exit(0);
                        }
                    } catch (IncorrectInputScriptException var14) {
                        Outputer.println("Неправильно введены данные для скрипта");
                    }

                    processingCode = this.processCommand(userCommand[0], userCommand[1]);
                } while(processingCode == ProcessingCode.ERROR && !this.fileMode());

                if (userCommand[0].isEmpty()) {
                    continue;
                }

                try {
                    if (this.fileMode() && (serverResponseCode == ResponseCode.ERROR || processingCode == ProcessingCode.ERROR)) {
                        throw new IncorrectInputScriptException();
                    }

                    switch(processingCode) {
                        case OBJECT:
                            TicketObj ticketAdd = this.generateTicketAdd();
                            return new Request(userCommand[0], userCommand[1], ticketAdd);
                        case UPDATE_OBJECT:
                            TicketObj ticketUpdate = this.generateTicketUpdate();
                            return new Request(userCommand[0], userCommand[1], ticketUpdate);
                        case SCRIPT:
                            File scriptFile = new File(userCommand[1]);
                            if (!scriptFile.exists()) {
                                throw new FileNotFoundException();
                            }

                            if (!this.scriptStack.isEmpty() && this.scriptStack.search(scriptFile) != -1) {
                                throw new RecursionException();
                            }

                            this.scannerStack.push(this.userScanner);
                            this.scriptStack.push(scriptFile);
                            this.userScanner = new Scanner(scriptFile);
                            Outputer.println("Выполняю скрипт '" + scriptFile.getName() + "'...");
                    }
                } catch (FileNotFoundException var9) {
                    Outputer.printerror("Файл со скриптом не найден!");
                } catch (RecursionException var10) {
                    Outputer.printerror("Скрипты не могут вызываться рекурсивно!");
                    throw new IncorrectInputScriptException();
                } catch (IncorrectInputScriptException var11) {
                    Outputer.println("Неправильно введены данные для скрипта");
                } catch (IncorrectInputException var12) {
                    Outputer.println("Неправильно введены данные");
                }
            } catch (IncorrectInputScriptException var15) {
                Outputer.printerror("Выполнение скрипта прервано!");

                while(!this.scannerStack.isEmpty()) {
                    this.userScanner.close();
                    this.userScanner = (Scanner)this.scannerStack.pop();
                }

                this.scriptStack.clear();
                return new Request();
            }

            return new Request(userCommand[0], userCommand[1]);
        }
    }

    private ProcessingCode processCommand(String command, String commandArgument) {
        try {
            byte var4 = -1;
            switch(command.hashCode()) {
                case -1145661232:
                    if (command.equals("sum_of_health")) {
                        var4 = 14;
                    }
                    break;
                case -1020102443:
                    if (command.equals("execute_script")) {
                        var4 = 9;
                    }
                    break;
                case -838846263:
                    if (command.equals("update")) {
                        var4 = 5;
                    }
                    break;
                case -759016294:
                    if (command.equals("server_exit")) {
                        var4 = 17;
                    }
                    break;
                case -526296440:
                    if (command.equals("remove_by_id")) {
                        var4 = 6;
                    }
                    break;
                case 0:
                    if (command.equals("")) {
                        var4 = 0;
                    }
                    break;
                case 96417:
                    if (command.equals("add")) {
                        var4 = 4;
                    }
                    break;
                case 3127582:
                    if (command.equals("exit")) {
                        var4 = 10;
                    }
                    break;
                case 3198785:
                    if (command.equals("help")) {
                        var4 = 1;
                    }
                    break;
                case 3237038:
                    if (command.equals("info")) {
                        var4 = 2;
                    }
                    break;
                case 3522941:
                    if (command.equals("save")) {
                        var4 = 8;
                    }
                    break;
                case 3529469:
                    if (command.equals("show")) {
                        var4 = 3;
                    }
                    break;
                case 94746189:
                    if (command.equals("clear")) {
                        var4 = 7;
                    }
                    break;
                case 926934164:
                    if (command.equals("history")) {
                        var4 = 13;
                    }
                    break;
                case 982730559:
                    if (command.equals("remove_greater")) {
                        var4 = 12;
                    }
                    break;
                case 1022623060:
                    if (command.equals("max_by_melee_weapon")) {
                        var4 = 15;
                    }
                    break;
                case 1760283022:
                    if (command.equals("add_if_min")) {
                        var4 = 11;
                    }
                    break;
                case 1898601276:
                    if (command.equals("filter_by_weapon_type")) {
                        var4 = 16;
                    }
            }

            switch(var4) {
                case 0:
                    return ProcessingCode.ERROR;
                case 1:
                    if (!commandArgument.isEmpty()) {
                        throw new CommandUsageException();
                    }
                    break;
                case 2:
                    if (!commandArgument.isEmpty()) {
                        throw new CommandUsageException();
                    }
                    break;
                case 3:
                    if (!commandArgument.isEmpty()) {
                        throw new CommandUsageException();
                    }
                    break;
                case 4:
                    if (!commandArgument.isEmpty()) {
                        throw new CommandUsageException("{element}");
                    }

                    return ProcessingCode.OBJECT;
                case 5:
                    if (commandArgument.isEmpty()) {
                        throw new CommandUsageException("<ID> {element}");
                    }

                    return ProcessingCode.UPDATE_OBJECT;
                case 6:
                    if (commandArgument.isEmpty()) {
                        throw new CommandUsageException("<ID>");
                    }
                    break;
                case 7:
                    if (!commandArgument.isEmpty()) {
                        throw new CommandUsageException();
                    }
                    break;
                case 8:
                    if (!commandArgument.isEmpty()) {
                        throw new CommandUsageException();
                    }
                    break;
                case 9:
                    if (commandArgument.isEmpty()) {
                        throw new CommandUsageException("<file_name>");
                    }

                    return ProcessingCode.SCRIPT;
                case 10:
                    if (!commandArgument.isEmpty()) {
                        throw new CommandUsageException();
                    }
                    break;
                case 11:
                    if (!commandArgument.isEmpty()) {
                        throw new CommandUsageException("{element}");
                    }

                    return ProcessingCode.OBJECT;
                case 12:
                    if (!commandArgument.isEmpty()) {
                        throw new CommandUsageException("{element}");
                    }

                    return ProcessingCode.OBJECT;
                case 13:
                    if (!commandArgument.isEmpty()) {
                        throw new CommandUsageException();
                    }
                    break;
                case 14:
                    if (!commandArgument.isEmpty()) {
                        throw new CommandUsageException();
                    }
                    break;
                case 15:
                    if (!commandArgument.isEmpty()) {
                        throw new CommandUsageException();
                    }
                    break;
                case 16:
                    if (commandArgument.isEmpty()) {
                        throw new CommandUsageException("<weapon_type>");
                    }
                    break;
                case 17:
                    if (!commandArgument.isEmpty()) {
                        throw new CommandUsageException();
                    }
                    break;
                default:
                    Outputer.println("Команда '" + command + "' не найдена. Наберите 'help' для справки.");
                    return ProcessingCode.ERROR;
            }
        } catch (CommandUsageException var5) {
            if (var5.getMessage() != null) {
                command = command + " " + var5.getMessage();
            }

            Outputer.println("Использование: '" + command + "'");
            return ProcessingCode.ERROR;
        }

        return ProcessingCode.OK;
    }

    private TicketObj generateTicketAdd() throws IncorrectInputException {
        TicketChecker ticketChecker = new TicketChecker(this.userScanner);
        if (this.fileMode()) {
            ticketChecker.setFileMode();
        }

        return new TicketObj(ticketChecker.checkName(), ticketChecker.returnCoordinates(), ticketChecker.checkPrice(), ticketChecker.checkTicketType(), ticketChecker.returnPerson(), ticketChecker.checkDiscount());
    }

    private TicketObj generateTicketUpdate() throws IncorrectInputException {
        TicketChecker ticketChecker = new TicketChecker(this.userScanner);
        if (this.fileMode()) {
            ticketChecker.setFileMode();
        }

        String name = ticketChecker.wantToChange("Хотите изменить название билета?") ? ticketChecker.checkName() : null;
        Coordinates coordinates = ticketChecker.wantToChange("Хотите изменить координаты билета?") ? ticketChecker.returnCoordinates() : null;
        double price = ticketChecker.wantToChange("Хотите изменить цену билета?") ? ticketChecker.checkPrice() : -1.0D;
        int discount = ticketChecker.wantToChange("Хотите изменить скидку на билет?") ? ticketChecker.checkDiscount() : null;
        TicketType ticketType = ticketChecker.wantToChange("Хотите изменить тип билета?") ? ticketChecker.checkTicketType() : null;
        Person person = ticketChecker.wantToChange("Хотите изменить владельца билета?") ? ticketChecker.returnPerson() : null;
        return new TicketObj(name, coordinates, price, ticketType, person, discount);
    }

    private boolean fileMode() {
        return !this.scannerStack.isEmpty();
    }
}

