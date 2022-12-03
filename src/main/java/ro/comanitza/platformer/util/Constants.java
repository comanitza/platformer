package ro.comanitza.platformer.util;

public class Constants {

    private Constants() {}

    public static class Directions {
        public static final int STANDING_STILL = -1;
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
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
