package swt6.orm.dao.base;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Ignore;
import swt6.util.JpaUtil;

import javax.sql.DataSource;

@Ignore
public class DataSourceDBUnitTest extends DataSourceBasedDBTestCase {
    @Override
    protected DataSource getDataSource() {
        // do initial commit that db gets created by jpa and can be found by dbunit
        JpaUtil.getTransactedTestEntityManager();
        JpaUtil.commit();
        EmbeddedDataSource dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName("memory:WorkLogDb");

        return dataSource;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("dbunit/dataSet.xml"));
    }

    @Override
    protected DatabaseOperation getSetUpOperation() {
        JpaUtil.getTransactedEntityManager();
        return DatabaseOperation.REFRESH;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() {
        JpaUtil.closeEntityManagerFactory();
        return DatabaseOperation.DELETE_ALL;
    }
}
