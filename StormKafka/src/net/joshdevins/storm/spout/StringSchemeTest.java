package net.joshdevins.storm.spout;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import backtype.storm.spout.Scheme;

public class StringSchemeTest {

    private Scheme scheme;


    public void before() {
        scheme = new StringScheme();
    }


    public void testDeserialize() {

        List<Object> list = scheme.deserialize("TEST".getBytes());

        Assert.assertEquals(1, list.size());
        Assert.assertEquals("TEST", list.get(0));
    }
}
