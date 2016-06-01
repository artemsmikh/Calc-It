package me.kantrael.calcit.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class WolframImage {
    @Attribute(name = "src")
    private String url;

    @Attribute
    private int width;

    @Attribute
    private int height;
}
