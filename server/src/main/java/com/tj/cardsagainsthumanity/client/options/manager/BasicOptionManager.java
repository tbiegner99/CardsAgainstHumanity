package com.tj.cardsagainsthumanity.client.options.manager;

import com.tj.cardsagainsthumanity.client.io.InputReader;
import com.tj.cardsagainsthumanity.client.io.OutputWriter;
import com.tj.cardsagainsthumanity.client.options.Option;
import com.tj.cardsagainsthumanity.client.options.OptionContext;
import com.tj.cardsagainsthumanity.client.options.OptionManager;
import com.tj.cardsagainsthumanity.client.options.OptionSet;
import com.tj.cardsagainsthumanity.exceptions.StateNotAffectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Stack;

@Component
public class BasicOptionManager implements OptionManager {
    private Stack<OptionSet> options;
    private Option[] currentOptions;
    private OptionSet currentSet;
    private OptionContext currentContext;

    @Autowired
    public BasicOptionManager(@Qualifier("initialOptions") OptionSet currentOption) {
        this.resetWithOptions(currentOption);
    }

    @Override
    public void prompt(OutputWriter writer, OptionContext context) {
        writer.writeLine(currentSet.getPrompt(context));
        for (int i = 0; i < currentOptions.length; i++) {
            promptOption(writer, i, currentOptions[i]);
        }

    }

    private void promptOption(OutputWriter writer, int index, Option option) {
        String output = String.format("%2d) %s", index + 1, option.getText());
        writer.writeLine(output);
    }


    @Override
    public Option readOption(InputReader reader) {
        Integer index = reader.readInteger();
        return currentOptions[index - 1];
    }

    @Override
    public void back() {
        changeOptionSet(options.pop());
    }

    @Override
    public void pushOptions(OptionContext context, OptionSet options) {
        this.currentContext = context;
        this.pushOptions(options);

    }

    @Override
    public void pushOptions(OptionSet options) {
        this.options.push(options);
        changeOptionSet(options);
    }

    private void changeOptionSet(OptionSet options) {
        currentSet = options;
        currentOptions = options.getOptions(currentContext);
    }

    @Override
    public void resetWithOptions(OptionSet options) {
        this.options = new Stack<>();
        changeOptionSet(options);
    }

    @Override
    public void resetWithOptions(OptionSet options, OptionContext context) {
        this.currentContext = context;
        resetWithOptions(options);
    }

    @Override
    public void resetWithOptionsIfChanged(OptionSet nextOptions, OptionContext nextContext) throws StateNotAffectedException {
        if (currentSet.equals(nextOptions)) {
            throw new StateNotAffectedException();
        }
        resetWithOptions(nextOptions, nextContext);
    }


}
