package kit.edu.pse.goapp.server.converter.objects;

import java.util.List;

public interface ObjectConverter<T> {
	
	public T deserialize(String jsonString);
	public List<T> deserializeList(String jsonString);

}
