import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Waluty {


    private String table;
    private String currency;
    private String code;
    private List<Kurs> rates = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


}
