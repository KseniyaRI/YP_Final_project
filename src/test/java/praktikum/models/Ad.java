package praktikum.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ad {

    private String name;
    private String category;
    private String condition;
    private String city;
    private String description;
    private Integer price;
    private Integer id;
}
