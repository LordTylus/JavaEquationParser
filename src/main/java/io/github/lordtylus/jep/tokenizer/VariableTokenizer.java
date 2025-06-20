/*
  Copyright 2025 Martin Rökker

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/
package io.github.lordtylus.jep.tokenizer;

import io.github.lordtylus.jep.options.ParsingOptions;
import io.github.lordtylus.jep.parsers.variables.VariablePattern;
import io.github.lordtylus.jep.tokenizer.tokens.Token;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * This tokenizer never does anything to the string, however it ensures that nobody else
 * splits while we are between [ and ] this is very important, because otherwise parenthesis
 * or operators between [ and ] would be detected as things they are not.
 */
@Getter
@RequiredArgsConstructor
public final class VariableTokenizer implements EquationTokenizer {

    /**
     * Default immutable instance of this singleton class
     */
    public static final VariableTokenizer INSTANCE = new VariableTokenizer();

    @Override
    public Set<Character> getDelimitersFor(
            @NonNull ParsingOptions parsingOptions) {

        VariablePattern variablePattern = parsingOptions.getVariablePattern();

        if (!variablePattern.isEscaped())
            return Collections.emptySet();

        char opening = variablePattern.openingCharacter();
        char closing = variablePattern.closingCharacter();

        if (opening == closing)
            return Set.of(opening);

        return Set.of(opening, closing);
    }

    @Override
    public boolean handle(
            int beginIndex,
            int currentIndex,
            char currentCharacter,
            @NonNull String equation,
            @NonNull List<Token> tokenList,
            @NonNull TokenizerContext context,
            @NonNull ParsingOptions parsingOptions) {

        VariablePattern variablePattern = parsingOptions.getVariablePattern();
        char opening = variablePattern.openingCharacter();
        char closing = variablePattern.closingCharacter();

        boolean isSame = opening == closing;

        if (!isSame) {

            if (currentCharacter == opening)
                context.increaseBracketCount();
            else
                context.decreaseBracketCount();

        } else {

            if (context.isSplitProhibited())
                context.decreaseBracketCount();
            else
                context.increaseBracketCount();
        }

        return false;
    }
}
