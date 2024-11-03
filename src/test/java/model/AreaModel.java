package model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class AreaModel {

    @Expose(serialize = false)
    private int id;

    @Expose
    private String description;

    @Expose
    private String location;

    @Expose
    private String size;

}
