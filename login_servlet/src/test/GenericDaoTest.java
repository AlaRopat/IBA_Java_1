import by.iba.web.dao.GenericDao;
import by.iba.web.dao.Identified;
import by.iba.web.exception.PersistException;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.Serializable;
import java.util.List;

@RunWith(org.junit.runners.Parameterized.class)
public abstract class GenericDaoTest<Context> {
  private static final Logger LOGGER = Logger.getLogger(GenericDaoTest.class);
  protected Class daoClass;
  protected Identified noSavedtDto;

  public abstract GenericDao dao();

  public abstract Context context();

  @Test
  public void testSave() throws PersistException {
    Assert.assertNull("Id  before persist isn't null", noSavedtDto.getId());
    Identified dto = dao().save(noSavedtDto);
    LOGGER.info(dto);
    Assert.assertNotNull("Id after persist is  null", dto.getId());
  }

  @Test
  public void testGet() throws PersistException {
    Serializable id = dao().save(noSavedtDto).getId();
    LOGGER.info("id=" + id);
    Identified dto = dao().get(id);
    LOGGER.info(dto);
    Assert.assertNotNull(dto);
  }

  @Test
  public void testDelete() throws PersistException {
    Identified dto = dao().save(noSavedtDto);
    LOGGER.info(dto);
    Assert.assertNotNull(dto.getId());
    List list = dao().getAll();
    Assert.assertNotNull(list);
    int oldSize = list.size();
    Assert.assertTrue(oldSize > 0);
    dao().delete(dto);
    list = dao().getAll();
    Assert.assertNotNull(list);
    int newSize = list.size();
    Assert.assertEquals(1, oldSize - newSize);
  }

  @Test
  public void testGetAll() throws PersistException {
    dao().save(noSavedtDto);
    List list = dao().getAll();
    LOGGER.info(list);
    Assert.assertNotNull(list);
    Assert.assertTrue(list.size() > 0);
  }

  public GenericDaoTest(Class daoClass, Identified<Integer> noSavedtDto) {
    this.daoClass = daoClass;
    this.noSavedtDto = noSavedtDto;
  }
}
