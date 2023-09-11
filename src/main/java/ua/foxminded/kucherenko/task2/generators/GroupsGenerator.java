package ua.foxminded.kucherenko.task2.generators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.dao.GroupDao;
import ua.foxminded.kucherenko.task2.models.Group;

import java.util.Random;

@Component
public class GroupsGenerator implements IGenerator {
    @Autowired
    private GroupDao groupDao;
    private static final int GROUPS_QUANTITY = 10;
    private static final String ALPHABET_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";

    @Override
    public void addToDb() {
        for (int i = 0; i < GROUPS_QUANTITY; i++) {
            String groupName = generateGroupNames();
            groupDao.save(new Group(groupName));
        }
    }

    private String generateGroupNames() {
        Random random = new Random();
        StringBuilder groupNameBuilder = new StringBuilder();
        groupNameBuilder.append(generateGroupPrefix(random)).append("-").append(generateGroupSuffix(random));
        return groupNameBuilder.toString();
    }

    private String generateGroupPrefix(Random random) {
        StringBuilder letterGroupName = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            char randomChar = ALPHABET_CHARS.charAt(random.nextInt(ALPHABET_CHARS.length()));
            letterGroupName.append(randomChar);
        }
        return letterGroupName.toString();
    }

    private String generateGroupSuffix(Random random) {
        StringBuilder numberGroup = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            char randomChar = DIGITS.charAt(random.nextInt(DIGITS.length()));
            numberGroup.append(randomChar);
        }
        return numberGroup.toString();
    }
}
