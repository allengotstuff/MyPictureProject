package com.pheth.hasee.stickerhero;

import com.pheth.hasee.stickerhero.GreenDaoManager.DaoManager;
import com.pheth.hasee.stickerhero.greendao.FavoriteCategory;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {



        ArrayList<String> list = new ArrayList<String>();

        list.add("one");
        assertEquals(4, 2 + 2);

        assertFalse(list.isEmpty());
    }
}