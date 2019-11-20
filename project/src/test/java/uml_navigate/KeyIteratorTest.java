package uml_navigate;


import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import uml_navigate.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.Key;


public class KeyIteratorTest extends BasePlatformTestCase {

    @Test
    public void testInitiation() {
        KeyIterator it = new KeyIterator();
        assertEquals("N", it.next());
    }

    @Test
    public void testShortIteration() {
        KeyIterator it = new KeyIterator();
        for(int i=0;i < 5; i++)
        {
            it.next();
        }
        assertEquals("S", it.next());
    }

    @Test
    public void testLongIteration() {
        KeyIterator it = new KeyIterator();
        for(int i=0;i < 1000; i++)
        {
            it.next();
        }
        assertEquals("EKZ", it.next());
    }

    @Test
    public void testHasNext() {
        KeyIterator it = new KeyIterator();
        for(int i=0;i < 1000; i++)
        {
            it.next();
            assertTrue(it.hasNext()) ;
        }
    }

}
