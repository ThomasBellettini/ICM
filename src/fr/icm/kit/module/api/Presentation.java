package fr.icm.kit.module.api;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Presentation {

    private String name;
    private List<String> description;
    private Material icon;

    public Presentation(String name, List<String> description, Material icon)
    {
        this.name = name;
        this.description = description;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<String> getDescription() {
        return description;
    }
    public void setDescription(List<String> description) {
        this.description = description;
    }
    public Material getIcon() {
        return icon;
    }
    public void setIcon(Material icon) {
        this.icon = icon;
    }
}
