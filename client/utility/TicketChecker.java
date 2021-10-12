package client.utility;

import data.Coordinates;
import data.Location;
import data.Person;
import data.TicketType;
import exceptions.EmptyException;
import exceptions.IncorrectInputException;
import exceptions.NullInputException;
import exceptions.WrongArgumentInputException;
import interaction.Outputer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TicketChecker {
    private final int MIN_DIsCOUNT = 1;
    private final int MAX_DIsCOUNT = 100;
    private final double MIN_PRICE = 0.0D;
    private final double MIN_HEIGHT = 0.0D;
    private final float MIN_WEIGHT = 0.0F;
    private final float MIN_Y = -588.0F;
    private Scanner userscanner;
    private boolean fileMode;

    public TicketChecker(Scanner userscanner) {
        this.userscanner = userscanner;
        this.fileMode = false;
    }

    public String checkName() throws IncorrectInputException {
        while(true) {
            try {
                Outputer.println("Введите имя: ");
                Outputer.print("> ");
                String name = this.userscanner.nextLine().trim();
                if (this.fileMode) {
                    Outputer.println(name);
                }

                if (name.equals("")) {
                    throw new EmptyException();
                }

                return name;
            } catch (NoSuchElementException var3) {
                Outputer.printerror("Имя не распознано!");
                Outputer.print("> ");
                if (this.fileMode) {
                    throw new IncorrectInputException();
                }
            } catch (EmptyException var4) {
                Outputer.printerror("Имя не может быть пустым");
                Outputer.print("> ");
                if (this.fileMode) {
                    throw new IncorrectInputException();
                }
            }
        }
    }

    public float checkXCoordinates() throws IncorrectInputException {
        while(true) {
            Float x;
            try {
                Outputer.println("Введите координату X:");
                Outputer.print("> ");
                String str = this.userscanner.nextLine().trim();
                if (this.fileMode) {
                    Outputer.println(str);
                }

                x = Float.parseFloat(str);
                if (x.equals((Object)null)) {
                    throw new NullInputException();
                }
            } catch (NoSuchElementException var4) {
                Outputer.printerror("Координата X не распознана!");
                Outputer.print("> ");
                if (!this.fileMode) {
                    continue;
                }

                throw new IncorrectInputException();
            } catch (NumberFormatException var5) {
                Outputer.printerror("Координата X должна быть числом c плавающей запятой!");
                Outputer.print("> ");
                if (!this.fileMode) {
                    continue;
                }

                throw new IncorrectInputException();
            } catch (NullPointerException var6) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
                continue;
            } catch (IllegalStateException var7) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
                continue;
            } catch (NullInputException var8) {
                Outputer.printerror("Координата X не может быть null");
                Outputer.print("> ");
                continue;
            }

            return x;
        }
    }

    public float checkYCoordinates() throws IncorrectInputException {
        while(true) {
            Float y;
            try {
                Outputer.println("Введите координату Y > -588.0: ");
                Outputer.print("> ");
                String str = this.userscanner.nextLine().trim();
                if (this.fileMode) {
                    Outputer.println(str);
                }

                y = Float.parseFloat(str);
                if (y < -588.0F) {
                    throw new WrongArgumentInputException();
                }

                if (y.equals((Object)null)) {
                    throw new NullInputException();
                }
            } catch (NoSuchElementException var4) {
                Outputer.printerror("Координата Y не распознана!");
                Outputer.print("> ");
                if (!this.fileMode) {
                    continue;
                }

                throw new IncorrectInputException();
            } catch (NumberFormatException var5) {
                Outputer.printerror("Координата Y должна быть числом c плавающей запятой!");
                Outputer.print("> ");
                if (!this.fileMode) {
                    continue;
                }

                throw new IncorrectInputException();
            } catch (WrongArgumentInputException var6) {
                Outputer.printerror("Y должен превышать значение равное = -588.0");
                Outputer.print("> ");
                if (!this.fileMode) {
                    continue;
                }

                throw new IncorrectInputException();
            } catch (NullPointerException var7) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
                continue;
            } catch (IllegalStateException var8) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
                continue;
            } catch (NullInputException var9) {
                Outputer.printerror("Координата Y не может быть null");
                Outputer.print("> ");
                continue;
            }

            return y;
        }
    }

    public Coordinates returnCoordinates() throws IncorrectInputException {
        Float x = this.checkXCoordinates();
        Float y = this.checkYCoordinates();
        return new Coordinates(x, y);
    }

    public Double checkPrice() throws IncorrectInputException {
        while(true) {
            try {
                Outputer.println("Введите цену > 0.0: ");
                Outputer.print("> ");
                String str = this.userscanner.nextLine().trim();
                if (this.fileMode) {
                    Outputer.println(str);
                }

                Double price = Double.parseDouble(str);
                if (price < 0.0D) {
                    throw new WrongArgumentInputException();
                }

                return price;
            } catch (NoSuchElementException var4) {
                Outputer.printerror("Цена не распознана!");
                Outputer.print("> ");
                if (this.fileMode) {
                    throw new IncorrectInputException();
                }
            } catch (NumberFormatException var5) {
                Outputer.printerror("Цена должна быть числом c плавающей запятой!");
                Outputer.print("> ");
                if (this.fileMode) {
                    throw new IncorrectInputException();
                }
            } catch (WrongArgumentInputException var6) {
                Outputer.printerror("Цена должна превышать значение равное = 0.0");
                Outputer.print("> ");
                if (this.fileMode) {
                    throw new IncorrectInputException();
                }
            } catch (NullPointerException var7) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            } catch (IllegalStateException var8) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
    }

    public int checkDiscount() throws IncorrectInputException {
        while(true) {
            try {
                Outputer.println("Введите скидку в диапозоне от 1 до 100:");
                Outputer.print("> ");
                String str = this.userscanner.nextLine().trim();
                if (this.fileMode) {
                    Outputer.println(str);
                }

                int discount = Integer.parseInt(str);
                if (discount <= 100 && discount >= 1) {
                    return discount;
                }

                throw new WrongArgumentInputException();
            } catch (NoSuchElementException var4) {
                Outputer.printerror("Скидка не распознана!");
                Outputer.print("> ");
                if (this.fileMode) {
                    throw new IncorrectInputException();
                }
            } catch (NumberFormatException var5) {
                Outputer.printerror("Скидка должна быть целым числом!");
                Outputer.print("> ");
                if (this.fileMode) {
                    throw new IncorrectInputException();
                }
            } catch (WrongArgumentInputException var6) {
                Outputer.printerror("Скидка должна быть в диапозоне от 1 до 100:");
                Outputer.print("> ");
                if (this.fileMode) {
                    throw new IncorrectInputException();
                }
            } catch (NullPointerException var7) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            } catch (IllegalStateException var8) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
    }

    public TicketType checkTicketType() throws IncorrectInputException {
        while(true) {
            try {
                Outputer.println("Список возможных типов: " + TicketType.nameList());
                Outputer.println("Введите тип: ");
                Outputer.print("> ");
                String str = this.userscanner.nextLine().trim();
                if (this.fileMode) {
                    Outputer.println(str);
                }

                TicketType ticketType;
                if (!str.equals((Object)null) && !str.equals("")) {
                    ticketType = TicketType.valueOf(str.toUpperCase());
                } else {
                    ticketType = null;
                }

                return ticketType;
            } catch (NoSuchElementException var4) {
                Outputer.printerror("Тип не распознан!");
                Outputer.print("> ");
                if (this.fileMode) {
                    throw new IncorrectInputException();
                }
            } catch (IllegalArgumentException var5) {
                Outputer.printerror("Такого типа билетов нет!");
                Outputer.print("> ");
                if (this.fileMode) {
                    throw new IncorrectInputException();
                }
            } catch (IllegalStateException var6) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
    }

    public Date checkBirthday() throws IncorrectInputException {
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy h:mm:ss");

        while(true) {
            try {
                Outputer.println("Введите дату дня рождения в фомате dd/MM/YYYY HH:mm:ss: ");
                Outputer.print("> ");
                String str = this.userscanner.nextLine().trim();
                if (this.fileMode) {
                    Outputer.println(str);
                }

                Date birthday = dateFormat.parse(str);
                if (birthday.equals((Object)null)) {
                    throw new WrongArgumentInputException();
                }

                return birthday;
            } catch (NoSuchElementException var6) {
                Outputer.printerror("дата не распознана!");
                Outputer.print("> ");
                if (this.fileMode) {
                    throw new IncorrectInputException();
                }
            } catch (IllegalStateException var7) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            } catch (ParseException var8) {
                Outputer.printerror("Ошибка, не правильно введены даты");
                Outputer.print("> ");
            } catch (WrongArgumentInputException var9) {
                Outputer.printerror("Дата не может быть NULL");
                Outputer.print("> ");
            }
        }
    }

    public Double checkHeight() throws IncorrectInputException {
        while(true) {
            try {
                Outputer.println("Введите рост человека > 0.0: ");
                Outputer.print("> ");
                String str = this.userscanner.nextLine().trim();
                if (this.fileMode) {
                    Outputer.println(str);
                }

                Double height = Double.parseDouble(str);
                if (height < 0.0D) {
                    throw new WrongArgumentInputException();
                }

                if (height.equals((Object)null)) {
                    throw new NullInputException();
                }

                return height;
            } catch (NoSuchElementException var4) {
                Outputer.printerror("Рост не распознан!");
                Outputer.print("> ");
                if (this.fileMode) {
                    throw new IncorrectInputException();
                }
            } catch (NumberFormatException var5) {
                Outputer.printerror("Рост должен быть числом c плавающей запятой!");
                Outputer.print("> ");
                if (this.fileMode) {
                    throw new IncorrectInputException();
                }
            } catch (WrongArgumentInputException var6) {
                Outputer.printerror("Рост не должен быть ниже значение равное = 0.0");
                Outputer.print("> ");
                if (this.fileMode) {
                    throw new IncorrectInputException();
                }
            } catch (NullPointerException var7) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            } catch (IllegalStateException var8) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            } catch (NullInputException var9) {
                Outputer.printerror("Рост не может быть null");
                Outputer.print("> ");
            }
        }
    }

    public float checkWeight() throws IncorrectInputException {
        while(true) {
            Float weight;
            try {
                Outputer.println("Введите ширину человека > 0.0: ");
                Outputer.print("> ");
                String str = this.userscanner.nextLine().trim();
                if (this.fileMode) {
                    Outputer.println(str);
                }

                weight = Float.parseFloat(str);
                if ((double)weight < 0.0D) {
                    throw new WrongArgumentInputException();
                }
            } catch (NoSuchElementException var4) {
                Outputer.printerror("Цена не распознана!");
                Outputer.print("> ");
                if (!this.fileMode) {
                    continue;
                }

                throw new IncorrectInputException();
            } catch (NumberFormatException var5) {
                Outputer.printerror("Цена должна быть числом c плавающей запятой!");
                Outputer.print("> ");
                if (!this.fileMode) {
                    continue;
                }

                throw new IncorrectInputException();
            } catch (WrongArgumentInputException var6) {
                Outputer.printerror("Цена должна превышать значение равное = -588.0");
                Outputer.print("> ");
                if (!this.fileMode) {
                    continue;
                }

                throw new IncorrectInputException();
            } catch (NullPointerException var7) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
                continue;
            } catch (IllegalStateException var8) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
                continue;
            }

            return weight;
        }
    }

    public double checkXLocation() throws IncorrectInputException {
        while(true) {
            Double x;
            try {
                Outputer.println("Введите локацию X:");
                Outputer.print("> ");
                String str = this.userscanner.nextLine().trim();
                if (this.fileMode) {
                    Outputer.println(str);
                }

                x = Double.parseDouble(str);
            } catch (NoSuchElementException var4) {
                Outputer.printerror("Координата X не распознана!");
                Outputer.print("> ");
                if (!this.fileMode) {
                    continue;
                }

                throw new IncorrectInputException();
            } catch (NumberFormatException var5) {
                Outputer.printerror("Координата X должна быть числом c плавающей запятой!");
                Outputer.print("> ");
                if (!this.fileMode) {
                    continue;
                }

                throw new IncorrectInputException();
            } catch (NullPointerException var6) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
                continue;
            } catch (IllegalStateException var7) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
                continue;
            }

            return x;
        }
    }

    public long checkYLocation() throws IncorrectInputException {
        while(true) {
            Long y;
            try {
                Outputer.println("Введите локацию Y:");
                Outputer.print("> ");
                String str = this.userscanner.nextLine().trim();
                if (this.fileMode) {
                    Outputer.println(str);
                }

                y = Long.parseLong(str);
                if (y.equals((Object)null)) {
                    throw new NullInputException();
                }
            } catch (NoSuchElementException var4) {
                Outputer.printerror("Координата Y не распознана!");
                Outputer.print("> ");
                if (!this.fileMode) {
                    continue;
                }

                throw new IncorrectInputException();
            } catch (NumberFormatException var5) {
                Outputer.printerror("Координата Y должна быть целым числом!");
                Outputer.print("> ");
                if (!this.fileMode) {
                    continue;
                }

                throw new IncorrectInputException();
            } catch (NullPointerException var6) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
                continue;
            } catch (IllegalStateException var7) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
                continue;
            } catch (NullInputException var8) {
                Outputer.printerror("Координата Y не может быть null");
                Outputer.print("> ");
                continue;
            }

            return y;
        }
    }

    public double checkZLocation() throws IncorrectInputException {
        while(true) {
            Double z;
            try {
                Outputer.println("Введите локацию Z:");
                Outputer.print("> ");
                String str = this.userscanner.nextLine().trim();
                if (this.fileMode) {
                    Outputer.println(str);
                }

                z = Double.parseDouble(str);
            } catch (NoSuchElementException var4) {
                Outputer.printerror("Координата Z не распознана!");
                Outputer.print("> ");
                if (!this.fileMode) {
                    continue;
                }

                throw new IncorrectInputException();
            } catch (NumberFormatException var5) {
                Outputer.printerror("Координата Z должна быть числом c плавающей запятой!");
                Outputer.print("> ");
                if (!this.fileMode) {
                    continue;
                }

                throw new IncorrectInputException();
            } catch (NullPointerException var6) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
                continue;
            } catch (IllegalStateException var7) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
                continue;
            }

            return z;
        }
    }

    public Location returnLocation() throws IncorrectInputException {
        double x = this.checkXLocation();
        Long y = this.checkYLocation();
        double z = this.checkZLocation();
        return new Location(x, y, z);
    }

    public Person returnPerson() throws IncorrectInputException {
        Date birthday = this.checkBirthday();
        Double height = this.checkHeight();
        float weight = this.checkWeight();
        Location location = this.returnLocation();
        return new Person(birthday, height, weight, location);
    }

    public boolean wantToChange(String str) throws IncorrectInputException {
        String question = str + " : 'yes' or 'no':";

        while(true) {
            try {
                Outputer.println(question);
                Outputer.print("> ");
                String answer = this.userscanner.nextLine().trim();
                if (this.fileMode) {
                    Outputer.println(answer);
                }

                if (!answer.equals("yes") && !answer.equals("no")) {
                    throw new WrongArgumentInputException();
                }

                return answer.equals("yes");
            } catch (NoSuchElementException var5) {
                Outputer.printerror("Ответ не распознан!");
                Outputer.print("> ");
                if (this.fileMode) {
                    throw new IncorrectInputException();
                }
            } catch (WrongArgumentInputException var6) {
                Outputer.printerror("Нужно ответить: 'yes' или 'no'");
                Outputer.print("> ");
                if (this.fileMode) {
                    throw new IncorrectInputException();
                }
            } catch (IllegalStateException var7) {
                Outputer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
    }

    public void setUserscanner(Scanner userscanner) {
        this.userscanner = userscanner;
    }

    public Scanner getUserscanner() {
        return this.userscanner;
    }

    public void setFileMode() {
        this.fileMode = true;
    }

    public void setUserMode() {
        this.fileMode = false;
    }
}

