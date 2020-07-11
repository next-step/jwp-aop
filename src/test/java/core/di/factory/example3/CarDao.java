package core.di.factory.example3;

import javax.sql.DataSource;

/**
 * @author KingCjy
 */
public class CarDao {
    private DataSource dataSource;

    public CarDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
