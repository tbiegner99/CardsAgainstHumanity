package com.tj.cardsagainsthumanity.client.options;

import com.tj.cardsagainsthumanity.client.options.processor.result.ProcessorResult;

public interface OptionProcessor<O extends Option> {
    ProcessorResult processOption(O option, OptionContext context);

    default boolean canProcessOption(O option, OptionContext context) {
        return true;
    }
}
