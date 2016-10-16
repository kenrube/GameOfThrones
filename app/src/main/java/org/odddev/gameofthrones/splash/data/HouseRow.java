package org.odddev.gameofthrones.splash.data;

import java.io.Serializable;
import java.util.List;

/**
 * @author kenrube
 * @date 16.10.16
 */

public class HouseRow implements Serializable {

    public int id;

    public String words;

    public List<Integer> charactersList;
}
