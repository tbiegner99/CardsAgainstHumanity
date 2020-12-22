package com.tj.cardsagainsthumanity.client.options;

import com.tj.cardsagainsthumanity.client.io.InputReader;
import com.tj.cardsagainsthumanity.client.io.OutputWriter;
import com.tj.cardsagainsthumanity.exceptions.StateNotAffectedException;

public interface OptionManager {
    void prompt(OutputWriter writer, OptionContext context);

    Option readOption(InputReader reader);

    void back();

    void pushOptions(OptionContext context, OptionSet options);

    void pushOptions(OptionSet options);

    void resetWithOptions(OptionSet options);

    void resetWithOptions(OptionSet options, OptionContext context);

    void resetWithOptionsIfChanged(OptionSet nextOptions, OptionContext nextContext) throws StateNotAffectedException;
}
