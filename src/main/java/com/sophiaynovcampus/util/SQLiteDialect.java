package com.sophiaynovcampus.util;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;
import java.sql.Types;

public class SQLiteDialect extends Dialect {

    public SQLiteDialect() {
        super();
        registerColumnType(Types.INTEGER, "integer");
        registerColumnType(Types.BIGINT, "integer");
        registerColumnType(Types.VARCHAR, "text");
        registerColumnType(Types.BOOLEAN, "boolean");
        // Ajoute d'autres mappings nécessaires ici
    }

    @Override
    public IdentityColumnSupport getIdentityColumnSupport() {
        return new SQLiteIdentityColumnSupport();
    }

    private static class SQLiteIdentityColumnSupport extends IdentityColumnSupportImpl {

        @Override
        public boolean supportsIdentityColumns() {
            return true;
        }

        @Override
        public String getIdentitySelectString(String table, String column, int type) {
            return "select last_insert_rowid()";
        }

        @Override
        public String getIdentityColumnString(int type) {
            return "integer";
        }

        @Override
        public boolean hasDataTypeInIdentityColumn() {
            return false;
        }
    }

    @Override
    public boolean hasAlterTable() {
        return false;  // SQLite ne supporte pas ALTER TABLE complet
    }

    @Override
    public boolean dropConstraints() {
        return false;
    }

    // Selon ta version de Hibernate, d'autres méthodes peuvent être nécessaires
}
