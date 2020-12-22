package com.tj.cardsagainsthumanity.client.io;

public interface OutputWriter {
    void write(String message);

    void writeLine(String message);

    interface Colors {
        String RESET = "\u001b[0m";

        interface Background {
            String BLACK = "\u001b[40m";
            String RED = "\u001b[41m";
            String GREEN = "\u001b[42m";
            String YELLOW = "\u001b[43m";
            String BLUE = "\u001b[44m";
            String MAGENTA = "\u001b[45m";
            String CYAN = "\u001b[46m";
            String WHITE = "\u001b[47m";

            interface Bold {
                String BLACK = "\u001b[100m";
                String RED = "\u001b[101m";
                String GREEN = "\u001b[102m";
                String YELLOW = "\u001b[103m";
                String BLUE = "\u001b[104m";
                String MAGENTA = "\u001b[105m";
                String CYAN = "\u001b[106m";
                String WHITE = "\u001b[107m";
            }
        }

        interface Foreground {
            String BLACK = "\u001b[30m";
            String RED = "\u001b[31m";
            String GREEN = "\u001b[32m";
            String YELLOW = "\u001b[33m";
            String BLUE = "\u001b[34m";
            String MAGENTA = "\u001b[35m";
            String CYAN = "\u001b[36m";
            String WHITE = "\u001b[37m";

            interface Bold {
                String BLACK = "\u001b[90m";
                String RED = "\u001b[91m";
                String GREEN = "\u001b[92m";
                String YELLOW = "\u001b[93m";
                String BLUE = "\u001b[94m";
                String MAGENTA = "\u001b[95m";
                String CYAN = "\u001b[96m";
                String WHITE = "\u001b[97m";
            }
        }

    }
}
