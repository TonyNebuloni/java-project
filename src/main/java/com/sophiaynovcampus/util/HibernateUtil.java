package com.sophiaynovcampus.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.Properties;
import java.io.InputStream;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Charger les propriétés personnalisées
            Properties props = new Properties();
            try (InputStream input = HibernateUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
                if (input != null) {
                    props.load(input);
                }
            }
            String bddPath = props.getProperty("bdd.path", "jeuxvideo.db");
            String jdbcUrl = "jdbc:sqlite:" + bddPath;

            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml"); // charge le fichier de config
            // Remplacer dynamiquement l'URL de connexion
            configuration.setProperty("hibernate.connection.url", jdbcUrl);
            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
