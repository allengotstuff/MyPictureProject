package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class MyClass {
    private static final String PROJECT_DIR = System.getProperty("user.dir");

    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.pheth.hasee.stickerhero.greendao");
        schema.enableKeepSectionsByDefault();

        addTables(schema);

        try {
            new DaoGenerator().generateAll(schema, PROJECT_DIR + "\\app\\src\\main\\java\\");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTables(final Schema schema) {
//        Entity user = addUser(schema);
//        Entity repo = addRepo(schema);
//
//        Property userId = repo.addLongProperty("userId").notNull().getProperty();
//        user.addToMany(repo, userId, "userRepos");
        addFavorite(schema);
        addHistory(schema);
        addFavoriteCategory(schema);
    }

    private static Entity addUser(final Schema schema) {
        Entity user = schema.addEntity("User");
        user.addIdProperty().primaryKey().autoincrement();
        user.addStringProperty("name").notNull();
        user.addShortProperty("age");

        return user;
    }

    private static Entity addRepo(final Schema schema) {
        Entity repo = schema.addEntity("Repo");
        repo.addIdProperty().primaryKey().autoincrement();
        repo.addStringProperty("title").notNull();
        repo.addStringProperty("language");
        repo.addIntProperty("watchers_count");

        return repo;
    }

    // this is my faborite list
    private static Entity addFavorite(final Schema schema)
    {

        Entity favorite = schema.addEntity("Favorite");
        favorite.addIdProperty().primaryKey().autoincrement();
        favorite.addStringProperty("name");
        favorite.addStringProperty("url_full");
        favorite.addStringProperty("url_thumb");
        favorite.addStringProperty("search_id");

        favorite.addStringProperty("url_full_local");
        favorite.addStringProperty("url_thumb_local");
        favorite.addDateProperty("add_date");
        return favorite;
    }

    private static Entity addFavoriteCategory(final Schema schema)
    {
        Entity favorite = schema.addEntity("FavoriteCategory");
        favorite.addIdProperty().primaryKey().autoincrement();
        favorite.addStringProperty("title");
        favorite.addStringProperty("url_full");
        favorite.addStringProperty("url_thumb");
        favorite.addStringProperty("identifier");

        favorite.addStringProperty("url_full_local");
        favorite.addStringProperty("url_thumb_local");
        favorite.addDateProperty("add_date");
        return favorite;
    }

    // this is the history
    private static Entity addHistory(final Schema schema)
    {
        Entity history = schema.addEntity("History");
        history.addIdProperty().primaryKey().autoincrement();
        history.addStringProperty("name");
        history.addStringProperty("url_send_local");
        history.addStringProperty("url_send_online");
        history.addStringProperty("url_thumb_local");
        history.addStringProperty("url_thumb_online");
        history.addStringProperty("identifier");

        history.addDateProperty("add_date");
        return history;
    }
}
