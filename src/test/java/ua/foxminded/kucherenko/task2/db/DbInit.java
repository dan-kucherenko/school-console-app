package ua.foxminded.kucherenko.task2.db;

public class DbInit {
    private static final TestConfigReader reader = new TestConfigReader();
    private static final DatabaseConfig testConfig = reader.readTestSchoolAdminConfiguration();

    public static void initDatabase() {
        DatabaseConfig adminTestConfig = reader.readAdminConfiguration();
        CreateTestDatabase createTestDatabase = new CreateTestDatabase(adminTestConfig);
        createTestDatabase.initDatabase();
        CreateTestTables createTestTables = new CreateTestTables(testConfig);
        createTestTables.createTables();
    }
}
