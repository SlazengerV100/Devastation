package engr302S3.server;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class StateService {
    private int x = 0;
    private int y = 0;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}