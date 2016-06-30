package kit.edu.pse.goapp.server.converter.daos;


public interface DaoConverter<T> {
	
	public T parse(String jsonString);
	

}
