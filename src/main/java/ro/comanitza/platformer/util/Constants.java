package ro.comanitza.platformer.util;

public class Constants {

    private Constants() {}

    public static class Enemy {

        public static final int CRABBY = 0;

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        public static final int CRABBY_WIDTH_DEFAULT = 72;
        public static final int CRABBY_HEIGHT_DEFAULT = 32;

        public static final int CRABBY_WIDTH = (int) (CRABBY_WIDTH_DEFAULT * Game.SCALE);
        public static final int CRABBY_HEIGHT = (int) (CRABBY_HEIGHT_DEFAULT * Game.SCALE);

        public static final int CRRABY_X_OFFSET = (int)(26 * Game.SCALE);
        public static final int CRRABY_Y_OFFSET = (int)(9 * Game.SCALE);

        public static int getSpriteAmount(int enemyType, int enemyAction) {

            switch (enemyType) {
                case CRABBY:
                default:
                    return switch (enemyAction) {
                        case IDLE -> 9;
                        case RUNNING -> 6;
                        case ATTACK -> 7;
                        case HIT -> 4;
                        case DEAD -> 5;
                        default -> 1;
                    };
            }


        }
    }

    public static class Game {

        public static final int TILES_DEFAULT_SIZE = 32;
        public static final int TILES_IN_WIDTH = 26;
        public static final int TILES_IN_HEIGHT = 14;
        public static final double SCALE = 2d;
        public static final int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE);
        public static final int GAME_WIDTH = TILES_IN_WIDTH * TILES_SIZE;
        public static final int GAME_HEIGHT = TILES_IN_HEIGHT * TILES_SIZE;

    }

    public static class Environment {

        public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
        public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;

        public static final int BIG_CLOUD_WIDTH= (int)(BIG_CLOUD_WIDTH_DEFAULT * Game.SCALE);
        public static final int BIG_CLOUD_HEIGHT= (int)(BIG_CLOUD_HEIGHT_DEFAULT * Game.SCALE);

        public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
        public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;

        public static final int SMALL_CLOUD_WIDTH= (int)(SMALL_CLOUD_WIDTH_DEFAULT * Game.SCALE);
        public static final int SMALL_CLOUD_HEIGHT= (int)(SMALL_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
    }

    public static class Directions {
        public static final int STANDING_STILL = -1;
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class UI {
        private UI() {}

        public static class Buttons {
            private Buttons() {}

            public static final int DEFAULT_WIDTH = 140;
            public static final int DEFAULT_HEIGHT = 56;

            public static final int WIDTH = (int) (DEFAULT_WIDTH * Game.SCALE);
            public static final int HEIGHT = (int) (DEFAULT_HEIGHT * Game.SCALE);

        }

        public static class PauseButtons {

            public static final int SOUND_BUTTON_DEFAULT_SIZE = 42;
            public static final int SOUND_BUTTON_SIZE = (int)(SOUND_BUTTON_DEFAULT_SIZE * Game.SCALE);

        }

        public static class UrmButtons {

            public static int URM_DEFAULT_SIZE = 56;
            public static int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE);
        }


    }

    public static class Player {

        private Player() {}

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMP = 2;
        public static final int FALL = 3;
        public static final int GROUND = 4;
        public static final int HIT = 5;
        public static final int ATTACK_1 = 6;
        public static final int ATTACK_JUMP_1 = 7;
        public static final int ATTACK_JUMP_2 = 8;

        public static int getSpriteAmount(final int playerAction) {

            return switch (playerAction) {
                case Player.IDLE -> 5;
                case Player.RUNNING -> 6;
                case Player.HIT -> 4;
                case Player.JUMP, Player.ATTACK_1, Player.ATTACK_JUMP_1, Player.ATTACK_JUMP_2 -> 3;
                case Player.FALL -> 2;
                case Player.GROUND -> 2;
                default -> 1;
            };

        }
    }



}
