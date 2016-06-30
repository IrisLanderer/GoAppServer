package kit.edu.pse.goapp.server.converter.objects;


public interface ObjectConverter<T> {
	
	public String serialize(T object);

}
