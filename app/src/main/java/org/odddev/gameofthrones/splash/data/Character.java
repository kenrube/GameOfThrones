package org.odddev.gameofthrones.splash.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kenrube
 * @date 16.10.16
 */

public class Character {

    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("culture")
    @Expose
    public String culture;
    @SerializedName("born")
    @Expose
    public String born;
    @SerializedName("died")
    @Expose
    public String died;
    @SerializedName("titles")
    @Expose
    public List<String> titles = new ArrayList<>();
    @SerializedName("aliases")
    @Expose
    public List<String> aliases = new ArrayList<>();
    @SerializedName("father")
    @Expose
    public String father;
    @SerializedName("mother")
    @Expose
    public String mother;
    @SerializedName("spouse")
    @Expose
    public String spouse;
    @SerializedName("allegiances")
    @Expose
    public List<String> allegiances = new ArrayList<>();
    @SerializedName("books")
    @Expose
    public List<String> books = new ArrayList<>();
    @SerializedName("povBooks")
    @Expose
    public List<String> povBooks = new ArrayList<>();
    @SerializedName("tvSeries")
    @Expose
    public List<String> tvSeries = new ArrayList<>();
    @SerializedName("playedBy")
    @Expose
    public List<String> playedBy = new ArrayList<>();
}
