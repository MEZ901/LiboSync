package src.main.java.services;

import src.main.java.repository.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorService {
    Model model = new Model("author");

    public int getAuthorIdOrCreateIfNotFound (String name) {
        Map<String, Object> criteria = new HashMap<>();
        criteria.put("name", name);

        List<Map<String, Object>> author = model.find(criteria, null);

        if (!author.isEmpty()) {
            return (int) author.get(0).get("id");
        }

        Map<String, Object> newData = new HashMap<>();
        newData.put("name", "\"" + name + "\"");

        List<Map<String, Object>> newAuthor = model.insertAndReturn(newData);

        return (int) newAuthor.get(0).get("id");
    }
}
