import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
@Getter
@Setter
public class Kurs {

    private String no;
    private String effectiveDate;
    private Double bid;
    private Double ask;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


}
