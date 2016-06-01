package me.kantrael.calcit.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "pod", strict = false)
public class WolframPod {
    @Attribute(required = false)
    public String title;

    @Attribute
    public boolean error;

    @ElementList(inline = true)
    public List<WolframSubPod> subPods;
}
