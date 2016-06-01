package me.kantrael.calcit.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "subpod", strict = false)
public class WolframSubPod {
    @Element(name = "plaintext", required = false)
    public String text;

    @Element(name = "img")
    public WolframImage image;
}
