package model;

/**
 * Created by Hermann on 02.08.2018.
 */
public enum Direction {
    UP,
    DOWN,
    RIGHT,
    LEFT;

    static public boolean areOpposites (Direction dir1, Direction dir2){
        return (dir1 == UP && dir2 == DOWN || dir1 == DOWN && dir2 == UP ||
                dir1 == RIGHT && dir2 == LEFT || dir1== LEFT && dir2 == RIGHT);
    }

    public Direction getOpposite(){
        if(this == Direction.DOWN){
            return Direction.UP;
        }

        if(this == Direction.UP){
            return Direction.DOWN;
        }

        if(this == Direction.LEFT){
            return Direction.RIGHT;
        }

        if(this == Direction.RIGHT){
            return Direction.LEFT;
        }

        return null;
    }
}
