package one.two.three.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("one.two.three.DAO")
@PropertySource("classpath:database.properties")
public class DataBaseConfig {

	@Autowired
	private Environment envi;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource managerDataSource = new DriverManagerDataSource();
		managerDataSource.setDriverClassName(envi.getProperty("db.driver"));
		managerDataSource.setUrl(envi.getProperty("db.url"));
		managerDataSource.setPassword(envi.getProperty("db.userPassword"));
		managerDataSource.setUsername(envi.getProperty("db.username"));

		return managerDataSource;
	}

	@Bean
	public JpaVendorAdapter vendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);

		//////////////////// delet on release//////////////////
		hibernateJpaVendorAdapter.setShowSql(true);
		// ___________________________________________________

		return hibernateJpaVendorAdapter;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean managerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		managerFactoryBean.setDataSource(dataSource());
		managerFactoryBean.setJpaVendorAdapter(vendorAdapter());
		managerFactoryBean.setPackagesToScan(envi.getProperty("db.packageToScan"));
		Properties properties = new Properties();
		properties.put(envi.getProperty("db.hibernate.hbm"), envi.getProperty("db.hibernate.hbm.value"));
		managerFactoryBean.setJpaProperties(properties);

		return managerFactoryBean;
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

		return new JpaTransactionManager(entityManagerFactory);
	}

}
