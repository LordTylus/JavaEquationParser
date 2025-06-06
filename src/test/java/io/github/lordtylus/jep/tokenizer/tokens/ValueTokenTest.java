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
package io.github.lordtylus.jep.tokenizer.tokens;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValueTokenTest {

    @Test
    void returnsTokenString() {

        /* Given */

        ValueToken sut = new ValueToken("Test String");

        /* When */

        String actual = sut.getString();

        /* Then */

        assertEquals("Test String", actual);
    }

    @Test
    void doesNotAdjustDepth() {

        /* Given */

        ValueToken sut = new ValueToken("Test String");

        /* When */

        int actual = sut.adjustDepth(10);

        /* Then */

        assertEquals(10, actual);
    }
}