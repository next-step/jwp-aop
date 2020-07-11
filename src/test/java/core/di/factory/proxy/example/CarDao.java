package core.di.factory.proxy.example;

import core.annotation.Inject;

import javax.sql.DataSource;

/**
 * @author KingCjy
 */
public class CarDao {
    private DataSource dataSource;

    @Inject
    public CarDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
