package com.vosure.main;

import com.vosure.serialization.HandmadeDatabase;
import com.vosure.serialization.HandmadeField;
import com.vosure.serialization.HandmadeObject;
import com.vosure.serialization.HandmadeString;

import java.util.ArrayList;
import java.util.List;

public class Sandbox {

    static class Level {

        private String name;
        private String path;
        private int width, height;
        private List<Entity> entities = new ArrayList<>();

        private Level() {

        }

        public Level(String path) {
            this.name = "Level One";
            this.path = path;
            width = 64;
            height = 128;
        }

        public void add(Entity entity) {
            entity.init(this);
            entities.add(entity);
        }

        public void update() {

        }

        public void render() {

        }

        public  void save(String path) {
            HandmadeDatabase database = new HandmadeDatabase(name);

            HandmadeObject object = new HandmadeObject("LevelData");
            object.addString(HandmadeString.Create("name", name));
            object.addString(HandmadeString.Create("path", this.path));
            object.addField(HandmadeField.Integer("width", width));
            object.addField(HandmadeField.Integer("height", height));
            object.addField(HandmadeField.Integer("entitiesCount", entities.size()));

            database.addObject(object);
            for (int i = 0; i < entities.size(); i++) {
                Entity e = entities.get(i);

                HandmadeObject entity = new HandmadeObject("E" + i);
                byte type = 0;
                if (e instanceof Player)
                    type = 1;

                entity.addField(HandmadeField.Byte("type", type));
                entity.addField(HandmadeField.Integer("arrayIndex", i));
                e.serialize(entity);

                database.addObject(entity);
            }
            database.serializeToFile(path);
        }

        public static Level load(String path) {
            HandmadeDatabase database = HandmadeDatabase.DeserializeFromFile(path);
            HandmadeObject levelData = database.findObject("LevelData");

            Level result = new Level();
            result.name = levelData.findString("name").getString();
            result.name = levelData.findString("path").getString();
            result.width = levelData.findField("width").value();
            result.width = levelData.findField("height").value();

            int entitiesCount = levelData.findField("entitiesCount").value();

            for (int i = 0; i < entitiesCount; i++) {
                HandmadeObject entity = database.findObject("E" + i);
                Entity e;
                if ((byte)entity.findField("type").value() == 1)
                    e = Player.deserialize(entity);
                else
                    e = Entity.deserialize(entity);
                result.add(e);
            }
            return result;
        }

    }

    static class Entity {

        protected int x, y;
        protected boolean removed = false;
        protected Level level;

        public Entity() {
            x = 0;
            y = 0;
        }

        public void init(Level level) {
            this.level = level;
        }

        public void serialize(HandmadeObject object) {
            object.addField(HandmadeField.Integer("x", x));
            object.addField(HandmadeField.Integer("y", y));
            object.addField(HandmadeField.Boolean("removed", removed));
        }

        public static Entity deserialize(HandmadeObject object) {
            Entity result = new Entity();
            result.x = object.findField("x").value();
            result.y = object.findField("y").value();
            result.removed = object.findField("removed").value();

            return result;
        }

    }

    static class Player extends Entity {

        private String name;
        private String avatarPath;

        private Player() {

        }

        public Player(String name, int x, int y) {
            this.x = x;
            this.y = y;

            this.name = name;
            avatarPath = "res/avatar.png";
        }

        public void serialize(HandmadeObject object) {
            super.serialize(object);
            object.addString(HandmadeString.Create("name", name));
            object.addString(HandmadeString.Create("avatarPath", avatarPath));
        }

        public static Player deserialize(HandmadeObject object) {
            Entity e =Entity.deserialize(object);

            Player result = new Player();
            result.x = e.x;
            result.y = e.y;
            result.removed = e.removed;

            result.name = object.findString("name").getString();
            result.avatarPath = object.findString("avatarPath").getString();

            return result;
        }

    }

    public void play() {
        Entity mob = new Entity();
        Player player = new Player("vosure", 40, 28);

        Level level = new Level("res/level.lvl");
        level.add(mob);
        level.add(player);

        level.save("level.hmdb");
    }

}
