package me.kantrael.calcit.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class WolframImage {
    @Attribute(name = "src")
    public String url;

    @Attribute
    public int width;

    @Attribute
    public int height;
}
