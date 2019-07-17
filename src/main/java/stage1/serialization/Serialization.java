package stage1.serialization;

import java.io.IOException;

public interface Serialization {
    byte[] serialize(Object object) throws IOException;

    <T> T deserialize(byte[] bytes, Class<T> clz) throws  IOException;

}
