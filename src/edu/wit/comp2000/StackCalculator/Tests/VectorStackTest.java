package edu.wit.comp2000.StackCalculator.Tests;

import edu.wit.comp2000.StackCalculator.Models.StackInterface;
import edu.wit.comp2000.StackCalculator.Models.VectorStack;
import org.junit.Assert;
import org.junit.Test;

import java.util.EmptyStackException;

/**
 * Created by beznosm on 10/13/2016.
 */
public class VectorStackTest {
    @Test
    public void PushExtend() throws Exception {
        StackInterface<String> vs = new VectorStack<>(1);
        vs.push("hello");
        vs.push("world");
        vs.push("!!");
        Assert.assertEquals("!!", vs.pop());
        Assert.assertEquals("world", vs.pop());
        Assert.assertEquals("hello", vs.pop());
    }

    @Test
    public void PeekTest(){
        StackInterface<String> vs = new VectorStack<>();
        vs.push("hello");
        Assert.assertEquals("hello", vs.peek());
        Assert.assertEquals("hello", vs.pop());
    }
    @Test(expected = EmptyStackException.class)
    public void InvalidPeekTest(){
        StackInterface<String> vs = new VectorStack<>();
        vs.peek();
    }

    @Test
    public void popTest(){
        VectorStack<String> vs = new VectorStack<>(1);
        vs.push("hello");
        vs.push("world");
        vs.push("!!");
        Assert.assertEquals("!!", vs.pop());
        Assert.assertEquals("world", vs.pop());
    }
    @Test(expected = EmptyStackException.class)
    public void InvalidPopTest(){
        VectorStack<String> vs = new VectorStack<>(1);
        vs.pop();
    }

    @Test
    public void clearTest(){
        VectorStack<String> vs = new VectorStack<>(1);
        vs.push("hello");
        vs.push("world");
        Assert.assertEquals(false, vs.isEmpty());
        vs.clear();
        Assert.assertEquals(true, vs.isEmpty());
    }

}