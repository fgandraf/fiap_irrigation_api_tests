package model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class ScheduleModel {

    @Expose(serialize = false)
    private int id;

    @Expose
    private String startTime;

    @Expose
    private String endTime;

}
