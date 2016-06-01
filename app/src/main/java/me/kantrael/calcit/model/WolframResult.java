package me.kantrael.calcit.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict = false)
public class WolframResult {
    @Attribute
    private boolean success;

    @Attribute
    private boolean error;

    @ElementList(inline = true)
    private List<WolframPod> pods;
}
