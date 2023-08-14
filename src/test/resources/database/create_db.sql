DROP DATABASE IF EXISTS school_test_db;
CREATE DATABASE school_test_db;
DROP USER IF EXISTS school_test_admin;
CREATE USER school_test_admin WITH ENCRYPTED PASSWORD 'school_test_admin';
GRANT ALL PRIVILEGES ON DATABASE school_test_db TO school_test_admin;