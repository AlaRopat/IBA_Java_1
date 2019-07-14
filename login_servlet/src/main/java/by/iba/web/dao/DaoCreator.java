package by.iba.web.dao;

@FunctionalInterface
public interface DaoCreator<Context> {

  GenericDao create(Context context);
}
