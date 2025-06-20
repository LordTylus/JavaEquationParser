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
package io.github.lordtylus.jep.options;

import io.github.lordtylus.jep.Equation;
import io.github.lordtylus.jep.EquationOptional;
import io.github.lordtylus.jep.parsers.EquationParser;
import io.github.lordtylus.jep.parsers.ParseException;
import io.github.lordtylus.jep.parsers.variables.StandardVariablePatterns;
import io.github.lordtylus.jep.parsers.variables.VariablePattern;
import io.github.lordtylus.jep.tokenizer.EquationTokenizer;

import java.util.List;
import java.util.Map;

/**
 * This Options object holds references to all {@link EquationParser EquationParsers},
 * as well as the mandatory {@link EquationTokenizer} objects to be used for parsing.
 * <p>
 * It can be passed directly into {@link Equation#parse(String, ParsingOptions)} and
 * therefore can influence if and how an equation is parsed.
 */
public interface ParsingOptions {

    /**
     * Returns a list of {@link EquationParser} classes to be used for solving mathematical equations.
     * <p>
     * See {@link DefaultParsingOptions}
     *
     * @return {@link DefaultParsingOptions} instance
     */
    static ParsingOptions defaultOptions() {
        return DefaultParsingOptions.INSTANCE;
    }

    /**
     * This method decides if in case of parsing errors or Exceptions
     * if these exceptions should be thrown, or if they are to be collected inside
     * the {@link EquationOptional}
     * <p>
     * The default behavior is that exceptions are not thrown, but if needed it can be changed
     * using {@link CustomParsingOptions}
     *
     * @return The currently configured ErrorBehavior. {@link ErrorBehavior#ERROR_RESULT} is default.
     */
    ErrorBehavior getErrorBehavior();

    /**
     * This method defines how variables are expected to be escaped in the equation string.
     * <p>
     * The default configuration will use {@link StandardVariablePatterns#BRACKETS}
     * <p>
     * Which means that an equation string has to look like "2+[x]". Changing it to {@link StandardVariablePatterns#NONE} would allow to have them in the form of "2+x"
     *
     * @return VariablePattern pattern to be used when parsing variables.
     */
    VariablePattern getVariablePattern();

    /**
     * Returns a Map containing a mapping of a character to the corresponding {@link EquationTokenizer}
     * <p>
     * This set would include Brackets, Parenthesis, Operators to be used according to the registered
     * {@link EquationTokenizer} implementations returned by {@link #getRegisteredTokenizers()}
     *
     * @return immutable mapping of tokenizer delimiter to {@link EquationTokenizer}
     */
    Map<Character, EquationTokenizer> getTokenizerForDelimiterMap();

    /**
     * Returns a list of {@link EquationParser} objects to be used for parsing.
     * The order is important for parsing to prevent unnecessary checks and prevent
     * incorrect matches in case the EquationParsers must be executed in order to succeed.
     * This depends on the implementation of the {@link EquationParser} classes themselves.
     *
     * @return List of {@link EquationParser} to be used for parsing
     */
    List<EquationParser> getRegisteredParsers();

    /**
     * Returns a List of {@link EquationTokenizer} objects to be used for tokenizing the
     * equation string before parsing. The order of the list is important as it may impact
     * how equation is later parsed.
     *
     * @return List of {@link EquationTokenizer} objects to prepare the string for parsing.
     */
    List<EquationTokenizer> getRegisteredTokenizers();

    /**
     * This enum controls how parsing should behave in case an error is spotted during parsing.
     * Errors aren't necessarily exceptions. However, this behavior also responsible for handling
     * exceptions.
     * <p>
     * In this context, error is defined as a parsing error. Such as missing parenthesis or
     * unknown functions.
     */
    enum ErrorBehavior {
        /**
         * If the error behavior is set to EXCEPTION, any parsing error or exception during parsing will result in a {@link ParseException} to be thrown.
         */
        EXCEPTION,
        /**
         * If ERROR_RESULT is configured, no exception will be thrown, and any occurring exceptions will be wrapped in
         * {@link EquationOptional}. This is the default behavior.
         */
        ERROR_RESULT;
    }
}
