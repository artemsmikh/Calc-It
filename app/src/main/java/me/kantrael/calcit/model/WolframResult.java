package me.kantrael.calcit.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict = false)
public class WolframResult {
    @Attribute
    public boolean success;

    @Attribute
    public boolean error;

    @ElementList(inline = true)
    public List<WolframPod> pods;
}
