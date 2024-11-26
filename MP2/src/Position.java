import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public enum Position
{
    S("Setter"),
    OH("Outside Hitter"),
    OP("Opposite Hitter"),
    MB("Middle Blocker"),
    L("Libero"),
    DS("Defensive Specialist");

    public final String fullname;

    private static final HashMap<String, Position> BY_FULLNAME=new HashMap<>();
    static {
        for(Position p:values())
            BY_FULLNAME.put(p.fullname,p);
    }

    Position(String fullname){
        this.fullname=fullname;
    }

    public static Position getPos(String fullname){
        Position fromHashMap=BY_FULLNAME.get(fullname);

        if(fromHashMap==null)
            return Position.valueOf(fullname.toUpperCase());

        return BY_FULLNAME.get(fullname);
    }
    public static String AllPositionsToString(){
        StringBuilder sb=new StringBuilder("Positions:\n");

        for (Position p:values())
            sb.append(getPos(p.fullname)).append(" = ").append(p.fullname).append(" ");
        return sb.toString();
    }
}
