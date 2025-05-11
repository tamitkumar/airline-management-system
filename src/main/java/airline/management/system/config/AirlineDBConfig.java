package airline.management.system.config;

import airline.management.system.exception.AirlineException;
import airline.management.system.exception.ErrorCode;
import airline.management.system.exception.ErrorSeverity;
import airline.management.system.utils.AirlineConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class AirlineDBConfig {

    @Bean
    DataSource dataSource() {
        String dbUser = AirlineConstant.DB_USER_NAME;
        String dbPassword = AirlineConstant.DB_PASSWORD;
        String driverClassName = AirlineConstant.DB_DRIVER_CLASS_NAME;
        DriverManagerDataSource ds = new DriverManagerDataSource(getDBUrl(), dbUser, dbPassword);
        try {
            ds.setDriverClassName(driverClassName);
        } catch (Exception e) {
            throw new AirlineException(ErrorCode.ERR002.getErrorCode(), ErrorSeverity.FATAL,
                    ErrorCode.ERR002.getErrorMessage(), e);
        }
        try {
            ds.getConnection().close();
        } catch (SQLException e) {
            throw new AirlineException(ErrorCode.ERR002.getErrorCode(), ErrorSeverity.FATAL,
                    ErrorCode.ERR002.getErrorMessage(), e);
        }
        return ds;
    }

    private String getDBUrl() {
        String dbHost = AirlineConstant.DB_HOST;
        String dbPort = AirlineConstant.DB_PORT;
        String dbName = AirlineConstant.DB_NAME;
        String dbUrlPrefix = AirlineConstant.DB_URL_PREFIX;
        //		baseUrl.append(EMPConstant.COLON);
        return dbUrlPrefix + dbHost +
                AirlineConstant.COLON +
                dbPort +
                dbName;
    }
}
