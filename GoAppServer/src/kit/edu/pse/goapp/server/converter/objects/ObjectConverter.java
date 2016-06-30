package kit.edu.pse.goapp.server.converter.objects;

import java.util.List;

public interface ObjectConverter<T> {
	
	public String serialize(T object);
	public String serialize(List<T> objects);

}
