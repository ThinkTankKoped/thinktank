package id.ac.ui.cs.sofeng.thinktank.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleCell {

    private String day;
    private String hour;
    private String description;
}
