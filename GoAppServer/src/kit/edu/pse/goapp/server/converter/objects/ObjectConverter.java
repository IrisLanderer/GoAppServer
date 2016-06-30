package kit.edu.pse.goapp.server.converter.objects;

import java.util.List;

public interface ObjectConverter<T> {
	
	public String deserialize(T object);
	public String deserializeList(List<T> objects);

}
