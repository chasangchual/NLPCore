package com.bloomingbread.core.nlp.tools;

import com.bloomingbread.core.nlp.tools.ngram.Gram;
import org.junit.Test;

import static org.junit.Assert.*;

public class GramTest {

    @Test
    public void NGramConstructor() {
        Gram gram = new Gram("first");
        assertEquals(1, gram.size());
        assertEquals("first", gram.getWord(0));
        assertEquals("first", gram.toString());
    }

    @Test
    public void addAndGet() {
        Gram gram = new Gram();

        gram.add("first");
        gram.add("second");
        gram.add("third");

        assertEquals(3, gram.size());
        assertEquals("second", gram.getWord(1));
    }

    @Test
    public void toStringTest() {
        Gram gram = new Gram();

        gram.add("first");
        gram.add("second");
        gram.add("third");

        assertEquals("first second third", gram.toString());
    }

    @Test
    public void toStringWithDelimiterTest() {
        Gram gram = new Gram();

        gram.add("first");
        gram.add("second");
        gram.add("third");

        assertEquals("first,second,third", gram.toString(","));
    }

    @Test
    public void hashCodeTest() {
        Gram gram1 = new Gram();

        gram1.add("first");
        gram1.add("second");
        gram1.add("third");

        Gram gram2 = new Gram();

        gram2.add("first");
        gram2.add("second");
        gram2.add("third");

        Gram gram3 = new Gram();

        gram3.add("third");
        gram3.add("second");
        gram3.add("first");

        assertEquals(gram1.hashCode(), gram2.hashCode());
        assertNotEquals(gram1.hashCode(), gram3.hashCode());
    }
}